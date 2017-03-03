package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.constraints.Constraint;
import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.generators.Generators;
import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.ignaciotcrespo.randomobject.utils.ClassUtils.isAbstract;

/**
 * Created by crespo on 2/18/17.
 */
class RandomObject {

    static final Range DEFAULT_COLLECTION_RANGE = new Range(2, 5);
    static final int DEFAULT_DEPTH = 3;

    private Processor processor = new Processor(0);
    private int depth = DEFAULT_DEPTH;

    private DataGenerator[] generators;

    private final List<Constraint> constraints = new ArrayList<>();
    private Range collectionSizeRange = DEFAULT_COLLECTION_RANGE;
    private List<String> excludeFields = new ArrayList<>();
    private List<Class<?>> excludeClasses = new ArrayList<>();
    private Randomizer randomizer = new Randomizer();
    private Class<?>[] genericTypesInClass = new Class[0];

    private RandomObject() {
        // hide constructor
        initGenerators();
    }

    private void initGenerators() {
        generators = Generators.createDefault(randomizer);
    }

    private <T> T fillInnerClass(Object parent, Class<T> clazz, int depth, Type[] genericTypesInClass) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls, depth, genericTypesInClass);
                    } else {
                        try {
                            Constructor<?> constructor = cls.getDeclaredConstructor(parent.getClass());
                            constructor.setAccessible(true);
                            instance = (T) constructor.newInstance(parent);
                            break;
                        } catch (ClassCastException exc) {
                            instance = null;
                        }
                    }
                }
            }
            if (instance != null) {
                processFieldsAndParents(parent, clazz, instance, depth, genericTypesInClass);
            } else {
                Class superClazz = parent.getClass().getSuperclass();
                while (superClazz != null) {
                    for (Class<?> cls : superClazz.getDeclaredClasses()) {
                        if (clazz.equals(cls)) {
                            if (Modifier.isStatic(cls.getModifiers())) {
                                return fill((Class<T>) cls, depth);
                            } else {
                                try {
                                    Constructor<?> constructor = cls.getDeclaredConstructor(superClazz);
                                    constructor.setAccessible(true);
                                    instance = (T) constructor.newInstance(parent);
                                    break;
                                } catch (ClassCastException exc) {
                                    instance = null;
                                }
                            }
                        }
                    }
                    if (instance != null) {
                        processFieldsAndParents(parent, clazz, instance, depth, genericTypesInClass);
                    }
                    superClazz = superClazz.getSuperclass();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    private <T> void processFieldsAndParents(Object parent, Class<T> clazz, T instance, int depth, Type[] genericTypesInClass) {
        if (instance != null && depth < this.depth) {
            processFields(parent, clazz, instance, depth, genericTypesInClass);
            processSuperClasses(parent, clazz, instance, depth);
        }
    }

    private boolean isValidClass(Class<?> clazz) {
        boolean valid = !clazz.getName().startsWith("java.")
                && !clazz.getName().startsWith("javax.")
                && !clazz.getName().startsWith("android.")
                && !clazz.getName().startsWith("com.android.")
                && !clazz.getName().startsWith("dalvik.");
        if (!valid) {
            System.out.println("Ignored class " + clazz.getName());
        }
        return valid;
    }

    private <T> void processSuperClasses(Object parent, Class<T> clazz, T instance, int depth) {
        Class<?> superclazz = clazz.getSuperclass();
        depth++;
        Type[] genericTypesInSuperClass = new Type[0];
        Type genericSuperclass = clazz.getGenericSuperclass();
        if(genericSuperclass instanceof ParameterizedType) {
            genericTypesInSuperClass = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
        }
        while (superclazz != null && isValidClass(superclazz) && depth < this.depth) {
            processFields(parent, superclazz, instance, depth, genericTypesInSuperClass);
            superclazz = superclazz.getSuperclass();
            genericSuperclass = superclazz.getGenericSuperclass();
            if(genericSuperclass instanceof ParameterizedType) {
                genericTypesInSuperClass = ((ParameterizedType)genericSuperclass).getActualTypeArguments();
            }
            depth++;
        }
    }

    private void processFields(Object parent, Class<?> clazz, Object instance, int depth, Type[] genericTypesInClass) {
        if (isValidClass(clazz)) {
            for (Field field : clazz.getDeclaredFields()) {
                if (isExcludedField(field)) {
                    continue;
                }
                if (field.getName().startsWith("this$")) {
                    // avoid inner class reference to outer class
                    continue;
                }
                if (processor.shouldStopNestedSameClasses(field, clazz)) {
                    // ignore same nested class
                    continue;
                }
                if (isInvalidGeneric(field, genericTypesInClass) || isInvalidNumberOfGenerics(field, genericTypesInClass)) {
                    continue;
                }
                Object value;
                Class<?> type = getFieldType(field, genericTypesInClass);
                if (!type.isPrimitive() && isAbstract(type)) {
                    // dont change value in fields with abstract type
                    continue;
                }
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Type[] genericTypesInField = new Type[0];
                if(isGenericFieldWithParameters(field)){
                    genericTypesInField = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                }
                value = getRandomValueForField(parent, field, instance, depth, genericTypesInField, genericTypesInClass);
                try {
                    field.set(instance, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isExcludedField(Field field) {
        for (String regex : excludeFields) {
            if (field.getName().matches(regex)) {
                return true;
            }
        }
        for (Class clazz : excludeClasses) {
            if (getFieldType(field, null).equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    <T> T fill(Class<T> clazz) {
        return fill(clazz, 0);
    }

    private <T> T fill(Class<T> clazz, int depth) {
        return fill(clazz, depth, genericTypesInClass);
    }

    private <T> T fill(Class<T> clazz, int depth, Type[] genericTypesInClass) {
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return null;
        }
        T instance = ClassUtils.newInstance(clazz);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance, depth, genericTypesInClass);
        }
        return instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance, int depth, Type[] genericTypesInField, Type[] genericTypesInClass) {
        Class<?> type = getFieldType(field, genericTypesInClass);
        if (type.isArray()) {
            Object newInstance = createArrayWithDefaultValues(type);
            Class<?> arrayType = getArrayType(type);
            fillArrayWithValues(newInstance, arrayType, parentInnerClass, field, instance, depth);
            return newInstance;
        } else {
            return getRandomValueForFieldType(parentInnerClass, field, instance, depth, type, genericTypesInField);
        }
    }

    private boolean isInvalidGeneric(Field field, Type[] genericTypesInClass) {
        boolean hasGenericSet = genericTypesInClass.length > 0;
        return isGenericField(field) && !hasGenericSet;
    }

    private boolean isInvalidNumberOfGenerics(Field field, Type[] genericTypesInClass) {
        if (isGenericField(field)) {
            TypeVariable<? extends Class<?>>[] genericTypes = field.getDeclaringClass().getTypeParameters();
            return genericTypes != null && genericTypes.length != genericTypesInClass.length;
        }
        return false;
    }

    private boolean isGenericField(Field field) {
        Type fieldGenericType = field.getGenericType();
        return fieldGenericType != null && (fieldGenericType instanceof TypeVariable);
    }

    private boolean isGenericFieldWithParameters(Field field) {
        Type fieldGenericType = field.getGenericType();
        return fieldGenericType != null && fieldGenericType instanceof ParameterizedType;
    }

    private Class<?> getFieldType(Field field, Type[] genericTypesInClass) {
        Type fieldGenericType = field.getGenericType();
        boolean hasGenericSet = genericTypesInClass != null && genericTypesInClass.length > 0;
        boolean isInvalidGeneric = isGenericField(field) && !hasGenericSet;
        if (isInvalidGeneric) {
            return null;
        }
        if (hasGenericSet && isGenericField(field)) {
            Type[] genericTypes = field.getDeclaringClass().getTypeParameters();
            if (genericTypes != null && genericTypes.length == genericTypesInClass.length) {
                for (int i = 0; i < genericTypes.length; i++) {
                    Type genericType = genericTypes[i];
                    if (genericType.getTypeName().equals(fieldGenericType.getTypeName())) {
                        return (Class<?>) genericTypesInClass[i];
                    }
                }
            }
        }
        return field.getType();
    }

//    private Class<?> getFieldType(Field field, Type[] genericParameters) {
//        Type fieldGenericType = field.getGenericType();
//        boolean hasGenericSet = genericParameters != null && genericParameters.length > 0;
//        boolean isInvalidGeneric = isGenericField(field) && !hasGenericSet;
//        if (isInvalidGeneric) {
//            return null;
//        }
//        if (hasGenericSet && isGenericField(field)) {
//            Type[] genericTypes = field.getDeclaringClass().getTypeParameters();
//            if (genericTypes != null && genericTypes.length == genericParameters.length) {
//                for (int i = 0; i < genericTypes.length; i++) {
//                    Type genericType = genericTypes[i];
//                    if (genericType.getTypeName().equals(fieldGenericType.getTypeName())) {
//                        return (Class<?>) genericParameters[i];
//                    }
//                }
//            }
//            if(genericTypes == null || genericTypes.length == 0 && field.getGenericType() instanceof ParameterizedType) {
//                genericTypes = ((ParameterizedType)field.getGenericType()).getActualTypeArguments();
//            }
//            if (genericTypes != null && genericTypes.length == genericParameters.length) {
//                for (int i = 0; i < genericTypes.length; i++) {
//                    Type genericType = genericTypes[i];
//                    if (genericType.getTypeName().equals(fieldGenericType.getTypeName())) {
//                        return (Class<?>) genericParameters[i];
//                    }
//                }
//            }
//        }
//        return field.getType();
//    }

    private void fillArrayWithValues(Object array, Class<?> arrayType, Object parentInnerClass, Field field, Object instance, int depth) {
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Object value = Array.get(array, i);
                if (value == null || value.getClass().isPrimitive() || value instanceof Number) {
                    value = getRandomValueForFieldType(parentInnerClass, field, instance, depth, arrayType, null);
                    Array.set(array, i, value);
                } else {
                    fillArrayWithValues(value, arrayType, parentInnerClass, field, instance, depth);
                }
            }
        }
    }

    private Class<?> getArrayType(Class<?> type) {
        if (type.isArray()) {
            return getArrayType(type.getComponentType());
        }
        return type;
    }

    private Object createArrayWithDefaultValues(Class<?> type) {
        if (!type.isArray()) {
            return null;
        }
        Class<?> componentType = type.getComponentType();
        int length = getRandomCollectionSize();
        Object instance = Array.newInstance(componentType, length);
        if (componentType.isArray()) {
            for (int i = 0; i < length; i++) {
                Array.set(instance, i, createArrayWithDefaultValues(componentType));
            }
        }
        return instance;
    }

    private int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private Object getRandomValueForFieldType(Object parentInnerClass, Field field, Object instance, int depth, Class<?> fieldType, Type[] genericTypesInField) {
        DataGenerator generator = getGenerator(fieldType);
        Object value = generator.getValue(field, fieldType);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value, randomizer);
                }
            }
        }
        if (value == null) {
            if (parentInnerClass != null) {
                value = fillInnerClass(parentInnerClass, fieldType, depth + 1, genericTypesInField);
            } else {
                value = fill(fieldType, depth + 1, genericTypesInField);
                if (value == null) {
                    value = fillInnerClass(instance, fieldType, depth + 1, genericTypesInField);
                }
            }
        }
        return value;
    }

    private DataGenerator getGenerator(Class<?> type) {
        for (DataGenerator generator : generators) {
            if (generator.canProcess(type)) {
                return generator;
            }
        }
        return new DataGenerator() {
            @Override
            public boolean canProcess(Class<?> type) {
                return true;
            }
        };
    }

    <T> List<T> fill(int size, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(fill(clazz));
        }
        return list;
    }

    <T> T[] fillArray(int size, Class<T> clazz) {
        T[] list = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            list[i] = fill(clazz);
        }
        return list;
    }

    static RandomObject random() {
        return new RandomObject();
    }

    public RandomObject deepSameTypeFields(int deep) {
        processor = new Processor(deep);
        return this;
    }

    RandomObject depth(int i) {
        depth = i;
        return this;
    }


    RandomObject collectionSizeRange(Range collectionSizeRange) {
        this.collectionSizeRange = collectionSizeRange;
        return this;
    }

    RandomObject seed(int seed) {
        if (seed > 0) {
            this.randomizer = new Randomizer(seed);
            initGenerators();
        }
        return this;
    }

    void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    RandomObject excludeClasses(List<Class<?>> excludeClasses) {
        this.excludeClasses = excludeClasses;
        return this;
    }

    RandomObject excludeRegex(List<String> excludeFields) {
        this.excludeFields = excludeFields;
        return this;
    }

    public RandomObject generics(Class<?>... generics) {
        this.genericTypesInClass = generics;
        return this;
    }

    static class Range {
        final int min;
        final int max;

        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

}

