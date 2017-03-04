package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

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
    public Object getValue(PowerField field, PowerClass fieldType) {
        Object[] enumValues = fieldType.getEnumConstants();
        return enumValues[mRandomizer.nextInt(enumValues.length)];
    }
}
