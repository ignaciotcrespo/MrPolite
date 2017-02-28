package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class DoubleDataGenerator extends RandomGenerator {

    DoubleDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Double.TYPE) || type.equals(Double.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextDouble();
    }

}
