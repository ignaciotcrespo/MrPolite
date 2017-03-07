package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;

import java.lang.reflect.Type;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class DataGenerator {

    public abstract boolean canProcess(Class<?> type);

    public Object getValue(PowerClass clazz, Type[] generics) {
        return null;
    }
}
