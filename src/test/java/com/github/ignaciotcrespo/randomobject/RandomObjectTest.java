package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.otherpackage.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.randomobject.RandomObject.many;
import static com.github.ignaciotcrespo.randomobject.RandomObject.one;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObjectTest {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void fill_normalClass_public_anotherPackage() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree2() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .deep(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree3() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .deep(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree4() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .deep(4)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_public_samePackage() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree2() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .deep(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree3() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .deep(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree4() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .deep(4)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_default_samePackage() throws Exception {
        MockClassDefaultSamePackage object = one(MockClassDefaultSamePackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorWithParam() throws Exception {
        MockClassConstructorWithParam object = one(MockClassConstructorWithParam.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorWithManyParams() throws Exception {
        MockClassConstructorWithManyParams object = one(MockClassConstructorWithManyParams.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivate() throws Exception {
        MockClassPrivateConstructor object = one(MockClassPrivateConstructor.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivateWithParams() throws Exception {
        MockClassPrivateConstructorWithParams object = one(MockClassPrivateConstructorWithParams.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorAbstract() throws Exception {
        MockClassAbstract object = one(MockClassAbstract.class).please();

        assertThat(object).isNull();
    }

    @Test
    public void fill_innerClasses() throws Exception {
        MockClassInnerAll object = one(MockClassInnerAll.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_innerClasses_levelsTree2() throws Exception {
        MockClassInnerAll object = one(MockClassInnerAll.class)
                .deep(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_anonymousClass() throws Exception {
        MockClassAnonymousAbstractClass object = one(MockClassAnonymousAbstractClass.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_list() throws Exception {

        List<MockClassPublicSamePackage> list = many(MockClassPublicSamePackage.class).listOf(2);

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(1);
        list.get(1).assertValidData(1);
    }

    @Test
    public void fill_listEmpty() throws Exception {

        List<MockClassPublicSamePackage> list = many(MockClassPublicSamePackage.class).listOf(0);

        assertThat(list).isEmpty();
    }

    // TODO fields with collections, arrays, etc
    // TODO set class to always null
    // TODO set class to always a constant
    // TODO set min/max in numbers

}