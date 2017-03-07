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
package com.github.ignaciotcrespo.mrpolite.utils;

/**
 * Created by crespo on 2/22/17.
 */
public class NumberUtils {

    public static boolean isNumeric(Object str) {
        return str instanceof Number; // ("" + str).matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public static Object castedValue(Object object, Object oldValue) {
        if (isNumeric(object)) {
            Number newValue = (Number) object;
            if (oldValue instanceof Byte) {
                return newValue.byteValue();
            } else if (oldValue instanceof Short) {
                return newValue.shortValue();
            } else if (oldValue instanceof Integer) {
                return newValue.intValue();
            } else if (oldValue instanceof Long) {
                return newValue.longValue();
            } else if (oldValue instanceof Float) {
                return newValue.floatValue();
            } else {
                return newValue;
            }
        }
        return object;
    }
}
