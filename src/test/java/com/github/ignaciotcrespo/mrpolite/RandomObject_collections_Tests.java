package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.*;
import org.junit.Test;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
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
    public void fieldList() throws Exception {
        Class_WithCollections object = one(Class_WithCollections.class).please();

        object.assertValidData(2, 5);
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
    public void fieldList_sizeRange() throws Exception {
        Class_WithCollections object = one(Class_WithCollections.class)
                .withCollectionSizeRange(4, 20)
                .please();

        object.assertValidData(4, 20);
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

    @Test
    public void fieldNestedCollections() throws Exception {
        Class_WithCollections_Nested object = one(Class_WithCollections_Nested.class).please();

        object.assertValidData(2, 5);
    }

    @Test
    public void collectionWithInnerClass() throws Exception {
        Class_WithCollectionWithInnerClass object = one(Class_WithCollectionWithInnerClass.class).please();

        object.assertValidData();

    }
}