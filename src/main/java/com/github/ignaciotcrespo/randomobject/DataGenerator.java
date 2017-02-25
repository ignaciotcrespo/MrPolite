package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
abstract class DataGenerator {

    protected abstract boolean is(Class<?> type);

    public Object getValue(Field field) {
        return null;
    }
}
