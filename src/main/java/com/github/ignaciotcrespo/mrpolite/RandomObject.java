package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.constraints.Constraint;
import com.github.ignaciotcrespo.mrpolite.generators.DataGenerator;
import com.github.ignaciotcrespo.mrpolite.generators.Generators;
import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import com.github.ignaciotcrespo.mrpolite.utils.PowerField;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

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

    private final List<Constraint> constraints = new ArrayList<Constraint>();
    private Range collectionSizeRange = DEFAULT_COLLECTION_RANGE;
    private List<String> excludeFields = new ArrayList<String>();
    private List<Class<?>> excludeClasses = new ArrayList<Class<?>>();
    private Randomizer randomizer = new Randomizer();
    private Class<?>[] genericTypesInClass = new Class[0];
    private boolean override = true;

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
                GeneratedValue generatedValue = getRandomObjectOrArray(field);
                field.setValue(generatedValue, override);
            }
        }
    }

    private GeneratedValue getRandomObjectOrArray(PowerField field) {
        GeneratedValue generatedValue;
        if (field.isArray()) {
            generatedValue = getRandomArray(field);
        } else {
            generatedValue = getRandomObject(field);
        }
        return generatedValue;
    }

    private boolean isExcludedField(PowerField field) {
        return field.isNameIn(excludeFields)
                || field.isClassIn(excludeClasses)
                || field.isInvalid();
    }

    private GeneratedValue getRandomArray(PowerField field) {
        GeneratedValue generatedValue = new GeneratedValue();
        Object array = PowerClass.newArray(field.getType(), getRandomCollectionSize());
        randomizeArray(array, field);
        generatedValue.setValue(array);
        return generatedValue;
    }

    private GeneratedValue getRandomObject(PowerField field) {
        GeneratedValue generatedValue = generateValue(field, field.getRawType(), field.getType(), field.getGenerics());
        if (generatedValue.getValue() == null) {
            generatedValue = randomizeClass(field.getType());
        } else {
            randomizeCollection(generatedValue.getValue(), field.getGenerics());
        }
        return generatedValue;
        }

    <T> T randomObject(Class<T> clazz) {
        GeneratedValue generatedValue = randomizeClass(new PowerClass(clazz, genericTypesInClass));
        return (T) generatedValue.getValue();
    }

    private GeneratedValue randomizeClass(PowerClass clazz) {
        GeneratedValue generatedValue = getRandomObject(clazz);
        randomizeObject(clazz, generatedValue);
        return generatedValue;
    }

    private void randomizeObject(PowerClass clazz, GeneratedValue generatedValue) {
        processFieldsAndParents(clazz);
        randomizeCollection(generatedValue.getValue(), clazz.getGenerics());
    }

    private void randomizeCollection(Object collection, Type[] genericTypes) {
        if (collection instanceof Collection || collection instanceof Map) {
            List items = new ArrayList();
            int randomCollectionSize = getRandomCollectionSize();
            for (int i = 0; i < randomCollectionSize; i++) {
                Type type = genericTypes.length > 0 ? genericTypes[0] : Object.class;
                GeneratedValue generatedValue = randomizeType(type);
                items.add(generatedValue.getValue());
            }
            if (collection instanceof List) {
                List list = (List) collection;
                list.addAll(items);
            }
            if (collection instanceof Set) {
                Set set = (Set) collection;
                set.addAll(items);
            }
            if (collection instanceof Map) {
                Map map = (Map) collection;
                for (int i = 0; i < randomCollectionSize; i++) {
                    Type type = genericTypes.length > 1 ? genericTypes[1] : Object.class;
                    GeneratedValue generatedValue = randomizeType(type);
                    map.put(items.get(i), generatedValue.getValue());
                }
            }
        }
    }

    private GeneratedValue randomizeType(Type type) {
        if (type instanceof ParameterizedType) {
            return randomizeParameterizedType((ParameterizedType) type);
        } else {
            return randomizeClass(new PowerClass((Class) type, ((Class) type).getTypeParameters()));
        }
    }

    private GeneratedValue randomizeParameterizedType(ParameterizedType type) {
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
                    Array.set(array, i, getRandomObject(field).getValue());
                }
            }
        }
    }

    private int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private GeneratedValue generateValue(PowerField field, PowerClass rawType, PowerClass type, Type[] generics) {
        GeneratedValue generatedValue = new GeneratedValue();
        DataGenerator generator = getDataGenerator(rawType);
        generatedValue.setValue(generator.getValue(type, generics));
        if (generatedValue.getValue() != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(generatedValue.getValue())) {
                    Object apply = constraint.apply(field, generatedValue.getValue(), randomizer);
                    generatedValue.setFromConstraint(!generatedValue.getValue().equals(apply));
                    generatedValue.setValue(apply);
                }
            }
        }
        return generatedValue;
    }

    private GeneratedValue getRandomObject(PowerClass type) {
        Type[] generics = type.getGenerics();
        GeneratedValue generatedValue = generateValue(null, type, type, generics);
        if (generatedValue.getValue() == null) {
            generatedValue.setValue(type.newInstance());
        }
        return generatedValue;
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
        List<T> list = new ArrayList<T>();
        for (int i = 0; i < size; i++) {
            list.add(randomObject(clazz));
        }
        return list;
    }

    <T> T[] randomArray(int size, Class<T> clazz) {
        T[] list = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            list[i] = randomObject(clazz);
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

    public <T> T randomObject(T objectToChange) {
        PowerClass clazz = new PowerClass(objectToChange, genericTypesInClass);
        GeneratedValue generatedValue = new GeneratedValue();
        generatedValue.setValue(objectToChange);
        randomizeObject(clazz, generatedValue);
        return objectToChange;
    }

    public RandomObject overrideValues(boolean override) {
        this.override = override;
        return this;
    }
}
