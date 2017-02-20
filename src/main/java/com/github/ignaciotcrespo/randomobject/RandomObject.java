package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by crespo on 2/18/17.
 */
public class RandomObject {

    Processor processor = new Processor(0);
    private int dataFlags;

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

    public <T> T fillInnerClass(Object parent, Class<T> clazz) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls);
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
                processFieldsAndParents(parent, clazz, instance);
            } else {
                Class superClazz = parent.getClass().getSuperclass();
                while (superClazz != null) {
                    for (Class<?> cls : superClazz.getDeclaredClasses()) {
                        if (clazz.equals(cls)) {
                            if (Modifier.isStatic(cls.getModifiers())) {
                                return fill((Class<T>) cls);
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
                        processFieldsAndParents(parent, clazz, instance);
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

    private <T> void processFieldsAndParents(Object parent, Class<T> clazz, T instance) {
        if (instance != null) {
            processFields(parent, clazz, instance);
            processSuperClasses(parent, clazz, instance);
        }
    }

    private <T> void processSuperClasses(Object parent, Class<T> clazz, T instance) {
        Class<?> superclazz = clazz.getSuperclass();
        while (superclazz != null) {
            processFields(parent, superclazz, instance);
            superclazz = superclazz.getSuperclass();
        }
    }

    private void processFields(Object parent, Class<?> clazz, Object instance) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getName().startsWith("this$")) {
                // avoid inner class reference to outer class
                continue;
            }
            if (processor.shouldStopNestedClasses(field, clazz)) {
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
                value = getRandomValueForField(parent, field, instance);
                field.set(instance, value);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }
    }

    public <T> T fill(Class<T> clazz) {
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return null;
        }
        T instance = ClassUtils.newInstance(clazz);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance);
        }
        return instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance) throws Exception {
        Class<?> type = field.getType();
        DataGenerator generator = getGenerator(type);
        Object value = generator.getValue(field, dataFlags);
        if (value == null) {
            if (parentInnerClass != null) {
                value = fillInnerClass(parentInnerClass, type);
            } else {
                value = fill(type);
                if (value == null) {
                    value = fillInnerClass(instance, type);
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

    public static RandomObject random() {
        return new RandomObject();
    }

    public RandomObject deepSameTypeFields(int deep) {
        processor = new Processor(deep);
        return this;
    }
}
