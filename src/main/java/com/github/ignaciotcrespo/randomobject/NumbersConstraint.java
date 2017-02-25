package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.utils.NumberUtils;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by crespo on 2/21/17.
 */
class NumbersConstraint extends Constraint {

    private final Number min;
    private final Number max;
    private Random random = new Random();

    private NumbersConstraint(Number min, Number max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Object apply(Field field, Object value) {
        double newValue = ((Number) value).doubleValue();
        if (newValue < min.doubleValue() || newValue > max.doubleValue()) {
            double div = max.doubleValue() - min.doubleValue();
            if (div == 0) {
                newValue = min.doubleValue();
            } else {
                newValue = random.nextDouble() * div + min.doubleValue();
            }
            return NumberUtils.castedValue(newValue, value);
        }
        return value;
    }

    @Override
    public boolean canApply(Object value) {
        return value instanceof Number;
    }

    double getMin() {
        return min.doubleValue();
    }

    double getMax() {
        return max.doubleValue();
    }

    static NumbersConstraint from(Number a, Number b) {
        return new NumbersConstraint(a, b);
    }

}
