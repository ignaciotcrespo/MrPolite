package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.*;
import org.junit.Before;
import org.junit.Test;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_constructor_tests {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void fill_constructorWithParam() throws Exception {
        Class_ConstructorWithParam object = one(Class_ConstructorWithParam.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorWithManyParams() throws Exception {
        Class_ConstructorWithManyParams object = one(Class_ConstructorWithManyParams.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivate() throws Exception {
        Class_WithPrivateConstructor object = one(Class_WithPrivateConstructor.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivateWithParams() throws Exception {
        Class_WithPrivateConstructorWithParams object = one(Class_WithPrivateConstructorWithParams.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorAbstract() throws Exception {
        Class_Abstract object = one(Class_Abstract.class).please();

        assertThat(object).isNull();
    }

}