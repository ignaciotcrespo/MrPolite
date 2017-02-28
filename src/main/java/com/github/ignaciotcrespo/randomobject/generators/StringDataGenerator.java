package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by crespo on 2/20/17.
 */
class StringDataGenerator extends RandomGenerator {

    StringDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    public Object getValue(Field field) {
        return new UUID(mRandomizer.nextLong(), mRandomizer.nextLong()).toString();
    }

}
