package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by crespo on 2/20/17.
 */
class DateDataGenerator extends RandomGenerator {

    DateDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Date.class);
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        return new Date(Math.abs(mRandomizer.nextLong()));
    }
}
