package com.github.ignaciotcrespo.randomobject.generators;

import com.github.ignaciotcrespo.randomobject.utils.Randomizer;

import java.lang.reflect.Field;
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
    public Object getValue(Field field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Math.abs(mRandomizer.nextLong()));
        return calendar;
    }
}
