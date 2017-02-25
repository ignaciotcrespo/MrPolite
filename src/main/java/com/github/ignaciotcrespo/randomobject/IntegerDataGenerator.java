package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class IntegerDataGenerator extends RandomGenerator {

    IntegerDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Integer.TYPE) || type.equals(Integer.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        return mRandomizer.nextInt();
    }

}
