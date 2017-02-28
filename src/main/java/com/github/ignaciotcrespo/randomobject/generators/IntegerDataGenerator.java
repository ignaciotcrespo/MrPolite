package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class IntegerDataGenerator extends RandomGenerator {

    IntegerDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Integer.TYPE) || type.equals(Integer.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextInt();
    }

}
