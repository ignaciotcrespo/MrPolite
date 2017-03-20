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
package com.github.ignaciotcrespo.mrpolite.generators;

import com.github.ignaciotcrespo.mrpolite.PoliteDesire;
import com.github.ignaciotcrespo.mrpolite.utils.PowerClass;
import com.github.ignaciotcrespo.mrpolite.utils.Randomizer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;

/**
 * Creates an ArrayList for List.
 */
class EnumerationDataGenerator extends RandomGenerator {

    EnumerationDataGenerator(Randomizer randomizer) {
        super(randomizer);
    }

    @Override
    public boolean canProcess(Class<?> type) {
        return type.equals(Enumeration.class);
    }

    @Override
    public Object getValue(PowerClass clazz, Type[] generics) {
        PoliteDesire<ArrayList, ArrayList> desire = one(ArrayList.class)
                .withSeed(mRandomizer.getSeed())
                .withCollectionSizeRange(mRandomizer.getCollectionRange().getMin(), mRandomizer.getCollectionRange().getMax());
        final ArrayList list;
        if (generics != null && generics.length > 0) {
            Class[] classes = new Class[generics.length];
            Arrays.asList(generics).toArray(classes);
            list = desire.withGenerics(classes).please();
        } else {
            list = desire.please();
        }
        return Collections.enumeration(list);
    }
}
