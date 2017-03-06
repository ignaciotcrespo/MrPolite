package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_changeObject_Test {

    @Test
    public void change_default_changesNothing() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .please();
        Class_WithPrimitives object2 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .please();
        assertThat(object1).isEqualTo(object2);

        MrPolite.change(object1).please();

        assertThat(object1).isEqualTo(object2);
    }

    @Test
    public void change_setField() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .please();
        Class_WithPrimitives object2 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .withFieldEqualTo("_long", 777)
                .please();

        MrPolite.change(object1).withFieldEqualTo("_long", 777).please();

        assertThat(object1).isEqualTo(object2);

    }

    @Test
    public void change_setClass() throws Exception {
        Class_WithPrimitives object1 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .please();
        Class_WithPrimitives object2 = one(Class_WithPrimitives.class)
                .withSeed(12345)
                .withClassEqualTo(String.class, "hi!")
                .please();

        MrPolite.change(object1)
                .withClassEqualTo(String.class, "hi!")
                .please();

        assertThat(object1).isEqualTo(object2);

    }
}