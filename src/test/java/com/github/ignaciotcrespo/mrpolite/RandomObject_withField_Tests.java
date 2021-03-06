package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_withField_Tests {

    @Test
    public void withField() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withFieldEqualTo("_byte", 3)
                .please();

        object.assertByte((byte) 3);
    }

    @Test
    public void withField2() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withFieldEqualTo(".*", 56)
                .please();

        object.assertNumbers(56);
    }

    @Test
    public void withField3() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withNumberRange(1, 1)
                .please();

        object.assertNumbers(1);
    }

}