package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithAllFieldsInnerClasses;
import org.junit.Before;
import org.junit.Test;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_innerClasses_Tests {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }


    @Test
    public void fill_innerClasses() throws Exception {
        Class_WithAllFieldsInnerClasses object = one(Class_WithAllFieldsInnerClasses.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_innerClasses_defaultDepth() throws Exception {
        Class_WithAllFieldsInnerClasses object = one(Class_WithAllFieldsInnerClasses.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_innerClasses_levelsTree2() throws Exception {
        Class_WithAllFieldsInnerClasses object = one(Class_WithAllFieldsInnerClasses.class)
                .withDepth(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

}