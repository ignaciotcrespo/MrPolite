package com.github.ignaciotcrespo.randomobject;

import java.lang.reflect.Field;
import java.util.Calendar;

/**
 * Created by crespo on 2/20/17.
 */
class CalendarDataGenerator extends RandomGenerator {

    CalendarDataGenerator(int seed) {
        super(seed);
    }

    @Override
    protected boolean is(Class<?> type) {
        return type.equals(Calendar.class);
    }

    @Override
    public Object getValue(Field field, int dataFlags) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Math.abs(mRandomizer.nextLong()));
        return calendar;
    }
}
