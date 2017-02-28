package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class ByteDataGenerator extends RandomGenerator {

    ByteDataGenerator(int seed) {
        super(seed);
    }

    @Override
    public boolean is(Class<?> type) {
        return type.equals(Byte.TYPE) || type.equals(Byte.class);
    }

    @Override
    public Object getValue(Field field) {
        return (byte) mRandomizer.nextInt();
    }

}
