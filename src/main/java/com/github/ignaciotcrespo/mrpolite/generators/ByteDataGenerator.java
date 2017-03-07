package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

import java.lang.reflect.Type;

/**
 * Created by crespo on 2/20/17.
 */
class ByteDataGenerator extends RandomGenerator {

    ByteDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Byte.TYPE) || type.equals(Byte.class);
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        return (byte) mRandomizer.nextInt();
    }

}
