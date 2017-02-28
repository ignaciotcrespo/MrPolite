package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class DataGenerator {

    public abstract boolean is(Class<?> type);

    public Object getValue(Field field) {
        return null;
    }
}
