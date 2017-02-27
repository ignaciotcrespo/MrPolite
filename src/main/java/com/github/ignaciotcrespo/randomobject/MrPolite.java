package com.github.ignaciotcrespo.randomobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/25/17.
 */
public class MrPolite {
    public static <T> One<T> one(Class<T> clazz) {
        return new One<T>(clazz);
    }

    public static <T> Many<T> many(Class<T> clazz) {
        return new Many<T>(clazz);
    }

    public static class One<T> extends PoliteRequest<T> {

        private One(Class<T> clazz) {
            super(clazz);
        }

        public T please() {
            return randomObject().fill(clazz);
        }

        @Override
        public One<T> withDepth(int levelsTree) {
            return (One<T>) super.withDepth(levelsTree);
        }

        @Override
        public One<T> withNumberRange(Number min, Number max) {
            return (One<T>) super.withNumberRange(min, max);
        }

        @Override
        public One<T> withStringsMaxLength(int len) {
            return (One<T>) super.withStringsMaxLength(len);
        }

        @Override
        public One<T> withFieldNamesInStrings() {
            return (One<T>) super.withFieldNamesInStrings();
        }

        @Override
        public One<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            return (One<T>) super.withFieldEqualTo(fieldNameRegex, value);
        }

        @Override
        public <K> One<T> withClassEqualTo(Class<K> clazz, K value) {
            return (One<T>) super.withClassEqualTo(clazz, value);
        }

        @Override
        public One<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            return (One<T>) super.withFieldImageLink(fieldNameRegex, width, height);
        }

        @Override
        public One<T> withSeed(int seed) {
            return (One<T>) super.withSeed(seed);
        }

        @Override
        public One<T> withCollectionSizeRange(int min, int max) {
            return (One<T>) super.withCollectionSizeRange(min, max);
        }

        @Override
        public One<T> exclude(String fieldNameRegex) {
            return (One<T>) super.exclude(fieldNameRegex);
        }

        @Override
        public One<T> exclude(Class<?> clazz) {
            return (One<T>) super.exclude(clazz);
        }
    }

    public static class Many<T> extends PoliteRequest<T> {
        private int size;

        private Many(Class<T> clazz) {
            super(clazz);
        }

        public Many<T> listOf(int size) {
            this.size = size;
            return this;
        }

        public List<T> please() {
            return randomObject().fill(size, clazz);
        }

        @Override
        public Many<T> withDepth(int levelsTree) {
            return (Many<T>) super.withDepth(levelsTree);
        }

        @Override
        public Many<T> withNumberRange(Number min, Number max) {
            return (Many<T>) super.withNumberRange(min, max);
        }

        @Override
        public Many<T> withStringsMaxLength(int len) {
            return (Many<T>) super.withStringsMaxLength(len);
        }

        @Override
        public Many<T> withFieldNamesInStrings() {
            return (Many<T>) super.withFieldNamesInStrings();
        }

        @Override
        public Many<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            return (Many<T>) super.withFieldEqualTo(fieldNameRegex, value);
        }

        @Override
        public <K> Many<T> withClassEqualTo(Class<K> clazz, K value) {
            return (Many<T>) super.withClassEqualTo(clazz, value);
        }

        @Override
        public Many<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            return (Many<T>) super.withFieldImageLink(fieldNameRegex, width, height);
        }

        @Override
        public Many<T> withSeed(int seed) {
            return (Many<T>) super.withSeed(seed);
        }

        @Override
        public Many<T> withCollectionSizeRange(int min, int max) {
            return (Many<T>) super.withCollectionSizeRange(min, max);
        }

        @Override
        public Many<T> exclude(String fieldNameRegex) {
            return (Many<T>) super.exclude(fieldNameRegex);
        }

        @Override
        public Many<T> exclude(Class<?> clazz) {
            return (Many<T>) super.exclude(clazz);
        }
    }

    private static class PoliteRequest<T> {
        final Class<T> clazz;
        @VisibleForTesting
        RandomObject mRandom;
        int levelsTree = 1;
        @VisibleForTesting
        int seed;
        RandomObject.Range collectionSizeRange = RandomObject.DEFAULT_COLLECTION_RANGE;
        private final List<String> excludeRegex = new ArrayList<String>();
        private final List<Class<?>> excludeClasses = new ArrayList<Class<?>>();

        private PoliteRequest(Class<T> clazz) {
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

        public PoliteRequest<T> withDepth(int levelsTree) {
            this.levelsTree = levelsTree;
            return this;
        }

        public PoliteRequest<T> withNumberRange(Number min, Number max) {
            mRandom.addConstraint(NumbersConstraint.from(min, max));
            return this;
        }

        public PoliteRequest<T> withStringsMaxLength(int len) {
            mRandom.addConstraint(new StringLengthConstraint(len));
            return this;
        }

        public PoliteRequest<T> withFieldNamesInStrings() {
            mRandom.addConstraint(new StringFieldNameConstraint());
            return this;
        }

        public PoliteRequest<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, value));
            return this;
        }

        public <K> PoliteRequest<T> withClassEqualTo(Class<K> clazz, K value) {
            mRandom.addConstraint(new TypeValueConstraint<K>(clazz, value));
            return this;
        }

        public PoliteRequest<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            mRandom.addConstraint(new RandoImageConstraint(fieldNameRegex, width, height));
            return this;
        }

        public PoliteRequest<T> withSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public PoliteRequest<T> withCollectionSizeRange(int min, int max) {
            collectionSizeRange = new RandomObject.Range(min, max);
            return this;
        }

        public PoliteRequest<T> exclude(String fieldNameRegex) {
            excludeRegex.add(fieldNameRegex);
            return this;
        }

        public PoliteRequest<T> exclude(Class<?> clazz) {
            excludeClasses.add(clazz);
            return this;
        }
    }
}
