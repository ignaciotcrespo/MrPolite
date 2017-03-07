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
package com.github.ignaciotcrespo.mrpolite;

import java.util.List;

/**
 * Mr Polite will give you anything you need, if you kindly ask for it.
 */
public class MrPolite {

    /**
     * Create one object.
     *
     * @param clazz the type of object
     * @return a {@link PoliteDesire} to continue adding conditions, or to build the object using {@link PoliteDesire#please()}
     */
    public static <T> PoliteDesire<T, T> one(Class<T> clazz) {
        return new One<T>(clazz);
    }

    /**
     * Create a list with objects.
     *
     * @param size  how many objects will contain the list
     * @param clazz the type of object
     * @return a {@link PoliteDesire} to continue adding conditions, or to build the object using {@link PoliteDesire#please()}
     */
    public static <T> PoliteDesire<T, List<T>> aListOf(int size, Class<T> clazz) {
        return new ListOf<T>(size, clazz);
    }

    /**
     * Create an array with objects.
     *
     * @param size  how many objects will contain the array
     * @param clazz the type of object
     * @return a {@link PoliteDesire} to continue adding conditions, or to build the object using {@link PoliteDesire#please()}
     */
    public static <T> PoliteDesire<T, T[]> anArrayOf(int size, Class<T> clazz) {
        return new ArrayOf<T>(size, clazz);
    }

    /**
     * Request to change an object.
     *
     * @param object the object to change
     * @return a {@link PoliteDesire} to continue adding conditions, or to build the object using {@link PoliteDesire#please()}
     */
    public static PoliteDesire<Object, Object> change(Object object) {
        return new Change<Object>(object);
    }
}
