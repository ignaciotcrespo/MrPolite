package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
public class EnumDataGenerator extends DataGenerator {

    Randomizer mRandomizer = new Randomizer();

    @Override
    protected boolean is(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        Object[] enumValues = field.getType().getEnumConstants();
        return enumValues[mRandomizer.nextInt(enumValues.length)];
    }
}
