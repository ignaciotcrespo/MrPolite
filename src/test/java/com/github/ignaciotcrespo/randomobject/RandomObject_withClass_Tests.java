package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_withClass_Tests {

    @Test
    public void withType() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withClassEqualTo(String.class, "a")
                .please();

        object.assertString("a");
    }

}