package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Type;

/**
 * Created by crespo on 2/20/17.
 */
class EnumDataGenerator extends RandomGenerator {

    EnumDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.isEnum();
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        Object[] enumValues = clazz.getEnumConstants();
        return enumValues[mRandomizer.nextInt(enumValues.length)];
    }
}
