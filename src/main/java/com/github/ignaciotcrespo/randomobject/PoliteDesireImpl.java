package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.annotations.VisibleForTesting;
import com.github.ignaciotcrespo.randomobject.constraints.Constraints;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/27/17.
 */
abstract class PoliteDesireImpl<C, V> implements PoliteDesire<C, V> {
    final Class<C> clazz;
    @VisibleForTesting
    private final RandomObject mRandom;
    private int depth = RandomObject.DEFAULT_DEPTH;
    @VisibleForTesting
    int seed;
    private Range collectionSizeRange = RandomObject.DEFAULT_COLLECTION_RANGE;
    private final List<String> excludeRegex = new ArrayList<>();
    private final List<Class<?>> excludeClasses = new ArrayList<>();
    private Class<?>[] generics = new Class[0];

    PoliteDesireImpl(Class<C> clazz) {
        this.clazz = clazz;
        mRandom = RandomObject.random();
    }

    RandomObject randomObject() {
        return mRandom
                .seed(seed)
                .depth(this.depth)
                .excludeRegex(excludeRegex)
                .excludeClasses(excludeClasses)
                .collectionSizeRange(collectionSizeRange)
                .generics(generics);
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
}
