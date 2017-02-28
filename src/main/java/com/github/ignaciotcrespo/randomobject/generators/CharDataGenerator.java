package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
class CharDataGenerator extends RandomGenerator {

    CharDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Character.TYPE) || type.equals(Character.class);
    }

    @Override
    public Object getValue(Field field) {
        return (char) (mRandomizer.nextInt(26) + 'a');
    }

}
