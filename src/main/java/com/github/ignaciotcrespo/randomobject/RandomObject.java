package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.constraints.Constraint;
import com.github.ignaciotcrespo.randomobject.generators.DataGenerator;
import com.github.ignaciotcrespo.randomobject.generators.Generators;
import com.github.ignaciotcrespo.randomobject.utils.ClassUtils;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.*;
import java.util.*;

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
    private Class<?>[] genericTypesInClass = new Class[0];

    private RandomObject() {
        // hide constructor
        initGenerators();
    }

    private void initGenerators() {
        generators = Generators.createDefault(randomizer);
    }

    private <T> T fillInnerClass(Object parent, Class<T> clazz, int depth, Type[] genericTypesInClass) {
        T instance = null;
        try {
            for (Class<?> cls : parent.getClass().getDeclaredClasses()) {
                if (clazz.equals(cls)) {
                    if (Modifier.isStatic(cls.getModifiers())) {
                        return fill((Class<T>) cls, depth, genericTypesInClass);
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
                processFieldsAndParents(parent, clazz, instance, depth, genericTypesInClass);
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
                        processFieldsAndParents(parent, clazz, instance, depth, genericTypesInClass);
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

    private <T> void processFieldsAndParents(Object parent, Class<?> clazz, T instance, int depth, Type[] genericTypesInClass) {
        if (instance != null && depth < this.depth) {
            processFields(parent, clazz, instance, depth, genericTypesInClass);
            processSuperClasses(parent, clazz, instance, depth, genericTypesInClass);
        }
    }

    private boolean isValidClass(Class<?> clazz) {
        return !clazz.getName().startsWith("java.")
                && !clazz.getName().startsWith("javax.")
                && !clazz.getName().startsWith("android.")
                && !clazz.getName().startsWith("com.android.")
                && !clazz.getName().startsWith("dalvik.");
    }

    private <T> void processSuperClasses(Object parent, Class<?> clazz, T instance, int depth, Type[] genericTypesInClass) {
        TypeVariable<?>[] clazzGenericParameters = clazz.getTypeParameters();
        Map<TypeVariable<?>, Type> clazzGenericParametersMap = new HashMap<>();
        for (int i = 0; i < genericTypesInClass.length; i++) {
            clazzGenericParametersMap.put(clazzGenericParameters[i], genericTypesInClass[i]);
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
        depth++;
        if (superclazz != null && isValidClass(superclazz) && depth < this.depth) {
            processFields(parent, superclazz, instance, depth, genericTypesInSuperClass);
            processSuperClasses(parent, superclazz, instance, depth, genericTypesInSuperClass);
        }
    }

    private void processFields(Object parent, Class<?> clazz, Object instance, int depth, Type[] genericTypesInClass) {
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
                if (isInvalidGeneric(field, genericTypesInClass) || isInvalidNumberOfGenerics(field, genericTypesInClass)) {
                    continue;
                }
                Object value;
                Class<?> fieldType = getFieldType(field, genericTypesInClass);
                if (!fieldType.isPrimitive() && isAbstract(fieldType)) {
                    if (fieldType.getName().equals(List.class.getName())) {
                        fieldType = ArrayList.class;
                    } else if (fieldType.getName().equals(Set.class.getName())) {
                        fieldType = HashSet.class;
                    } else if (fieldType.getName().equals(Queue.class.getName())) {
                        fieldType = LinkedList.class;
                    } else if (fieldType.getName().equals(Map.class.getName())) {
                        fieldType = HashMap.class;
                    } else {
                        // dont change value in fields with abstract type
                        continue;
                    }
                }
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Type[] genericTypesInField = new Type[0];
                if (isGenericFieldWithParameters(field, genericTypesInClass)) {
                    if(field.getGenericType() instanceof TypeVariable){
                        genericTypesInField = ((ParameterizedType) genericTypesInClass[0]).getActualTypeArguments();
                    }else {
                        genericTypesInField = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
                    }
                }
                value = getRandomValueForField(parent, field, instance, depth, genericTypesInField, genericTypesInClass, fieldType);
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
            if (getFieldType(field, null).equals(clazz)) {
                return true;
            }
        }
        return false;
    }

    <T> T fill(Class<T> clazz) {
        return fill(clazz, 0);
    }

    private <T> T fill(Class<T> clazz, int depth) {
        return fill(clazz, depth, genericTypesInClass);
    }

    private <T> T fill(Class<T> clazz, int depth, Type[] genericTypesInClass) {
        if (Modifier.isPrivate(clazz.getModifiers())) {
            return null;
        }
        Object instance = getRandomValueForType(depth, clazz, genericTypesInClass);

        if (instance != null) {
            processFieldsAndParents(null, clazz, instance, depth, genericTypesInClass);
        }

        tryToFillCollection(instance, depth, genericTypesInClass);

        return (T) instance;
    }

    private Object getRandomValueForField(Object parentInnerClass, Field field, Object instance, int depth, Type[] genericTypesInField, Type[] genericTypesInClass, Class<?> fieldType) {
        if (fieldType.isArray()) {
            Object newInstance = createArrayWithDefaultValues(fieldType);
            Class<?> arrayType = getArrayType(fieldType);
            fillArrayWithValues(newInstance, arrayType, parentInnerClass, field, instance, depth);
            return newInstance;
        } else {
            return getRandomValueForFieldType(parentInnerClass, field, instance, depth, fieldType, genericTypesInField);
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
                    item = fill(rawType, depth, actualTypeArguments);
                } else {
                    item = fill((Class) type, depth, ((Class) type).getTypeParameters());
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
                        item = fill((Class) ((ParameterizedType) type).getRawType(), depth, ((ParameterizedType) type).getActualTypeArguments());
                    } else {
                        item = fill((Class) type, depth, ((Class) type).getTypeParameters());
                    }
                    map.put(items.get(i), item);
                }
            }
//                fillArrayWithValues(value, null, parentInnerClass, field, instance, depth);
        }
    }

    private boolean isInvalidGeneric(Field field, Type[] genericTypesInClass) {
        boolean hasGenericSet = genericTypesInClass.length > 0;
        return isGenericField(field) && !hasGenericSet;
    }

    private boolean isInvalidNumberOfGenerics(Field field, Type[] genericTypesInClass) {
        if (isGenericField(field)) {
            TypeVariable<? extends Class<?>>[] genericTypes = field.getDeclaringClass().getTypeParameters();
            return genericTypes != null && genericTypes.length != genericTypesInClass.length;
        }
        return false;
    }

    private boolean isGenericField(Field field) {
        Type fieldGenericType = field.getGenericType();
        return fieldGenericType != null && (fieldGenericType instanceof TypeVariable);
    }

    private boolean isGenericFieldWithParameters(Field field, Type[] genericTypesInClass) {
        Type fieldGenericType = field.getGenericType();
        if(fieldGenericType instanceof TypeVariable){
            return genericTypesInClass[0] instanceof ParameterizedType;
        }
        return fieldGenericType instanceof ParameterizedType;
    }

    private Class<?> getFieldType(Field field, Type[] genericTypesInClass) {
        Type fieldGenericType = field.getGenericType();
        boolean hasGenericSet = genericTypesInClass != null && genericTypesInClass.length > 0;
        boolean isInvalidGeneric = isGenericField(field) && !hasGenericSet;
        if (isInvalidGeneric) {
            return null;
        }
        if (hasGenericSet && isGenericField(field)) {
            Type[] genericTypes = field.getDeclaringClass().getTypeParameters();
            if (genericTypes != null && genericTypes.length == genericTypesInClass.length) {
                for (int i = 0; i < genericTypes.length; i++) {
                    Type genericType = genericTypes[i];
                    if (genericType.getTypeName().equals(fieldGenericType.getTypeName())) {
                        Type type = genericTypesInClass[i];
                        return getClassFromType(type);
                    }
                }
            }
        }
        return field.getType();
    }

    private Class<?> getClassFromType(Type type) {
        if (type instanceof WildcardType) {
            Type[] upper = ((WildcardType) type).getUpperBounds();
            if (upper != null && upper.length > 0) {
                return (Class<?>) upper[0];
            }
            Type[] lower = ((WildcardType) type).getLowerBounds();
            if (lower != null && lower.length > 0) {
                return (Class<?>) lower[0];
            }
        }
        if (type instanceof ParameterizedType) {
            return (Class<?>) ((ParameterizedType) type).getRawType();
        }
        return (Class) type;
    }

    private void fillArrayWithValues(Object array, Class<?> arrayType, Object parentInnerClass, Field field, Object instance, int depth) {
        if (array.getClass().isArray()) {
            int len = Array.getLength(array);
            for (int i = 0; i < len; i++) {
                Object value = Array.get(array, i);
                if (value == null || value.getClass().isPrimitive() || value instanceof Number) {
                    value = getRandomValueForFieldType(parentInnerClass, field, instance, depth, arrayType, null);
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

    int getRandomCollectionSize() {
        return randomizer.nextInt(collectionSizeRange.max - collectionSizeRange.min) + collectionSizeRange.min;
    }

    private Object getRandomValueForFieldType(Object parentInnerClass, Field field, Object instance, int depth, Class<?> fieldType, Type[] genericTypesInField) {
        DataGenerator generator = getGenerator(fieldType);
        Object value = generator.getValue(field, fieldType, genericTypesInField);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(field, value, randomizer);
                }
            }
        }
        if (value == null) {
            if (parentInnerClass != null) {
                value = fillInnerClass(parentInnerClass, fieldType, depth + 1, genericTypesInField);
            } else {
                value = fill(fieldType, depth + 1, genericTypesInField);
                if (value == null) {
                    value = fillInnerClass(instance, fieldType, depth + 1, genericTypesInField);
                }
            }
        } else {
            tryToFillCollection(value, depth, genericTypesInField);
        }
        return value;
    }

    private Object getRandomValueForType(int depth, Class<?> type, Type[] genericTypes) {
        DataGenerator generator = getGenerator(type);
        Object value = generator.getValue(null, type, genericTypes);
        if (value != null) {
            for (Constraint constraint : constraints) {
                if (constraint.canApply(value)) {
                    value = constraint.apply(null, value, randomizer);
                }
            }
        }
        if (value == null) {
            value = ClassUtils.newInstance(type);
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

