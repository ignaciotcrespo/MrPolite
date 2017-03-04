package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.PowerClass;
import com.github.ignaciotcrespo.randomobject.PowerField;

/**
 * Created by crespo on 2/20/17.
 */
public abstract class DataGenerator {

    public abstract boolean canProcess(Class<?> type);

    public Object getValue(PowerField field, PowerClass fieldType) {
        return null;
    }
}
