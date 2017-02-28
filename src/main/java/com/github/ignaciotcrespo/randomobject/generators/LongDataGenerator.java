package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class LongDataGenerator extends RandomGenerator {

    LongDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean is(Class<?> type) {
        return type.equals(Long.TYPE) || type.equals(Long.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextLong();
    }

}
