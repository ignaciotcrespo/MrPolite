package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class FloatDataGenerator extends RandomGenerator {

    FloatDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Float.TYPE) || type.equals(Float.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextFloat();
    }

}
