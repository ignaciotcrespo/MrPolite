package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_seed_Tests {

    @Test
    public void withSeed_equal() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class)
                .withSeed(1234)
                .please();

        Class_WithPrimitives object2 = one(Class_WithPrimitives.class)
                .withSeed(1234)
                .please();

        assertThat(object1).isEqualTo(object2);

    }


    @Test
    public void withSeed_notEqual() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class)
                .withSeed(1234)
                .please();

        Class_WithPrimitives object2 = one(Class_WithPrimitives.class)
                .withSeed(54656)
                .please();

        assertThat(object1).isNotEqualTo(object2);

    }

    @Test
    public void withSeed_default_notEqual() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class).please();

        Class_WithPrimitives object2 = one(Class_WithPrimitives.class).please();

        assertThat(object1).isNotEqualTo(object2);
    }

}