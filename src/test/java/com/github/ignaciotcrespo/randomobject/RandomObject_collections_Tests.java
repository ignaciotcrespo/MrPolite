package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithArrays;
import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithArrays_Multidimension;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_collections_Tests {

    @Test
    public void fieldArray() throws Exception {
        Class_WithArrays object = one(Class_WithArrays.class).please();

        assertThat(object._arrayString).doesNotContainNull();
        assertThat(object._arrayInt).doesNotContain(0);
    }

    @Test
    public void fieldArray_defaultSize() throws Exception {
        Class_WithArrays object = one(Class_WithArrays.class).please();

        assertThat(object._arrayString.length).isBetween(2, 5);
        assertThat(object._arrayInt.length).isBetween(2, 5);
    }

    @Test
    public void fieldArray_collectionSize() throws Exception {
        Class_WithArrays object = one(Class_WithArrays.class)
                .withCollectionSizeRange(10, 12)
                .please();

        assertThat(object._arrayString.length).isBetween(10, 12);
        assertThat(object._arrayInt.length).isBetween(10, 12);
    }

    @Test
    public void fieldCollection_collectionSize() throws Exception {
        Class_WithArrays object = one(Class_WithArrays.class)
                .withCollectionSizeRange(10, 12)
                .please();

        assertThat(object._arrayString.length).isBetween(10, 12);
        assertThat(object._arrayInt.length).isBetween(10, 12);
    }

    @Test
    public void fieldNestedArray() throws Exception {
        Class_WithArrays_Multidimension object = one(Class_WithArrays_Multidimension.class).please();

        assertThat(object._arrayString).doesNotContainNull();
        assertThat(object._arrayInt).doesNotContainNull();
        assertThat(object._arrayByte).doesNotContainNull();
    }

}