package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Type;

/**
 * Created by crespo on 3/5/17.
 */
public class ArrayDataGenerator extends RandomGenerator {

    ArrayDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.isArray();
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        return PowerClass.newArray(clazz, 3);
    }
}
