package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

/**
 * Created by crespo on 2/20/17.
 */
class LongDataGenerator extends RandomGenerator {

    LongDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Long.TYPE) || type.equals(Long.class);
    }

    @Override
    public Object getValue(PowerField field, PowerClass fieldType) {
        return mRandomizer.nextLong();
    }

}
