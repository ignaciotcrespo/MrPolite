package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by crespo on 2/20/17.
 */
class EnumDataGenerator extends RandomGenerator {

    EnumDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object getValue(Field field, Type fieldType, Type[] genericTypes) {
        if (fieldType instanceof Class) {
            Object[] enumValues = ((Class) fieldType).getEnumConstants();
            return enumValues[mRandomizer.nextInt(enumValues.length)];
        }
        return null;
    }
}
