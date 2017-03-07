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

import com.github.ignaciotcrespo.mrpolite.annotations.VisibleForTesting;
import com.github.ignaciotcrespo.mrpolite.constraints.Constraints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/27/17.
 */
abstract class PoliteDesireImpl<C, V> implements PoliteDesire<C, V> {
    @VisibleForTesting
    private final RandomObject mRandom;
    private int depth = RandomObject.DEFAULT_DEPTH;
    @VisibleForTesting
    int seed;
    private Range collectionSizeRange = RandomObject.DEFAULT_COLLECTION_RANGE;
    private final List<String> excludeRegex = new ArrayList<String>();
    private final List<Class<?>> excludeClasses = new ArrayList<Class<?>>();
    private Class<?>[] generics = new Class[0];
    private boolean override = true;
    private boolean overrideFinals;

    public <T> PoliteDesireImpl() {
        mRandom = RandomObject.newInstance();
    }

    RandomObject randomObject() {
        return mRandom
                .seed(seed)
                .depth(this.depth)
                .excludeRegex(excludeRegex)
                .excludeClasses(excludeClasses)
                .collectionSizeRange(collectionSizeRange)
                .generics(generics)
                .overrideValues(override)
                .overrideFinals(overrideFinals);
    }

    @Override
    public PoliteDesire<C, V> withDepth(int depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public PoliteDesire<C, V> withNumberRange(Number min, Number max) {
        mRandom.addConstraint(Constraints.numbersConstraint(min, max));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withStringsMaxLength(int len) {
        mRandom.addConstraint(Constraints.stringLengthConstraint(len));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldNamesInStrings() {
        mRandom.addConstraint(Constraints.stringFieldNameConstraint());
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldEqualTo(String fieldNameRegex, Object value) {
        mRandom.addConstraint(Constraints.fieldNameRegexConstraint(fieldNameRegex, value));
        return this;
    }

    @Override
    public <K> PoliteDesire<C, V> withClassEqualTo(Class<K> clazz, K value) {
        mRandom.addConstraint(Constraints.typeValueConstraint(clazz, value));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldImageLink(String fieldNameRegex, int width, int height) {
        mRandom.addConstraint(Constraints.randoImageConstraint(fieldNameRegex, width, height));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withSeed(int seed) {
        this.seed = seed;
        return this;
    }

    @Override
    public PoliteDesire<C, V> withCollectionSizeRange(int min, int max) {
        collectionSizeRange = new Range(min, max);
        return this;
    }

    @Override
    public PoliteDesire<C, V> exclude(String fieldNameRegex) {
        excludeRegex.add(fieldNameRegex);
        return this;
    }

    @Override
    public PoliteDesire<C, V> exclude(Class<?> clazz) {
        excludeClasses.add(clazz);
        return this;
    }

    @Override
    public PoliteDesire<C, V> withGenerics(Class<?>... clazz) {
        generics = clazz;
        return this;
    }

    @Override
    public PoliteDesire<C, V> overrideValues(boolean override) {
        this.override = override;
        return this;
    }

    @Override
    public PoliteDesire<C, V> overrideFinals() {
        this.overrideFinals = true;
        return this;
    }
}
