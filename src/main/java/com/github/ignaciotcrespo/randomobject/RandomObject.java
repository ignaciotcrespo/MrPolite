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

    private Processor processor = new Processor(0);
    private int levelsTree = 1;

    private DataGenerator[] generators;

    private final List<Constraint> constraints = new ArrayList<>();
    private Range collectionSizeRange = DEFAULT_COLLECTION_RANGE;
    private List<String> excludeFields = new ArrayList<>();
    private List<Class<?>> excludeClasses = new ArrayList<>();
    private Randomizer randomizer = new Randomizer();

    private RandomObject() {
        // hide constructor
        initGenerators();
    }

    private void initGenerators() {
        generators = Generators.createDefault(randomizer);
    }

    private <T> T fillInnerClass(Object parent, Class<T> clazz, int levelTree) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls, levelTree);
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
                processFieldsAndParents(parent, clazz, instance, levelTree);
            } else {
                Class superClazz = parent.getClass().getSuperclass();
                while (superClazz != null) {
                    for (Class<?> cls : superClazz.getDeclaredClasses()) {
                        if (clazz.equals(cls)) {
                            if (Modifier.isStatic(cls.getModifiers())) {
                                return fill((Class<T>) cls, levelTree);
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
                        processFieldsAndParents(parent, clazz, instance, levelTree);
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

    private <T> void processFieldsAndParents(Object parent, Class<T> clazz, T instance, int levelTree) {
        if (instance != null && levelTree < levelsTree) {
            processFields(parent, clazz, instance, levelTree);
            processSuperClasses(parent, clazz, instance, levelTree);
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

    private <T> void processSuperClasses(Object parent, Class<T> clazz, T instance, int levelTree) {
        Class<?> superclazz = clazz.getSuperclass();
        levelTree++;
        while (superclazz != null && isValidClass(superclazz) && levelTree < levelsTree) {
            processFields(parent, superclazz, instance, levelTree);
            superclazz = superclazz.getSuperclass();
            levelTree++;
        }
    }

    private void processFields(Object parent, Class<?> clazz, Object instance, int levelTree) {
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
                Object value;
                if (!field.getType().isPrimitive() && isAbstract(field.getType())) {
                    // dont change value in fields with abstract type
                    continue;
                }
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                value = getRandomValueForField(parent, field, instance, levelTree);
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
            if (field.getType().equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    <T> T fill(Class<T> clazz) {
        return fill(clazz, 0);
    }

    private <T> T fill(Class<T> clazz, int levelTree) {
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return null;
        }
        T instance = ClassUtils.newInstance(clazz);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance, levelTree);
        }
        return instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance, int levelTree) {
        Class<?> type = field.getType();
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            int length = getRandomCollectionSize();
            Object newInstance = Array.newInstance(componentType, length);
            for (int i = 0; i < length; i++) {
                Array.set(newInstance, i, getRandomValueForFieldType(parentInnerClass, field, instance, levelTree, componentType));
            }
            return newInstance;
        } else {
            return getRandomValueForFieldType(parentInnerClass, field, instance, levelTree, type);
        }
    }

    private int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private Object getRandomValueForFieldType(Object parentInnerClass, Field field, Object instance, int levelTree, Class<?> type) {
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
                value = fillInnerClass(parentInnerClass, type, levelTree + 1);
            } else {
                value = fill(type, levelTree + 1);
                if (value == null) {
                    value = fillInnerClass(instance, type, levelTree + 1);
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

    RandomObject levelsTree(int i) {
        levelsTree = i;
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

    static class Range {
        final int min;
        final int max;

        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
