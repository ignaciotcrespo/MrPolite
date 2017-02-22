package com.github.ignaciotcrespo.randomobject;

import java.util.Random;

/**
 * Created by crespo on 2/21/17.
 */
abstract class NumbersConstraint<T extends Number> extends Constraint<T> {

    private final T min;
    private final T max;
    Random random = new Random();

    private NumbersConstraint(T min, T max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public T apply(T value) {
        double newValue = value.doubleValue();
        if (newValue < min.doubleValue() || newValue > max.doubleValue()) {
            double div = max.doubleValue() - min.doubleValue();
            if (div == 0) {
                newValue = min.doubleValue();
            } else {
                newValue = random.nextDouble() * div + min.doubleValue();
            }
            return castedValue(newValue);
        }
        return value;
    }

    protected abstract T castedValue(Double newValue);

    @Override
    public boolean canApply(T value) {
        return value instanceof Number;
    }

    public double getMin() {
        return min.doubleValue();
    }

    public double getMax() {
        return max.doubleValue();
    }

    static <T extends Number> NumbersConstraint from(T a, T b) {
        if (a instanceof Byte) {
            return new ByteConstraint(a.byteValue(), b.byteValue());
        } else if (a instanceof Short) {
            return new ShortConstraint(a.shortValue(), b.shortValue());
        } else if (a instanceof Integer) {
            return new IntegerConstraint(a.intValue(), b.intValue());
        } else if (a instanceof Long) {
            return new LongConstraint(a.longValue(), b.longValue());
        } else if (a instanceof Float) {
            return new FloatConstraint(a.floatValue(), b.floatValue());
        } else {
            return new DoubleConstraint(a.doubleValue(), b.doubleValue());
        }
    }

    private static class ByteConstraint extends NumbersConstraint<Byte> {


        private ByteConstraint(Byte min, Byte max) {
            super(min, max);
        }

        @Override
        protected Byte castedValue(Double newValue) {
            return newValue.byteValue();
        }
    }

    private static class ShortConstraint extends NumbersConstraint<Short> {

        private ShortConstraint(Short min, Short max) {
            super(min, max);
        }

        @Override
        protected Short castedValue(Double newValue) {
            return newValue.shortValue();
        }
    }

    private static class IntegerConstraint extends NumbersConstraint<Integer> {


        private IntegerConstraint(Integer min, Integer max) {
            super(min, max);
        }

        @Override
        protected Integer castedValue(Double newValue) {
            return newValue.intValue();
        }
    }

    private static class LongConstraint extends NumbersConstraint<Long> {

        private LongConstraint(Long min, Long max) {
            super(min, max);
        }

        @Override
        protected Long castedValue(Double newValue) {
            return newValue.longValue();
        }
    }

    private static class DoubleConstraint extends NumbersConstraint<Double> {

        private DoubleConstraint(Double min, Double max) {
            super(min, max);
        }

        @Override
        protected Double castedValue(Double newValue) {
            return newValue;
        }
    }

    private static class FloatConstraint extends NumbersConstraint<Float> {

        private FloatConstraint(Float min, Float max) {
            super(min, max);
        }

        @Override
        protected Float castedValue(Double newValue) {
            return newValue.floatValue();
        }
    }
}
