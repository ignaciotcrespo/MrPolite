package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.otherpackage.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.ignaciotcrespo.randomobject.MrPolite.*;
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
                .withDepth(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree3() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .withDepth(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_levelsTree4() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .withDepth(4)
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
                .withDepth(2)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(2);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree3() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .withDepth(3)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
    }

    @Test
    public void fill_normalClass_public_samePackage_levelsTree4() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .withDepth(4)
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
                .withDepth(2)
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

        List<MockClassPublicSamePackage> list = aListOf(2, MockClassPublicSamePackage.class).please();

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(1);
        list.get(1).assertValidData(1);
    }

    @Test
    public void fill_array() throws Exception {

        MockClassPublicSamePackage[] list = anArrayOf(2, MockClassPublicSamePackage.class).please();

        assertThat(list).hasSize(2);
        list[0].assertValidData(1);
        list[1].assertValidData(1);
    }

    @Test
    public void fill_listEmpty() throws Exception {

        List<MockClassPublicSamePackage> list = aListOf(0, MockClassPublicSamePackage.class).please();

        assertThat(list).isEmpty();
    }

    @Test
    public void fill_arrayEmpty() throws Exception {

        MockClassPublicSamePackage[] list = anArrayOf(0, MockClassPublicSamePackage.class).please();

        assertThat(list).isEmpty();
    }

    @Test
    public void withNumbers() throws Exception {
        One<MockClassPrimitives> one = (One<MockClassPrimitives>) one(MockClassPrimitives.class).withNumberRange(-23, 42);

        NumbersConstraint constraint = (NumbersConstraint) one.mRandom.constraints.get(0);
        assertThat(constraint.getMin()).isEqualTo(-23);
        assertThat(constraint.getMax()).isEqualTo(42);
    }

    @Test
    public void withStringMaxLength() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        object.assertStringLen(3);
    }

    @Test
    public void withStringMaxLength_many() throws Exception {
        List<MockClassPrimitives> list = aListOf(10, MockClassPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        for (MockClassPrimitives object : list) {
            object.assertStringLen(3);
        }
    }

    @Test
    public void withStringMaxLength_array() throws Exception {
        MockClassPrimitives[] list = anArrayOf(10, MockClassPrimitives.class)
                .withStringsMaxLength(3)
                .please();

        for (MockClassPrimitives object : list) {
            object.assertStringLen(3);
        }
    }

    @Test
    public void withStringAsFieldName_one() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class).withFieldNamesInStrings().please();

        object.assertString("_string", "_string2");
    }

    @Test
    public void withStringAsFieldName_many() throws Exception {
        List<MockClassPrimitives> many = aListOf(5, MockClassPrimitives.class).withFieldNamesInStrings().please();

        for (MockClassPrimitives object : many) {
            object.assertString("_string", "_string2");
        }
    }

    @Test
    public void withStringAsFieldName_array() throws Exception {
        MockClassPrimitives[] many = anArrayOf(5, MockClassPrimitives.class).withFieldNamesInStrings().please();

        for (MockClassPrimitives object : many) {
            object.assertString("_string", "_string2");
        }
    }

    @Test
    public void withField() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .withFieldEqualTo("_byte", 3)
                .please();

        object.assertByte((byte) 3);
    }

    @Test
    public void withField2() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .withFieldEqualTo(".*", 1)
                .please();

        System.out.println(object);
    }

    @Test
    public void withField3() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .withNumberRange(1, 1)
                .please();

        object.assertNumbers(1);
    }

    @Test
    public void withType() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .withClassEqualTo(String.class, "a")
                .please();

        object.assertString("a");
    }

    @Test
    public void withRandomImageLink() throws Exception {
        One<ClassWithUri> one = (One<ClassWithUri>) one(ClassWithUri.class)
                .withFieldImageLink(".*[uU]ri.*", 300, 200);
        one.seed = 1234;
        ClassWithUri object = one.please();

        assertThat(object.imageUri).isEqualTo("http://lorempixel.com/300/200/?rand=-1517918040");
        assertThat(object.text).isNotEqualTo("http://lorempixel.com/300/200/?rand=-1517918040");
        assertThat(object.name).isNotEqualTo("http://lorempixel.com/300/200/?rand=-1517918040");
    }

    @Test
    public void withSeed_equal() throws Exception {
        MockClassPrimitives object1 = one(MockClassPrimitives.class)
                .withSeed(1234)
                .please();

        MockClassPrimitives object2 = one(MockClassPrimitives.class)
                .withSeed(1234)
                .please();

        assertThat(object1).isEqualTo(object2);

    }


    @Test
    public void withSeed_notEqual() throws Exception {
        MockClassPrimitives object1 = one(MockClassPrimitives.class)
                .withSeed(1234)
                .please();

        MockClassPrimitives object2 = one(MockClassPrimitives.class)
                .withSeed(54656)
                .please();

        assertThat(object1).isNotEqualTo(object2);

    }

    @Test
    public void withSeed_default_notEqual() throws Exception {
        MockClassPrimitives object1 = one(MockClassPrimitives.class).please();

        MockClassPrimitives object2 = one(MockClassPrimitives.class).please();

        assertThat(object1).isNotEqualTo(object2);
    }

    @Test
    public void fieldArray() throws Exception {
        ClassWithCollections object = one(ClassWithCollections.class).please();

        assertThat(object._arrayString).doesNotContainNull();
        assertThat(object._arrayInt).doesNotContain(0);
    }

    @Test
    public void fieldArray_collectionSize() throws Exception {
        ClassWithCollections object = one(ClassWithCollections.class)
                .withCollectionSizeRange(10, 12)
                .please();

        assertThat(object._arrayString.length).isBetween(10, 12);
        assertThat(object._arrayInt.length).isBetween(10, 12);
    }

    @Test
    public void fieldCollection_collectionSize() throws Exception {
        ClassWithCollections object = one(ClassWithCollections.class)
                .withCollectionSizeRange(10, 12)
                .please();

        assertThat(object._arrayString.length).isBetween(10, 12);
        assertThat(object._arrayInt.length).isBetween(10, 12);
    }

    @Test
    public void exclude() throws Exception {
        ClassWithCollections object = one(ClassWithCollections.class)
                .exclude(".*Int")
                .please();

        assertThat(object._arrayString).isNotNull();
        assertThat(object._arrayInt).isNull();
    }

    @Test
    public void exclude_class() throws Exception {
        MockClassPrimitives object = one(MockClassPrimitives.class)
                .exclude(String.class)
                .please();

        object.assertStringNull();
        object.assertNumbersRandom();
    }

    static class ClassWithUri {
        String text;
        String name;
        String imageUri;
    }

    static class ClassWithCollections {
        String[] _arrayString;
        int[] _arrayInt;
    }

    // TODO change(object).field("regex").please()
    // TODO detect android annotations StringDef, etc.
    // TODO MrPolite.listOf(5, Person.class).please();
    // TODO MrPolite.arrayOf(5, Person.class).please();
    // TODO fields with collections
    // TODO random color
    // TODO random bitmap as byte array

}