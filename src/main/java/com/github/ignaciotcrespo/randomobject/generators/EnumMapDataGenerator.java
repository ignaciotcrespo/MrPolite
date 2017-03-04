package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

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
    public Object getValue(PowerField field, PowerClass fieldType) {
        return new EnumMap(field.getGeneric(0));
    }
}
