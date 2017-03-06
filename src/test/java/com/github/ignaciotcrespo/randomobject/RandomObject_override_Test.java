package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_override_Test {

    @Test
    public void change_override_false() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withSeed(234)
                .overrideValues(false)
                .please();

        object.assertNumbersRandom();
        object.assertString("bbb9586e-d62b-a2f9-d516-fe91c7238b40", "cf477b54-66f9-8db5-360e-521107544ed5");
        object.assertNotOverride();
    }

    @Test
    public void change_override_true() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withSeed(234)
                .overrideValues(true)
                .please();

        object.assertNumbersRandom();
        object.assertString("bbb9586e-d62b-a2f9-d516-fe91c7238b40", "cf477b54-66f9-8db5-360e-521107544ed5");
        object.assertOverride();
    }
}