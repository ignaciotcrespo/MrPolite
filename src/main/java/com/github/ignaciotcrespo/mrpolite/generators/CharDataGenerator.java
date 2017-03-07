package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

import java.lang.reflect.Type;

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
    public Object getValue(PowerClass clazz, Type[] generics) {
        return (char) (mRandomizer.nextInt(26) + 'a');
    }

}
