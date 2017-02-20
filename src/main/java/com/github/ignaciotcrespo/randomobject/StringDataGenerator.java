package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by crespo on 2/20/17.
 */
public class StringDataGenerator extends DataGenerator {

    public static int DATA_UUID = 1;
    public static int DATA_FIELD_NAME = 2;

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(String.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        if ((dataFlags & DATA_FIELD_NAME) != 0) {
            return field.getName();
        } else {
            return UUID.randomUUID().toString();
        }
    }

}
