package com.github.ignaciotcrespo.randomobject;

/**
 * Created by crespo on 2/27/17.
 */
public interface PoliteDesire<C, V> {

    V please();

    PoliteDesire<C, V> withDepth(int levelsTree);

    PoliteDesire<C, V> withNumberRange(Number min, Number max);

    PoliteDesire<C, V> withStringsMaxLength(int len);

    PoliteDesire<C, V> withFieldNamesInStrings();

    PoliteDesire<C, V> withFieldEqualTo(String fieldNameRegex, Object value);

    <K> PoliteDesire<C, V> withClassEqualTo(Class<K> clazz, K value);

    PoliteDesire<C, V> withFieldImageLink(String fieldNameRegex, int width, int height);

    PoliteDesire<C, V> withSeed(int seed);

    PoliteDesire<C, V> withCollectionSizeRange(int min, int max);

    PoliteDesire<C, V> exclude(String fieldNameRegex);

    PoliteDesire<C, V> exclude(Class<?> clazz);

}
