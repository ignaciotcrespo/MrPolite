package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

import java.lang.reflect.Type;
import java.util.EnumMap;

/**
 * Created by crespo on 2/20/17.
 */
class EnumMapDataGenerator extends RandomGenerator {

    EnumMapDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(EnumMap.class);
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        return new EnumMap((Class) generics[0]);
    }
}
