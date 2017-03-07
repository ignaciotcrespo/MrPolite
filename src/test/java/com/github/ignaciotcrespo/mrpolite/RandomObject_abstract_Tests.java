package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_WithAbstractField;
import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_WithAnonymousField;
import org.junit.Before;
import org.junit.Test;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_abstract_Tests {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void fill_anonymousClass() throws Exception {
        Class_WithAnonymousField object = one(Class_WithAnonymousField.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_abstractClass() throws Exception {
        Class_WithAbstractField object = one(Class_WithAbstractField.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }
}