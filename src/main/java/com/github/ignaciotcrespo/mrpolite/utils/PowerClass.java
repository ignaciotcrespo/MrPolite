/**
 * The MIT License
 * <p>
 * Copyright (c) 2017, Ignacio Tomas Crespo (itcrespo@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.ignaciotcrespo.mrpolite.utils;

import com.github.ignaciotcrespo.mrpolite.GeneratedValue;
import com.github.ignaciotcrespo.mrpolite.generators.DataGenerator;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by crespo on 3/4/17.
 */
public class PowerClass {
    public int depth;
    Class clazz;
    Type[] generics;

    private GeneratedValue currentInstance;

    public <T> PowerClass(Class<?> clazz, Type[] genericTypesInClass) {
        this.clazz = clazz;
        this.generics = genericTypesInClass;
        this.depth = 0;
    }

    public <T> PowerClass(T object, Type[] genericTypesInClass) {
        this(object.getClass(), genericTypesInClass);
        currentInstance = new GeneratedValue();
        currentInstance.setValue(object);
    }

    public boolean canGenerateData(DataGenerator generator) {
        return generator.canProcess(clazz);
    }

    public Object newInstance(Randomizer randomizer) {
        Object instance = ClassUtils.newInstance(clazz, randomizer);
        currentInstance = new GeneratedValue();
        currentInstance.setValue(instance);
        return instance;
    }

    public boolean isValidPackage() {
        return clazz != null && !clazz.getName().startsWith("java.")
                && !clazz.getName().startsWith("javax.")
                && !clazz.getName().startsWith("android.")
                && !clazz.getName().startsWith("com.android.")
                && !clazz.getName().startsWith("dalvik.");
    }

    public PowerField[] getDeclaredFields() {
        PowerField[] declaredFields = getDeclaredFields(clazz, getGenerics());
        for (PowerField field: declaredFields) {
            field.setParentInstance(currentInstance);
            field.setDepth(depth);
        }
        return declaredFields;
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
        return genericTypes != null && genericTypes.length != getGenerics().length;
    }

    public boolean hasGenerics() {
        return getGenerics() != null && getGenerics().length > 0;
    }

    public PowerClass getSuperclass() {
        TypeVariable<?>[] clazzGenericParameters = clazz.getTypeParameters();
        Map<TypeVariable<?>, Type> clazzGenericParametersMap = new HashMap<TypeVariable<?>, Type>();
        for (int i = 0; i < getGenerics().length; i++) {
            clazzGenericParametersMap.put(clazzGenericParameters[i], getGenerics()[i]);
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
        PowerClass powerClass = new PowerClass(superclazz, genericTypesInSuperClass);
        powerClass.currentInstance = currentInstance;
        powerClass.depth = depth + 1;
        return powerClass;
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

    public static Object newArray(PowerClass type, int size) {
        if (!type.isArray()) {
            return null;
        }
        Class<?> componentType = type.clazz.getComponentType();
        Object instance = Array.newInstance(componentType, size);
        if (componentType.isArray()) {
            for (int i = 0; i < size; i++) {
                Array.set(instance, i, newArray(new PowerClass(componentType, null), size));
            }
        }
        return instance;
    }

    public Object[] getEnumConstants() {
        return clazz.getEnumConstants();
    }

    public boolean hasCurrentInstance() {
        return currentInstance != null;
    }

    public Type[] getGenerics() {
        return generics;
    }

    public boolean isValidDepth(int depth) {
        return this.depth < depth;
    }

    @Override
    public String toString() {
        return "PowerClass{" +
                "depth=" + depth +
                ", clazz=" + clazz +
                ", generics=" + Arrays.toString(generics) +
                ", currentInstance=" + currentInstance +
                '}';
    }

    public <K> boolean is(Class<K> clazz) {
        return this.clazz.equals(clazz);
    }
}
