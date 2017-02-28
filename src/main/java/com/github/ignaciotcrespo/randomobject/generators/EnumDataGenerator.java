package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class EnumDataGenerator extends RandomGenerator {

    EnumDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean is(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object getValue(Field field) {
        Object[] enumValues = field.getType().getEnumConstants();
        return enumValues[mRandomizer.nextInt(enumValues.length)];
    }
}
