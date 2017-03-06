package com.github.ignaciotcrespo.randomobject.utils;

import com.github.ignaciotcrespo.randomobject.GeneratedValue;

import java.lang.reflect.*;
import java.util.List;

/**
 * Created by crespo on 3/4/17.
 */
public class PowerField {
    private final Field field;
    private final PowerClass parentClass;

    private GeneratedValue parentInstance;

    public PowerField(Field field, PowerClass parentClass) {
        this.field = field;
        field.setAccessible(true);
        this.parentClass = parentClass;
    }

    public boolean isInvalid() {
        if (field.getName().startsWith("this$")) {
            // avoid inner class reference to outer class
            return true;
        }
        // ignore same nested class
        return //processor.shouldStopNestedSameClasses(field, parentClass)
                //||
                isInvalidGeneric()
                        || hasInvalidNumberOfGenerics()
                        || Modifier.isFinal(field.getModifiers());
    }

    private boolean isInvalidGeneric() {
        return isGeneric() && !parentClass.hasGenerics();
    }

    private boolean hasInvalidNumberOfGenerics() {
        return isGeneric() && parentClass.hasValidGenericTypes();
    }

    private boolean isGeneric() {
        Type fieldGenericType = field.getGenericType();
        return fieldGenericType != null && (fieldGenericType instanceof TypeVariable);
    }

    private Class<?> getFieldType() {
        Type fieldGenericType = field.getGenericType();
        boolean isInvalidGeneric = isGeneric() && !parentClass.hasGenerics();
        if (isInvalidGeneric) {
            return null;
        }
        if (parentClass.hasGenerics() && isGeneric()) {
            Type[] genericTypes = field.getDeclaringClass().getTypeParameters();
            if (genericTypes != null && genericTypes.length == parentClass.getGenerics().length) {
                for (int i = 0; i < genericTypes.length; i++) {
                    Type genericType = genericTypes[i];
                    if (genericType.getTypeName().equals(fieldGenericType.getTypeName())) {
                        Type type = parentClass.getGenerics()[i];
                        return getClassFromType(type);
                    }
                }
            }
        }
        return field.getType();
    }

    private Class<?> getClassFromType(Type type) {
        if (type instanceof WildcardType) {
            Type[] upper = ((WildcardType) type).getUpperBounds();
            if (upper != null && upper.length > 0) {
                return (Class<?>) upper[0];
            }
            Type[] lower = ((WildcardType) type).getLowerBounds();
            if (lower != null && lower.length > 0) {
                return (Class<?>) lower[0];
            }
        }
        if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        }
        return (Class) type;
    }

    public Type[] getGenerics() {
        Type[] genericTypesInField = new Type[0];
        if (isGenericFieldWithParameters()) {
            if (field.getGenericType() instanceof TypeVariable) {
                genericTypesInField = ((ParameterizedType) parentClass.getGenerics()[0]).getActualTypeArguments();
            } else {
                genericTypesInField = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            }
        }
        return genericTypesInField;
    }

    private boolean isGenericFieldWithParameters() {
        Type fieldGenericType = field.getGenericType();
        if (fieldGenericType instanceof TypeVariable) {
            return parentClass.getGenerics()[0] instanceof ParameterizedType;
        }
        return fieldGenericType instanceof ParameterizedType;
    }

    public boolean isArray() {
        return getType().isArray();
    }

    public PowerClass getType() {
        PowerClass powerClass = new PowerClass(getFieldType(), getGenerics());
        powerClass.depth = parentClass.depth + 1;
        return powerClass;
    }

    public void setValue(GeneratedValue generatedValue, boolean override) {
        if (generatedValue.getValue() != null && parentInstance.getValue() != null) {
            try {
                if (override) {
                    field.set(parentInstance.getValue(), generatedValue.getValue());
                } else {
                    if (generatedValue.isFromConstraint()) {
                        field.set(parentInstance.getValue(), generatedValue.getValue());
                    } else if (hasDefaultValue()) {
                        field.set(parentInstance.getValue(), generatedValue.getValue());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasDefaultValue() throws IllegalAccessException {
        Object value = field.get(parentInstance.getValue());
        return (value != null
                && (value instanceof Number && ((Number) value).doubleValue() == 0D))
                || value == null;
    }

    public PowerClass getArrayType() {
        PowerClass powerClass = new PowerClass(getType().getArrayType(), null);
        powerClass.depth = parentClass.depth + 1;
        return powerClass;
    }

    public String getName() {
        return field.getName();
    }

    public boolean nameMatches(String regex) {
        return field.getName().matches(regex);
    }

    public boolean isAssignableFrom(Class<?> clazz) {
        return getFieldType().isAssignableFrom(clazz);
    }

    public boolean isPrimitive() {
        return getFieldType().isPrimitive();
    }

    public PowerClass getRawType() {
        if (isArray()) {
            return getArrayType();
        }
        return getType();
    }

    public boolean isNameIn(List<String> names) {
        for (String regex : names) {
            if (field.getName().matches(regex)) {
                return true;
            }
        }
        return false;
    }

    public boolean isClassIn(List<Class<?>> classes) {
        for (Class clazz : classes) {
            if (getFieldType().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    public void setParentInstance(GeneratedValue instance) {
        this.parentInstance = instance;
    }

    public void setDepth(int depth) {
        parentClass.depth = depth;
    }
}
