package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.PowerClass;
import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Type;
import java.util.Calendar;

/**
 * Created by crespo on 2/20/17.
 */
class CalendarDataGenerator extends RandomGenerator {

    CalendarDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Calendar.class);
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Math.abs(mRandomizer.nextLong()));
        return calendar;
    }
}
