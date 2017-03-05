package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;

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
