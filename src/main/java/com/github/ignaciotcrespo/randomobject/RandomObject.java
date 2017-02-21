package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/18/17.
 */
public class RandomObject {

    Processor processor = new Processor(0);
    private int dataFlags;
    int levelsTree = 1;

    DataGenerator[] generators = {
            new EnumDataGenerator(),
            new BooleanDataGenerator(),
            new ByteDataGenerator(),
            new ShortDataGenerator(),
            new IntegerDataGenerator(),
            new LongDataGenerator(),
            new FloatDataGenerator(),
            new DoubleDataGenerator(),
            new StringDataGenerator(),
            new DateDataGenerator(),
            new CalendarDataGenerator()
    };

    private RandomObject() {
        // hide constructor
    }

    public <T> T fillInnerClass(Object parent, Class<T> clazz, int levelTree) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls, levelTree);
                    } else {
                        try {
                            Constructor<?> constructor = cls.getDeclaredConstructor(new Class[]{parent.getClass()});
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
                                    Constructor<?> constructor = cls.getDeclaredConstructor(new Class[]{superClazz});
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
                if (field.getName().startsWith("this$")) {
                    // avoid inner class reference to outer class
                    continue;
                }
                if (processor.shouldStopNestedSameClasses(field, clazz)) {
                    // ignore same nested class
                    continue;
                }
                if (Modifier.isAbstract(field.getType().getModifiers())) {
                    // dont change value in fields with abstract type
                    continue;
                }
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = getRandomValueForField(parent, field, instance, levelTree);
                    field.set(instance, value);
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
    }

    public <T> T fill(Class<T> clazz) {
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

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance, int levelTree) throws Exception {
        Class<?> type = field.getType();
        DataGenerator generator = getGenerator(type);
        Object value = generator.getValue(field, dataFlags);
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
            if (generator.is(type)) {
                return generator;
            }
        }
        return new DataGenerator() {
            @Override
            protected boolean is(Class<?> type) {
                return true;
            }
        };
    }

    public <T> List<T> fill(int size, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(fill(clazz));
        }
        return list;
    }

    public RandomObject use(int flags) {
        this.dataFlags |= flags;
        return this;
    }

    private static RandomObject random() {
        return new RandomObject();
    }

    public RandomObject deepSameTypeFields(int deep) {
        processor = new Processor(deep);
        return this;
    }

    public RandomObject levelsTree(int i) {
        levelsTree = i;
        return this;
    }

    public static <T> One<T> one(Class<T> clazz) {
        return new One<>(clazz);
    }

    public static <T> Many<T> many(Class<T> clazz) {
        return new Many<>(clazz);
    }


    public static class One<T> {

        private final Class<T> clazz;
        private int levelsTree = 1;

        private One(Class<T> clazz) {
            this.clazz = clazz;
        }

        public T please() {
            T object = random().levelsTree(levelsTree).fill(clazz);
            return object;
        }

        public One<T> deep(int levelsTree) {
            this.levelsTree = levelsTree;
            return this;
        }

    }

    public static class Many<T> {
        private final Class<T> clazz;
        private int size;

        private Many(Class<T> clazz) {
            this.clazz = clazz;
        }

        public List<T> listOf(int size) {
            this.size = size;
            return please();
        }

        private List<T> please() {
            return random().fill(size, clazz);
        }
    }
}
