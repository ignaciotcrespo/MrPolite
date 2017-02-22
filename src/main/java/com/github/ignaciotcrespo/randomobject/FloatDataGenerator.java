package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
public class FloatDataGenerator extends DataGenerator {

    Randomizer mRandomizer = new Randomizer();

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Float.TYPE) || type.equals(Float.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        return mRandomizer.nextFloat();
    }

}
