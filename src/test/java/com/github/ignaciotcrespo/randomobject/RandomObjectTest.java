package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.otherpackage.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.randomobject.RandomObject.random;
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
        MockClassPublicOtherPackage object = random().fill(MockClassPublicOtherPackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree2() throws Exception {
        MockClassPublicOtherPackage object = random()
                .levelsTree(2)
                .fill(MockClassPublicOtherPackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree3() throws Exception {
        MockClassPublicOtherPackage object = random()
                .levelsTree(3)
                .fill(MockClassPublicOtherPackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree4() throws Exception {
        MockClassPublicOtherPackage object = random()
                .levelsTree(4)
                .fill(MockClassPublicOtherPackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_public_samePackage() throws Exception {
        MockClassPublicSamePackage object = random().fill(MockClassPublicSamePackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree2() throws Exception {
        MockClassPublicSamePackage object = random()
                .levelsTree(2)
                .fill(MockClassPublicSamePackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree3() throws Exception {
        MockClassPublicSamePackage object = random()
                .levelsTree(3)
                .fill(MockClassPublicSamePackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree4() throws Exception {
        MockClassPublicSamePackage object = random()
                .levelsTree(4)
                .fill(MockClassPublicSamePackage.class);

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_default_samePackage() throws Exception {
        MockClassDefaultSamePackage object = random().fill(MockClassDefaultSamePackage.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorWithParam() throws Exception {
        MockClassConstructorWithParam object = random().fill(MockClassConstructorWithParam.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorWithManyParams() throws Exception {
        MockClassConstructorWithManyParams object = random().fill(MockClassConstructorWithManyParams.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivate() throws Exception {
        MockClassPrivateConstructor object = random().fill(MockClassPrivateConstructor.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorPrivateWithParams() throws Exception {
        MockClassPrivateConstructorWithParams object = random().fill(MockClassPrivateConstructorWithParams.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_constructorAbstract() throws Exception {
        MockClassAbstract object = random().fill(MockClassAbstract.class);

        assertThat(object).isNull();
    }

    @Test
    public void fill_innerClasses() throws Exception {
        MockClassInnerAll object = random().fill(MockClassInnerAll.class);

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_innerClasses_levelsTree2() throws Exception {
        MockClassInnerAll object = random()
                .levelsTree(2)
                .fill(MockClassInnerAll.class);

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_anonymousClass() throws Exception {
        MockClassAnonymousAbstractClass object = random().fill(MockClassAnonymousAbstractClass.class);

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_list() throws Exception {

        List<MockClassPublicSamePackage> list = random().fill(2, MockClassPublicSamePackage.class);

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(1);
        list.get(1).assertValidData(1);
    }

    @Test
    public void fill_listEmpty() throws Exception {

        List<MockClassPublicSamePackage> list = random().fill(0, MockClassPublicSamePackage.class);

        assertThat(list).isEmpty();
    }

    // TODO fields with collections, arrays, etc
    // TODO set class to always null
    // TODO set class to always a constant
    // TODO set min/max in numbers

}