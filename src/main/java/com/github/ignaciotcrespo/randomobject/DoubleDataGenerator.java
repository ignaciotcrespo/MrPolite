package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
public class DoubleDataGenerator extends DataGenerator {

    Randomizer mRandomizer = new Randomizer();

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Double.TYPE) || type.equals(Double.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        return mRandomizer.nextDouble();
    }

}
