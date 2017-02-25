package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class BooleanDataGenerator extends RandomGenerator {

    BooleanDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Boolean.TYPE) || type.equals(Boolean.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextBoolean();
    }
}
