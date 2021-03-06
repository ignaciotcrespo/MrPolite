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

import static com.github.ignaciotcrespo.mrpolite.utils.NumberUtils.isNumeric;

/**
 * Created by crespo on 2/22/17.
 */
class FieldNameRegexConstraint extends Constraint {

    private final String fieldNameRegex;
    private final Object value;

    FieldNameRegexConstraint(String fieldNameRegex, Object value) {
        this.fieldNameRegex = fieldNameRegex;
        this.value = value;
    }

    @Override
    public Object apply(PowerField field, Object oldValue, Randomizer randomizer) {
        if (this.value == null) {
            if (field.isPrimitive()) {
                return 0;
            } else {
                return oldValue;
            }
        }
        if (field.getName().matches(fieldNameRegex) &&  isAssignableFrom(field, oldValue)) {
            if (NumberUtils.isNumeric(this.value)) {
                return NumberUtils.castedValue(this.value, oldValue);
            }
            return this.value;
        }

        return oldValue;
    }

    private boolean isAssignableFrom(PowerField field, Object oldValue) {
        return field.isPrimitive() && isNumeric(oldValue)
                || field.isAssignableFrom(this.value.getClass());
    }

    @Override
    public boolean canApply(Object value) {
        return true;
    }
}
