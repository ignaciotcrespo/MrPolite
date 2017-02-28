package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class FloatDataGenerator extends RandomGenerator {

    FloatDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Float.TYPE) || type.equals(Float.class);
    }

    @Override
    public Object getValue(Field field) {
        return mRandomizer.nextFloat();
    }

}
