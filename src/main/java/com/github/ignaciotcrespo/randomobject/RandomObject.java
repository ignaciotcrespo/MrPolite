package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.constraints.Constraint;
import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.generators.Generators;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.*;
import java.util.*;

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

    private <T> T fillInnerClass(Object parent, PowerClass clazz, int depth) {
        T instance = null;
        try {
            for (PowerClass cls : PowerClass.getDeclaredClasses(parent)) {
                if (clazz.equals(cls)) {
                    if (cls.isStatic()) {
                        return fill(cls, depth);
                    } else {
                        instance = (T) cls.newInstance();
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

    private <T> void processFieldsAndParents(Object parent, PowerClass clazz, T instance, int depth) {
        if (instance != null && depth < this.depth) {
            processFields(parent, clazz, instance, depth);
            processSuperClasses(parent, clazz, instance, depth);
        }
    }

    private <T> void processSuperClasses(Object parent, PowerClass clazz, T instance, int depth) {
        PowerClass superclazz = clazz.getSuperclass();
        depth++;
        if (superclazz.isValidPackage() && depth < this.depth) {
            processFields(parent, superclazz, instance, depth);
            processSuperClasses(parent, superclazz, instance, depth);
        }
    }

    private void processFields(Object parent, PowerClass clazz, Object instance, int depth) {
        if (clazz.isValidPackage()) {
            for (PowerField field : clazz.getDeclaredFields()) {
                if(field.isNameIn(excludeFields)){
                    continue;
                }
                if(field.isClassIn(excludeClasses)){
                    continue;
                }
                if (field.isInvalid()) {
                    continue;
                }
                Object value = getRandomValueForField(parent, field, instance, depth);
                field.setValueToField(instance, value);
            }
        }
    }

    <T> T fill(Class<T> clazz) {
        return fill(clazz, 0);
    }

    private <T> T fill(Class<T> clazz, int depth) {
        return fill(new PowerClass(clazz, genericTypesInClass), depth);
    }

    private <T> T fill(PowerClass clazz, int depth) {
        if (clazz.isPrivate()) {
            return null;
        }
        Object instance = getRandomValueForType(depth, clazz);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance, depth);
        }

        tryToFillCollection(instance, depth, clazz.generics);

        return (T) instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, PowerField field, Object instance, int depth) {
        if (field.isArray()) {
            Object newInstance = PowerClass.createArrayWithDefaultValues(field.getType(), getRandomCollectionSize());
            fillArrayWithValues(newInstance, parentInnerClass, field, instance, depth);
            return newInstance;
        } else {
            return getRandomValueForFieldType(parentInnerClass, field, instance, depth);
        }
    }

    private void tryToFillCollection(Object value, int depth, Type[] genericTypes) {
        if (value instanceof Collection || value instanceof Map) {
            List items = new ArrayList();
            int randomCollectionSize = getRandomCollectionSize();
            for (int i = 0; i < randomCollectionSize; i++) {
                Type type = genericTypes.length > 0 ? genericTypes[0] : Object.class;
                Object item = null;
                if (type instanceof ParameterizedType) {
                    Class rawType = (Class) ((ParameterizedType) type).getRawType();
                    Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                    item = fill(new PowerClass(rawType, actualTypeArguments), depth);
                } else {
                    item = fill(new PowerClass((Class) type, ((Class) type).getTypeParameters()), depth);
                }
                items.add(item);
            }
            if (value instanceof List) {
                List list = (List) value;
                list.addAll(items);
            }
            if (value instanceof Set) {
                Set set = (Set) value;
                set.addAll(items);
            }
            if (value instanceof Map) {
                Map map = (Map) value;
                for (int i = 0; i < randomCollectionSize; i++) {
                    Type type = genericTypes.length > 1 ? genericTypes[1] : Object.class;
                    Object item = null;
                    if (type instanceof ParameterizedType) {
                        PowerClass clazz = new PowerClass((Class) ((ParameterizedType) type).getRawType(), ((ParameterizedType) type).getActualTypeArguments());
                        item = fill(clazz, depth);
                    } else {
                        item = fill(new PowerClass((Class) type, ((Class) type).getTypeParameters()), depth);
                    }
                    map.put(items.get(i), item);
                }
            }
        }
    }


    private void fillArrayWithValues(Object array, Object parentInnerClass, PowerField field, Object instance, int depth) {
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Object value = Array.get(array, i);
                if (value == null || value.getClass().isPrimitive() || value instanceof Number) {
                    value = getRandomValueForFieldType(parentInnerClass, field, instance, depth);
                    Array.set(array, i, value);
                } else {
                    fillArrayWithValues(value, parentInnerClass, field, instance, depth);
                }
            }
        }
    }

    int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private Object getRandomValueForFieldType(Object parentInnerClass, PowerField field, Object instance, int depth) {
        DataGenerator generator = getGenerator(field.getRawType());
        Object value = generator.getValue(field, field.getType());
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value, randomizer);
                }
            }
        }
        if (value == null) {
            if (parentInnerClass != null) {
                value = fillInnerClass(parentInnerClass, field.getType(), depth + 1);
            } else {
                value = fill(field.getType(), depth + 1);
                if (value == null) {
                    value = fillInnerClass(instance, field.getType(), depth + 1);
                }
            }
        } else {
            tryToFillCollection(value, depth, field.getGenericTypesInField());
        }
        return value;
    }

    private Object getRandomValueForType(int depth, PowerClass type) {
        DataGenerator generator = getGenerator(type);
        Object value = generator.getValue(null, type);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(null, value, randomizer);
                }
            }
        }
        if (value == null) {
            value = type.newInstance();
        }
        return value;
    }

    private DataGenerator getGenerator(PowerClass type) {
        for (DataGenerator generator : generators) {
            if (type.canGenerateData(generator)) {
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

