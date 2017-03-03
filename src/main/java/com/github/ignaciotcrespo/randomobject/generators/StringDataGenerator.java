package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.UUID;

/**
 * Created by crespo on 2/20/17.
 */
class StringDataGenerator extends RandomGenerator {

    StringDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    public Object getValue(Field field, Type fieldType) {
        return new UUID(mRandomizer.nextLong(), mRandomizer.nextLong()).toString();
    }

}
