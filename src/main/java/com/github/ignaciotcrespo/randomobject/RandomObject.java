package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.constraints.Constraint;
import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.generators.Generators;
import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
        initDataGenerators();
    }

    private void initDataGenerators() {
        generators = Generators.createDefault(randomizer);
    }

    private void processFieldsAndParents(PowerClass clazz) {
        if (clazz.hasCurrentInstance() && clazz.isValidDepth(depth)) {
            processFields(clazz);
            processSuperClasses(clazz);
        }
    }

    private void processSuperClasses(PowerClass clazz) {
        PowerClass superclazz = clazz.getSuperclass();
        if (superclazz.isValidPackage() && superclazz.isValidDepth(depth)) {
            processFields(superclazz);
            processSuperClasses(superclazz);
        }
    }

    private void processFields(PowerClass clazz) {
        if (clazz.isValidPackage()) {
            for (PowerField field : clazz.getDeclaredFields()) {
                if (isExcludedField(field)) {
                    continue;
                }
                Object value = getRandomObjectOrArray(field);
                field.setValue(value);
            }
        }
    }

    private Object getRandomObjectOrArray(PowerField field) {
        Object value;
        if (field.isArray()) {
            value = getRandomArray(field);
        } else {
            value = getRandomObject(field);
        }
        return value;
    }

    private boolean isExcludedField(PowerField field) {
        return field.isNameIn(excludeFields)
                || field.isClassIn(excludeClasses)
                || field.isInvalid();
    }

    private Object getRandomArray(PowerField field) {
        Object array = PowerClass.newArray(field.getType(), getRandomCollectionSize());
        randomizeArray(array, field);
        return array;
    }

    private Object getRandomObject(PowerField field) {
        Object value = generateValue(field, field.getRawType(), field.getType(), field.getGenerics());
        if (value == null) {
            value = randomizeClass(field.getType());
        } else {
            randomizeCollection(value, field.getGenerics());
        }
        return value;
    }

    <T> T randomCollection(Class<T> clazz) {
        return (T) randomizeClass(new PowerClass(clazz, genericTypesInClass));
    }

    private Object randomizeClass(PowerClass clazz) {
        Object instance = getRandomObject(clazz);
        processFieldsAndParents(clazz);
        randomizeCollection(instance, clazz.getGenerics());
        return instance;
    }

    private void randomizeCollection(Object value, Type[] genericTypes) {
        if (value instanceof Collection || value instanceof Map) {
            List items = new ArrayList();
            int randomCollectionSize = getRandomCollectionSize();
            for (int i = 0; i < randomCollectionSize; i++) {
                Type type = genericTypes.length > 0 ? genericTypes[0] : Object.class;
                Object item = randomizeType(type);
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
                    Object item = randomizeType(type);
                    map.put(items.get(i), item);
                }
            }
        }
    }

    private Object randomizeType(Type type) {
        if (type instanceof ParameterizedType) {
            return randomizeParameterizedType((ParameterizedType) type);
        } else {
            return randomizeClass(new PowerClass((Class) type, ((Class) type).getTypeParameters()));
        }
    }

    private Object randomizeParameterizedType(ParameterizedType type) {
        Type rawType = type.getRawType();
        Type[] actualTypeArguments = type.getActualTypeArguments();
        PowerClass clazz = new PowerClass((Class<?>) rawType, actualTypeArguments);
        return randomizeClass(clazz);
    }

    private void randomizeArray(Object array, PowerField field) {
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Object value = Array.get(array, i);
                if (value != null && value.getClass().isArray()) {
                    randomizeArray(value, field);
                } else {
                    Array.set(array, i, getRandomObject(field));
                }
            }
        }
    }

    private int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private Object generateValue(PowerField field, PowerClass rawType, PowerClass type, Type[] generics) {
        DataGenerator generator = getDataGenerator(rawType);
        Object value = generator.getValue(type, generics);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value, randomizer);
                }
            }
        }
        return value;
    }

    private Object getRandomObject(PowerClass type) {
        Type[] generics = type.getGenerics();
        Object value = generateValue(null, type, type, generics);
        if (value == null) {
            value = type.newInstance();
        }
        return value;
    }

    private DataGenerator getDataGenerator(PowerClass type) {
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

    <T> List<T> randomCollection(int size, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(randomCollection(clazz));
        }
        return list;
    }

    <T> T[] randomArray(int size, Class<T> clazz) {
        T[] list = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            list[i] = randomCollection(clazz);
        }
        return list;
    }

    static RandomObject newInstance() {
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
            initDataGenerators();
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

}

