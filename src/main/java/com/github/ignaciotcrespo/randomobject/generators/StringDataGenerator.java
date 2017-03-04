package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

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
    public Object getValue(PowerField field, PowerClass fieldType) {
        return new UUID(mRandomizer.nextLong(), mRandomizer.nextLong()).toString();
    }

}
