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

    static final int DEFAULT_DEPTH = 3;

    private int depth = DEFAULT_DEPTH;

    private DataGenerator[] generators;

    private final List<Constraint> constraints = new ArrayList<Constraint>();
    private List<String> excludeFields = new ArrayList<String>();
    private List<Class<?>> excludeClasses = new ArrayList<Class<?>>();
    private Randomizer randomizer = new Randomizer();
    private Class<?>[] genericTypesInClass = new Class[0];
    private boolean override = true;
    private boolean overrideFinals;
    private List<DataGenerator> newGenerators = new ArrayList<DataGenerator>();

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
                || field.isInvalid(overrideFinals);
    }

    private GeneratedValue getRandomArray(PowerField field) {
        GeneratedValue generatedValue = new GeneratedValue();
        Object array = PowerClass.newArray(field.getType(), randomizer.getRandomCollectionSize());
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
            int randomCollectionSize = randomizer.getRandomCollectionSize();
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

    private GeneratedValue generateValue(PowerField field, PowerClass rawType, PowerClass type, Type[] generics) {
        GeneratedValue generatedValue = new GeneratedValue();
        DataGenerator generator = getDataGenerator(rawType);
        generatedValue.setValue(generator.getValue(type, generics));
        Object value = generatedValue.getValue();
        for (Constraint constraint : constraints) {
            if (value != null && constraint.canApply(value)) {
                applyConstraint(field, generatedValue, constraint);
            } else if (value == null && constraint.canApplyType(type)) {
                applyConstraint(field, generatedValue, constraint);
            }
        }
        return generatedValue;
    }

    private void applyConstraint(PowerField field, GeneratedValue generatedValue, Constraint constraint) {
        Object apply = constraint.apply(field, generatedValue.getValue(), randomizer);
        generatedValue.setFromConstraint(!apply.equals(generatedValue.getValue()));
        generatedValue.setValue(apply);
    }

    private GeneratedValue getRandomObject(PowerClass type) {
        Type[] generics = type.getGenerics();
        GeneratedValue generatedValue = generateValue(null, type, type, generics);
        if (generatedValue.getValue() == null) {
            generatedValue.setValue(type.newInstance(randomizer));
        }
        return generatedValue;
    }

    private DataGenerator getDataGenerator(PowerClass type) {
        for (DataGenerator generator : newGenerators) {
            if (type.canGenerateData(generator)) {
                return generator;
            }
        }
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

    RandomObject depth(int i) {
        depth = i;
        return this;
    }


    RandomObject collectionSizeRange(Range collectionSizeRange) {
        randomizer.setRandomCollectionSize(collectionSizeRange);
        return this;
    }

    RandomObject seed(long seed) {
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

    public RandomObject overrideFinals(boolean overrideFinals) {
        this.overrideFinals = overrideFinals;
        return this;
    }

    public RandomObject withDataGenerators(List<DataGenerator> newGenerators) {
        this.newGenerators = newGenerators;
        return this;
    }
}

