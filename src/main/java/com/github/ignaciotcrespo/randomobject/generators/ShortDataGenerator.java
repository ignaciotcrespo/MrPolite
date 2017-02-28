package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class ShortDataGenerator extends RandomGenerator {

    ShortDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Short.TYPE) || type.equals(Short.class);
    }

    @Override
    public Object getValue(Field field) {
        return (short) mRandomizer.nextInt();
    }

}
