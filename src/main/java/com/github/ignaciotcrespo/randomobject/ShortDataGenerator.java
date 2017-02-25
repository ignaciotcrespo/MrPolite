package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class ShortDataGenerator extends RandomGenerator {

    ShortDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Short.TYPE) || type.equals(Short.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        return (short) mRandomizer.nextInt();
    }

}
