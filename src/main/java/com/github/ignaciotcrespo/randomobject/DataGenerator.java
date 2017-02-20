package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class DataGenerator {

    protected abstract boolean is(Class<?> type);

    public Object getValue(Field field, int dataFlags) {
        return null;
    }
}
