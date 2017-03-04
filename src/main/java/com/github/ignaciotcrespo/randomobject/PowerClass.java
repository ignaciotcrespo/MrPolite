package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crespo on 3/4/17.
 */
public class PowerClass {
    Class clazz;
    Type[] generics;

    public <T> PowerClass(Class<?> clazz, Type[] genericTypesInClass) {
        this.clazz = clazz;
        this.generics = genericTypesInClass;
    }

    public boolean isPrivate() {
        return Modifier.isPrivate(clazz.getModifiers());
    }

    public boolean canGenerateData(DataGenerator generator) {
        return generator.canProcess(clazz);
    }

    public Object newInstance() {
        return ClassUtils.newInstance(clazz);
    }

    public boolean isValidPackage() {
        return clazz != null && !clazz.getName().startsWith("java.")
                && !clazz.getName().startsWith("javax.")
                && !clazz.getName().startsWith("android.")
                && !clazz.getName().startsWith("com.android.")
                && !clazz.getName().startsWith("dalvik.");
    }

    public PowerField[] getDeclaredFields() {
        return getDeclaredFields(clazz, generics);
    }

    public static PowerField[] getDeclaredFields(Class clazz) {
        return getDeclaredFields(clazz, null);
    }

    private static PowerField[] getDeclaredFields(Class clazz, Type[] generics) {
        Field[] declaredFields = clazz.getDeclaredFields();
        PowerField[] fields = new PowerField[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            fields[i] = new PowerField(declaredFields[i], new PowerClass(clazz, generics));
        }
        return fields;
    }

    public boolean isFor(Field field) {
        return field.getType().equals(clazz);
    }

    public boolean hasValidGenericTypes() {
        TypeVariable<? extends Class<?>>[] genericTypes = clazz.getTypeParameters();
        return genericTypes != null && genericTypes.length != generics.length;
    }

    public boolean hasGenerics() {
        return generics != null && generics.length > 0;
    }

    public PowerClass getSuperclass() {
        TypeVariable<?>[] clazzGenericParameters = clazz.getTypeParameters();
        Map<TypeVariable<?>, Type> clazzGenericParametersMap = new HashMap<>();
        for (int i = 0; i < generics.length; i++) {
            clazzGenericParametersMap.put(clazzGenericParameters[i], generics[i]);
        }
        Class<?> superclazz = clazz.getSuperclass();
        Type[] genericTypesInSuperClass = new Type[0];
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            genericTypesInSuperClass = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        }
        for (int i = 0; i < genericTypesInSuperClass.length; i++) {
            if (genericTypesInSuperClass[i] instanceof TypeVariable) {
                // is T, replace it
                genericTypesInSuperClass[i] = clazzGenericParametersMap.get(genericTypesInSuperClass[i]);
            }
        }
        return new PowerClass(superclazz, genericTypesInSuperClass);
    }

    public static PowerClass[] getDeclaredClasses(Object parent) {
        Class<?>[] declaredClasses = parent.getClass().getDeclaredClasses();
        PowerClass[] classes = new PowerClass[declaredClasses.length];
        for (int i = 0; i < declaredClasses.length; i++) {
            classes[i] = new PowerClass(declaredClasses[i], null);
        }
        return classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PowerClass that = (PowerClass) o;

        return clazz.equals(that.clazz);
    }

    @Override
    public int hashCode() {
        return clazz.hashCode();
    }

    public boolean isStatic() {
        return Modifier.isStatic(clazz.getModifiers());
    }

    public boolean isArray() {
        return clazz.isArray();
    }

    public Class<?> getArrayType() {
        return getArrayType(clazz);
    }

    private Class<?> getArrayType(Class type) {
        if (type.isArray()) {
            return getArrayType(type.getComponentType());
        }
        return type;
    }

    public static PowerField getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        return new PowerField(clazz.getDeclaredField(fieldName), new PowerClass(clazz, null));
    }

    public static Object createArrayWithDefaultValues(PowerClass type, int size) {
        if (!type.isArray()) {
            return null;
        }
        Class<?> componentType = type.clazz.getComponentType();
        Object instance = Array.newInstance(componentType, size);
        if (componentType.isArray()) {
            for (int i = 0; i < size; i++) {
                Array.set(instance, i, createArrayWithDefaultValues(new PowerClass(componentType, null), size));
            }
        }
        return instance;
    }

    public Object[] getEnumConstants() {
        return clazz.getEnumConstants();
    }
}
