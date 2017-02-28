package com.github.ignaciotcrespo.randomobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/27/17.
 */
abstract class PoliteDesireImpl<C, V> implements PoliteDesire<C, V> {
    final Class<C> clazz;
    @VisibleForTesting
    final RandomObject mRandom;
    private int levelsTree = 1;
    @VisibleForTesting
    int seed;
    private RandomObject.Range collectionSizeRange = RandomObject.DEFAULT_COLLECTION_RANGE;
    private final List<String> excludeRegex = new ArrayList<String>();
    private final List<Class<?>> excludeClasses = new ArrayList<Class<?>>();

    PoliteDesireImpl(Class<C> clazz) {
        this.clazz = clazz;
        mRandom = RandomObject.random();
    }

    RandomObject randomObject() {
        return mRandom
                .seed(seed)
                .levelsTree(this.levelsTree)
                .excludeRegex(excludeRegex)
                .excludeClasses(excludeClasses)
                .collectionSizeRange(collectionSizeRange);
    }

    @Override
    public PoliteDesire<C, V> withDepth(int levelsTree) {
        this.levelsTree = levelsTree;
        return this;
    }

    @Override
    public PoliteDesire<C, V> withNumberRange(Number min, Number max) {
        mRandom.addConstraint(NumbersConstraint.from(min, max));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withStringsMaxLength(int len) {
        mRandom.addConstraint(new StringLengthConstraint(len));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldNamesInStrings() {
        mRandom.addConstraint(new StringFieldNameConstraint());
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldEqualTo(String fieldNameRegex, Object value) {
        mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, value));
        return this;
    }

    @Override
    public <K> PoliteDesire<C, V> withClassEqualTo(Class<K> clazz, K value) {
        mRandom.addConstraint(new TypeValueConstraint<K>(clazz, value));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withFieldImageLink(String fieldNameRegex, int width, int height) {
        mRandom.addConstraint(new RandoImageConstraint(fieldNameRegex, width, height));
        return this;
    }

    @Override
    public PoliteDesire<C, V> withSeed(int seed) {
        this.seed = seed;
        return this;
    }

    @Override
    public PoliteDesire<C, V> withCollectionSizeRange(int min, int max) {
        collectionSizeRange = new RandomObject.Range(min, max);
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
}
