package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;

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
                .withFieldEqualTo(".*", 1)
                .please();

        System.out.println(object);
    }

    @Test
    public void withField3() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withNumberRange(1, 1)
                .please();

        object.assertNumbers(1);
    }

}