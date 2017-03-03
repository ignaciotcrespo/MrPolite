package com.github.ignaciotcrespo.randomobject.generators;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class DataGenerator {

    public abstract boolean canProcess(Class<?> type);

    public Object getValue(Field field, Type fieldType) {
        return null;
    }
}
