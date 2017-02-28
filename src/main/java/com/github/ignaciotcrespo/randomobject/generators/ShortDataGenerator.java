package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class ShortDataGenerator extends RandomGenerator {

    ShortDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean is(Class<?> type) {
        return type.equals(Short.TYPE) || type.equals(Short.class);
    }

    @Override
    public Object getValue(Field field) {
        return (short) mRandomizer.nextInt();
    }

}
