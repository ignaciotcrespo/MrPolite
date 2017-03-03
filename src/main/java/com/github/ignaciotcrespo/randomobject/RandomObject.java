package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.constraints.Constraint;
import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.generators.Generators;
import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Calendar;
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
    private Class<?>[] generics;

    private RandomObject() {
        // hide constructor
        initGenerators();
    }

    private void initGenerators() {
        generators = Generators.createDefault(randomizer);
    }

    private <T> T fillInnerClass(Object parent, Class<T> clazz, int depth) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls, depth);
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
                processFieldsAndParents(parent, clazz, instance, depth);
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
                        processFieldsAndParents(parent, clazz, instance, depth);
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

    private <T> void processFieldsAndParents(Object parent, Class<T> clazz, T instance, int depth) {
        if (instance != null && depth < this.depth) {
            processFields(parent, clazz, instance, depth);
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
        while (superclazz != null && isValidClass(superclazz) && depth < this.depth) {
            processFields(parent, superclazz, instance, depth);
            superclazz = superclazz.getSuperclass();
            depth++;
        }
    }

    private void processFields(Object parent, Class<?> clazz, Object instance, int depth) {
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
                if (isInvalidGeneric(field) || isInvalidNumberOfGenerics(field)) {
                    continue;
                }
                Object value;
                Class<?> type = getFieldType(field);
                if (!type.isPrimitive() && isAbstract(type)) {
                    // dont change value in fields with abstract type
                    continue;
                }
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                value = getRandomValueForField(parent, field, instance, depth);
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
            if (getFieldType(field).equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    <T> T fill(Class<T> clazz) {
        return fill(clazz, 0);
    }

    private <T> T fill(Class<T> clazz, int depth) {
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return null;
        }
        T instance = ClassUtils.newInstance(clazz);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance, depth);
        }
        return instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance, int depth) {
        Class<?> type = getFieldType(field);
        if (type.isArray()) {
            Object newInstance = createArrayWithDefaultValues(type);
            Class<?> arrayType = getArrayType(type);
            fillArrayWithValues(newInstance, arrayType, parentInnerClass, field, instance, depth);
            return newInstance;
        } else {
            return getRandomValueForFieldType(parentInnerClass, field, instance, depth, type);
        }
    }

    private boolean isInvalidGeneric(Field field) {
        Type fieldGenericType = field.getGenericType();
        boolean isGenericField = fieldGenericType != null && fieldGenericType instanceof TypeVariable;
        boolean hasGenericSet = generics != null && generics.length > 0;
        return isGenericField && !hasGenericSet;
    }

    private boolean isInvalidNumberOfGenerics(Field field) {
        if (isGenericField(field)) {
            TypeVariable<? extends Class<?>>[] genericTypes = field.getDeclaringClass().getTypeParameters();
            return genericTypes != null && genericTypes.length != generics.length;
        }
        return false;
    }

    private boolean isGenericField(Field field) {
        Type fieldGenericType = field.getGenericType();
        return fieldGenericType != null && fieldGenericType instanceof TypeVariable;
    }

    private Class<?> getFieldType(Field field) {
        Type fieldGenericType = field.getGenericType();
        boolean hasGenericSet = generics != null && generics.length > 0;
        boolean isInvalidGeneric = isGenericField(field) && !hasGenericSet;
        if (isInvalidGeneric) {
            return null;
        }
        if (hasGenericSet && isGenericField(field)) {
            TypeVariable<? extends Class<?>>[] genericTypes = field.getDeclaringClass().getTypeParameters();
            if (genericTypes != null && genericTypes.length == generics.length) {
                for (int i = 0; i < genericTypes.length; i++) {
                    TypeVariable<? extends Class<?>> genericType = genericTypes[i];
                    if (genericType.getName().equals(((TypeVariable) fieldGenericType).getName())) {
                        return generics[i];
                    }
                }
            }
        }
        return field.getType();
    }

    private void fillArrayWithValues(Object array, Class<?> arrayType, Object parentInnerClass, Field field, Object instance, int depth) {
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Object value = Array.get(array, i);
                if (value == null || value.getClass().isPrimitive() || value instanceof Number) {
                    value = getRandomValueForFieldType(parentInnerClass, field, instance, depth, arrayType);
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

    private Object getRandomValueForFieldType(Object parentInnerClass, Field field, Object instance, int depth, Class<?> type) {
        DataGenerator generator = getGenerator(type);
        Object value = generator.getValue(field);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value, randomizer);
                }
            }
        }
        if (value == null) {
            if (parentInnerClass != null) {
                value = fillInnerClass(parentInnerClass, type, depth + 1);
            } else {
                value = fill(type, depth + 1);
                if (value == null) {
                    value = fillInnerClass(instance, type, depth + 1);
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
        this.generics = generics;
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

