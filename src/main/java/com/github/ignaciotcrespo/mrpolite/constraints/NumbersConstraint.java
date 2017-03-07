/**
 * The MIT License
 * <p>
 * Copyright (c) 2017, Ignacio Tomas Crespo (itcrespo@gmail.com)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.ignaciotcrespo.mrpolite.constraints;

import com.github.ignaciotcrespo.mrpolite.utils.NumberUtils;
import com.github.ignaciotcrespo.mrpolite.utils.PowerField;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

import java.util.Random;

/**
 * Created by crespo on 2/21/17.
 */
class NumbersConstraint extends Constraint {

    private final Number min;
    private final Number max;
    private final Random random = new Random();

    private NumbersConstraint(Number min, Number max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Object apply(PowerField field, Object value, Randomizer randomizer) {
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

    static NumbersConstraint from(Number a, Number b) {
        return new NumbersConstraint(a, b);
    }

}
