package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_Default;
import com.github.ignaciotcrespo.mrpolite.classesfortest.Class_Public;
import com.github.ignaciotcrespo.mrpolite.classesfortest.SuperClass_Public;
import com.github.ignaciotcrespo.mrpolite.classesfortest.SuperSuperClass_Public;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_HierarchyTests {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        Class_Public.resetStaticFields();
        Class_Default.resetStaticFields();
        SuperClass_Public.resetStaticFields();
        ForTest_Class_Default_SamePackage.resetStaticFields();
        ForTest_Class_Public_SamePackage.resetStaticFields();
        SuperSuperClass_Public.resetStaticFields();
    }

    @Test
    public void fill_normalClass_public_anotherPackage() throws Exception {
        Class_Public object = one(Class_Public.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_defaultDepth() throws Exception {
        Class_Public object = one(Class_Public.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree2() throws Exception {
        Class_Public object = one(Class_Public.class)
                .withDepth(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree3() throws Exception {
        Class_Public object = one(Class_Public.class)
                .withDepth(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree4() throws Exception {
        Class_Public object = one(Class_Public.class)
                .withDepth(4)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_public_samePackage() throws Exception {
        ForTest_Class_Public_SamePackage object = one(ForTest_Class_Public_SamePackage.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_samePackage_defaultLevels() throws Exception {
        ForTest_Class_Public_SamePackage object = one(ForTest_Class_Public_SamePackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree2() throws Exception {
        ForTest_Class_Public_SamePackage object = one(ForTest_Class_Public_SamePackage.class)
                .withDepth(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree3() throws Exception {
        ForTest_Class_Public_SamePackage object = one(ForTest_Class_Public_SamePackage.class)
                .withDepth(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree4() throws Exception {
        ForTest_Class_Public_SamePackage object = one(ForTest_Class_Public_SamePackage.class)
                .withDepth(4)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(4);
    }

    @Test
    public void fill_normalClass_default_samePackage() throws Exception {
        ForTest_Class_Default_SamePackage object = one(ForTest_Class_Default_SamePackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData();
    }

    @Test
    public void fill_list() throws Exception {

        List<ForTest_Class_Public_SamePackage> list = aListOf(2, ForTest_Class_Public_SamePackage.class)
                .withDepth(1)
                .please();

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(1);
        list.get(1).assertValidData(1);
    }

    @Test
    public void fill_list_defaultDepth() throws Exception {

        List<ForTest_Class_Public_SamePackage> list = aListOf(2, ForTest_Class_Public_SamePackage.class).please();

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(3);
        list.get(1).assertValidData(3);
    }

    @Test
    public void fill_array() throws Exception {

        ForTest_Class_Public_SamePackage[] array = anArrayOf(2, ForTest_Class_Public_SamePackage.class)
                .withDepth(1)
                .please();

        assertThat(array).hasSize(2);
        array[0].assertValidData(1);
        array[1].assertValidData(1);
    }

    @Test
    public void fill_array_defaultDepth() throws Exception {

        ForTest_Class_Public_SamePackage[] list = anArrayOf(2, ForTest_Class_Public_SamePackage.class).please();

        assertThat(list).hasSize(2);
        list[0].assertValidData(3);
        list[1].assertValidData(3);
    }

    @Test
    public void fill_listEmpty() throws Exception {

        List<ForTest_Class_Public_SamePackage> list = aListOf(0, ForTest_Class_Public_SamePackage.class).please();

        assertThat(list).isEmpty();
    }

    @Test
    public void fill_arrayEmpty() throws Exception {

        ForTest_Class_Public_SamePackage[] list = anArrayOf(0, ForTest_Class_Public_SamePackage.class).please();

        assertThat(list).isEmpty();
    }

}