package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithPrimitives;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.randomobject.MrPolite.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_strings_Tests {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void withStringMaxLength() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        object.assertStringLen(3);
    }

    @Test
    public void withStringMaxLength_many() throws Exception {
        List<Class_WithPrimitives> list = aListOf(10, Class_WithPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        for (Class_WithPrimitives object : list) {
            object.assertStringLen(3);
        }
    }

    @Test
    public void withStringMaxLength_array() throws Exception {
        Class_WithPrimitives[] list = anArrayOf(10, Class_WithPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        for (Class_WithPrimitives object : list) {
            object.assertStringLen(3);
        }
    }

    @Test
    public void withStringAsFieldName_one() throws Exception {
        Class_WithPrimitives object = one(Class_WithPrimitives.class).withFieldNamesInStrings().please();

        object.assertString("_string", "_string2");
    }

    @Test
    public void withStringAsFieldName_many() throws Exception {
        List<Class_WithPrimitives> many = aListOf(5, Class_WithPrimitives.class).withFieldNamesInStrings().please();

        for (Class_WithPrimitives object : many) {
            object.assertString("_string", "_string2");
        }
    }

    @Test
    public void withStringAsFieldName_array() throws Exception {
        Class_WithPrimitives[] many = anArrayOf(5, Class_WithPrimitives.class).withFieldNamesInStrings().please();

        for (Class_WithPrimitives object : many) {
            object.assertString("_string", "_string2");
        }
    }

}