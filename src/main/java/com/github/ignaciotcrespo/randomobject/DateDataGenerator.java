package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * Created by crespo on 2/20/17.
 */
class DateDataGenerator extends RandomGenerator {

    DateDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Date.class);
    }

    @Override
    public Object getValue(Field field) {
        return new Date(Math.abs(mRandomizer.nextLong()));
    }
}
