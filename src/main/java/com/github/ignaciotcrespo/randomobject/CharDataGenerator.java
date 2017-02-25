package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class CharDataGenerator extends RandomGenerator {

    CharDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Character.TYPE) || type.equals(Character.class);
    }

    @Override
    public Object getValue(Field field) {
        return (char) (mRandomizer.nextInt(26) + 'a');
    }

}
