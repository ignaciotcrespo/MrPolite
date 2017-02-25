package com.github.ignaciotcrespo.randomobject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by crespo on 2/25/17.
 */
public class Want {
    public static <T> One<T> one(Class<T> clazz) {
        return new One<>(clazz);
    }

    public static <T> Many<T> many(Class<T> clazz) {
        return new Many<>(clazz);
    }

    public static class One<T> extends BaseRandom<T> {

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

    public static class Many<T> extends BaseRandom<T> {
        private int size;

        private Many(Class<T> clazz) {
            super(clazz);
        }

        public List<T> listOf(int size) {
            this.size = size;
            return please();
        }

        @Override
        public Many<T> withDepth(int levelsTree) {
            return (Many<T>) super.withDepth(levelsTree);
        }

        private List<T> please() {
            return randomObject().fill(size, clazz);
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

    private static class BaseRandom<T> {
        final Class<T> clazz;
        final RandomObject mRandom;
        int levelsTree = 1;
        int seed;
        RandomObject.Range collectionSizeRange = RandomObject.DEFAULT_COLLECTION_RANGE;
        private final List<String> excludeRegex = new ArrayList<>();
        private final List<Class<?>> excludeClasses = new ArrayList<>();

        private BaseRandom(Class<T> clazz) {
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

        public BaseRandom<T> withDepth(int levelsTree) {
            this.levelsTree = levelsTree;
            return this;
        }

        public BaseRandom<T> withNumberRange(Number min, Number max) {
            mRandom.addConstraint(NumbersConstraint.from(min, max));
            return this;
        }

        public BaseRandom<T> withStringsMaxLength(int len) {
            mRandom.addConstraint(new StringLengthConstraint(len));
            return this;
        }

        public BaseRandom<T> withFieldNamesInStrings() {
            mRandom.addConstraint(new StringFieldNameConstraint());
            return this;
        }

        public BaseRandom<T> withFieldEqualTo(String fieldNameRegex, Object value) {
            mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, value));
            return this;
        }

        public <K> BaseRandom<T> withClassEqualTo(Class<K> clazz, K value) {
            mRandom.addConstraint(new TypeValueConstraint<>(clazz, value));
            return this;
        }

        public BaseRandom<T> withFieldImageLink(String fieldNameRegex, int width, int height) {
            mRandom.addConstraint(new FieldNameRegexConstraint(fieldNameRegex, "http://lorempixel.com/" + width + "/" + height));
            return this;
        }

        public BaseRandom<T> withSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public BaseRandom<T> withCollectionSizeRange(int min, int max) {
            collectionSizeRange = new RandomObject.Range(min, max);
            return this;
        }

        public BaseRandom<T> exclude(String fieldNameRegex) {
            excludeRegex.add(fieldNameRegex);
            return this;
        }

        public BaseRandom<T> exclude(Class<?> clazz) {
            excludeClasses.add(clazz);
            return this;
        }
    }
}
