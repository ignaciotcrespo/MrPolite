package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_WithArrays;
import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_exclude_Tests {

    @Test
    public void exclude() throws Exception {
        Class_WithArrays object = one(Class_WithArrays.class)
                .exclude(".*Int")
                .please();

        assertThat(object._arrayString).isNotNull();
        assertThat(object._arrayInt).isNull();
    }

    @Test
    public void exclude_class() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .exclude(String.class)
                .please();

        object.assertStringNull();
        object.assertNumbersRandom();
    }

}