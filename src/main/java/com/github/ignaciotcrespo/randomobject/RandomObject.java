package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import static com.github.ignaciotcrespo.randomobject.utils.ClassUtils.isAbstract;

/**
 * Created by crespo on 2/18/17.
 */
public class RandomObject {

    Processor processor = new Processor(0);
    private int dataFlags;
    int levelsTree = 1;

    DataGenerator[] generators;

    List<Constraint> constraints = new ArrayList<>();
    private int seed;

    private RandomObject() {
        // hide constructor
        initGenerators();
    }

    private void initGenerators() {
        generators = new DataGenerator[]{
                new EnumDataGenerator(seed),
                new BooleanDataGenerator(seed),
                new ByteDataGenerator(seed),
                new ShortDataGenerator(seed),
                new CharDataGenerator(seed),
                new IntegerDataGenerator(seed),
                new LongDataGenerator(seed),
                new FloatDataGenerator(seed),
                new DoubleDataGenerator(seed),
                new StringDataGenerator(seed),
                new DateDataGenerator(seed),
                new CalendarDataGenerator(seed)
        };
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
                if (!field.getType().isPrimitive() && isAbstract(field.getType())) {
                    // dont change value in fields with abstract type
                    continue;
                }
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                Object value = null;
                try {
                    field.setAccessible(true);
                    value = getRandomValueForField(parent, field, instance, levelTree);
                    field.set(instance, value);
                } catch (Exception e) {
                    e.printStackTrace();
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
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value);
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


    public static class One<T> extends BaseRandom<T> {

        private One(Class<T> clazz) {
            super(clazz);
        }

        public T please() {
            return mRandom.seed(seed).levelsTree(this.levelsTree).fill(clazz);
        }

        @Override
        public One<T> withDepth(int levelsTree) {
            return (One<T>) super.withDepth(levelsTree);
        }

        @Override
        public One<T> withNumberRange(Number min, Number max) {
            return (One<T>) super.withNumberRange(min, max);
        }

        @Override
        public One<T> withStringsMaxLength(int len) {
            return (One<T>) super.withStringsMaxLength(len);
        }

        @Override
        public One<T> withFieldNamesInStrings() {
            return (One<T>) super.withFieldNamesInStrings();
        }

        @Override
        public One<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            return (One<T>) super.withFieldEqualTo(fieldNameRegex, value);
        }

        @Override
        public <K> One<T> withClassEqualTo(Class<K> clazz, K value) {
            return (One<T>) super.withClassEqualTo(clazz, value);
        }

        @Override
        public One<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            return (One<T>) super.withFieldImageLink(fieldNameRegex, width, height);
        }

        @Override
        public One<T> withSeed(int seed) {
            return (One<T>) super.withSeed(seed);
        }
    }

    private RandomObject seed(int seed) {
        this.seed = seed;
        initGenerators();
        return this;
    }

    private void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public static class Many<T> extends BaseRandom<T> {
        private int size;

        private Many(Class<T> clazz) {
            super(clazz);
        }

        public List<T> listOf(int size) {
            this.size = size;
            return please();
        }

        @Override
        public Many<T> withDepth(int levelsTree) {
            return (Many<T>) super.withDepth(levelsTree);
        }

        private List<T> please() {
            return random().seed(seed).levelsTree(this.levelsTree).fill(size, clazz);
        }

        @Override
        public Many<T> withNumberRange(Number min, Number max) {
            return (Many<T>) super.withNumberRange(min, max);
        }

        @Override
        public Many<T> withStringsMaxLength(int len) {
            return (Many<T>) super.withStringsMaxLength(len);
        }

        @Override
        public Many<T> withFieldNamesInStrings() {
            return (Many<T>) super.withFieldNamesInStrings();
        }

        @Override
        public Many<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            return (Many<T>) super.withFieldEqualTo(fieldNameRegex, value);
        }

        @Override
        public <K> Many<T> withClassEqualTo(Class<K> clazz, K value) {
            return (Many<T>) super.withClassEqualTo(clazz, value);
        }

        @Override
        public Many<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            return (Many<T>) super.withFieldImageLink(fieldNameRegex, width, height);
        }

        @Override
        public Many<T> withSeed(int seed) {
            return (Many<T>) super.withSeed(seed);
        }
    }

    private static class BaseRandom<T> {
        final Class<T> clazz;
        RandomObject mRandom;
        int levelsTree = 1;
        int seed;

        private BaseRandom(Class<T> clazz) {
            this.clazz = clazz;
            mRandom = random();
        }

        public BaseRandom<T> withDepth(int levelsTree) {
            this.levelsTree = levelsTree;
            return this;
        }

        public BaseRandom<T> withNumberRange(Number min, Number max) {
            mRandom.addConstraint(NumbersConstraint.from(min, max));
            return this;
        }

        public BaseRandom<T> withStringsMaxLength(int len) {
            mRandom.addConstraint(new StringLengthConstraint(len));
            return this;
        }

        public BaseRandom<T> withFieldNamesInStrings() {
            mRandom.addConstraint(new StringFieldNameConstraint());
            return this;
        }

        public BaseRandom<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, value));
            return this;
        }

        public <K> BaseRandom<T> withClassEqualTo(Class<K> clazz, K value) {
            mRandom.addConstraint(new TypeValueConstraint(clazz, value));
            return this;
        }

        public BaseRandom<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, "http://lorempixel.com/" + width + "/" + height));
            return this;
        }

        public BaseRandom<T> withSeed(int seed) {
            this.seed = seed;
            return this;
        }
    }
}
