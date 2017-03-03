package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.otherpackage.*;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

import static com.github.ignaciotcrespo.randomobject.MrPolite.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObjectTest {

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        MockClassPublicOtherPackage.resetStaticFields();
        MockClassDefaultOtherPackage.resetStaticFields();
        MockParentClassPublicOtherPackage.resetStaticFields();
        MockClassDefaultSamePackage.resetStaticFields();
        MockClassPublicSamePackage.resetStaticFields();
        MockGrandParentClassPublicOtherPackage.resetStaticFields();
    }

    @Test
    public void fill_normalClass_public_anotherPackage() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_anotherPackage_defaultDepth() throws Exception {
        MockClassPublicOtherPackage object = one(MockClassPublicOtherPackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
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
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_normalClass_public_samePackage_defaultLevels() throws Exception {
        MockClassPublicSamePackage object = one(MockClassPublicSamePackage.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
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
        MockClassInnerAll object = one(MockClassInnerAll.class)
                .withDepth(1)
                .please();

        assertThat(object).isNotNull();
        object.assertValidData(1);
    }

    @Test
    public void fill_innerClasses_defaultDepth() throws Exception {
        MockClassInnerAll object = one(MockClassInnerAll.class).please();

        assertThat(object).isNotNull();
        object.assertValidData(3);
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

        List<MockClassPublicSamePackage> list = aListOf(2, MockClassPublicSamePackage.class)
                .withDepth(1)
                .please();

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(1);
        list.get(1).assertValidData(1);
    }

    @Test
    public void fill_list_defaultDepth() throws Exception {

        List<MockClassPublicSamePackage> list = aListOf(2, MockClassPublicSamePackage.class).please();

        assertThat(list).hasSize(2);
        list.get(0).assertValidData(3);
        list.get(1).assertValidData(3);
    }

    @Test
    public void fill_array() throws Exception {

        MockClassPublicSamePackage[] array = anArrayOf(2, MockClassPublicSamePackage.class)
                .withDepth(1)
                .please();

        assertThat(array).hasSize(2);
        array[0].assertValidData(1);
        array[1].assertValidData(1);
    }

    @Test
    public void fill_array_defaultDepth() throws Exception {

        MockClassPublicSamePackage[] list = anArrayOf(2, MockClassPublicSamePackage.class).please();

        assertThat(list).hasSize(2);
        list[0].assertValidData(3);
        list[1].assertValidData(3);
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

        assertThat(object.imageUri).isEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
        assertThat(object.text).isNotEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
        assertThat(object.name).isNotEqualTo("http://lorempixel.com/300/200/?rand=1489956094");
    }

    @Test
    public void withRandomImageLink_different() throws Exception {
        ClassWithUri object = one(ClassWithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();
        ClassWithUri object2 = one(ClassWithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();

        assertThat(object.imageUri).isNotEqualTo(object2.imageUri);
    }

    @Test
    public void withRandomImageLink_list_different() throws Exception {
        List<ClassWithUri> list = aListOf(3, ClassWithUri.class).withFieldImageLink(".*[uU]ri.*", 300, 200).please();

        assertThat(list.get(0).imageUri).isNotEqualTo(list.get(1).imageUri);
        assertThat(list.get(1).imageUri).isNotEqualTo(list.get(2).imageUri);
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
    public void fieldArray_defaultSize() throws Exception {
        ClassWithCollections object = one(ClassWithCollections.class).please();

        assertThat(object._arrayString.length).isBetween(2, 5);
        assertThat(object._arrayInt.length).isBetween(2, 5);
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

    @Test
    public void fieldNestedArray() throws Exception {
        ClassWithNestedCollections object = one(ClassWithNestedCollections.class).please();

        assertThat(object._arrayString).doesNotContainNull();
        assertThat(object._arrayInt).doesNotContainNull();
        assertThat(object._arrayByte).doesNotContainNull();
    }

    @Test
    public void genericEnumTest() {
        GenericClassWithEnumExtend dto = one(GenericClassWithEnumExtend.class).please();

        assertThat(dto).isNotNull();
        assertThat(dto.getStatus()).isEqualTo(FooEnum.DEFAULT);
    }

    @Test
    public void generics() throws Exception {
        ClassWithGenerics<String> object = one(ClassWithGenerics.class)
                .withGenerics(String.class)
                .please();

        assertThat(object.genericObject).isNotEmpty();

    }

   @Test
    public void genericsMulti() throws Exception {
        ClassWithMultiGenerics<Integer, String> object = one(ClassWithMultiGenerics.class)
                .withGenerics(Integer.class, String.class)
                .please();

        assertThat(object.genericObjectKey).isNotNull();
        assertThat(object.genericObjectValue).isNotEmpty();
    }

   @Test
    public void genericsMulti_withoutGenerics() throws Exception {
        ClassWithMultiGenerics<Integer, String> object = one(ClassWithMultiGenerics.class)
                .please();

        assertNull(object.genericObjectKey);
        assertNull(object.genericObjectValue);
    }

    @Test
    public void genericsMulti_withWrongNumberOfGenerics() throws Exception {
        ClassWithMultiGenerics<Integer, String> object = one(ClassWithMultiGenerics.class)
                .withGenerics(String.class)
                .please();

        assertNull(object.genericObjectKey);
        assertNull(object.genericObjectValue);
    }
    @Test
    public void genericsMulti_wrongTypesInGenerics() throws Exception {
        ClassWithMultiGenerics<Integer, String> object = one(ClassWithMultiGenerics.class)
                .withSeed(1234) // using seed to compare values later
                .withGenerics(String.class, Integer.class)
                .please();

        // THIS IS CRAZY! The int field is a string, the string field is an int! Can't figure out how to fix it
        assertEquals(object.genericObjectKey, "a58668a8-4281-97d2-f38c-2dda3cc8eb78");
        assertEquals(object.genericObjectValue, -611652875);
    }

    @Test
    public void genericsNested() throws Exception {
        ClassWithGenerics<AnotherClassWithGenerics<String>> object = one(ClassWithGenerics.class)
                .withGenerics(AnotherClassWithGenerics.class)
                .please();

        assertThat(object.genericObject).isInstanceOf(AnotherClassWithGenerics.class);
        assertThat(object.genericObject.anotherGenericObject).isNull();

    }

    @Test
    public void genericClassInside() throws Exception {
        ClassWithGenericClassInside object = one(ClassWithGenericClassInside.class).please();

        assertThat(object.clazzGeneric.genericObject).isInstanceOf(String.class);

    }

    static class ClassWithGenericClassInside {
        ClassWithGenerics<String> clazzGeneric;
    }


    static class ClassWithGenerics<T>{
        T genericObject;
    }

    static class AnotherClassWithGenerics<T>{
        T anotherGenericObject;
    }

    static class ClassWithMultiGenerics<K, V>{
        K genericObjectKey;
        V genericObjectValue;
    }

    public class GenericClassWithEnum<T extends Enum<T>> implements Serializable {
        private T status;

        public T getStatus() {
            return status;
        }

        public void setStatus(T status) {
            this.status = status;
        }
    }

    public class GenericClassWithEnumExtend extends GenericClassWithEnum<FooEnum> {
    }

    public enum FooEnum {
        DEFAULT
    }

    static class ClassWithUri {
        String text;
        String name;
        String imageUri;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ClassWithUri that = (ClassWithUri) o;

            if (text != null ? !text.equals(that.text) : that.text != null) return false;
            if (name != null ? !name.equals(that.name) : that.name != null) return false;
            return imageUri != null ? imageUri.equals(that.imageUri) : that.imageUri == null;
        }

        @Override
        public int hashCode() {
            int result = text != null ? text.hashCode() : 0;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + (imageUri != null ? imageUri.hashCode() : 0);
            return result;
        }
    }

    static class ClassWithCollections {
        String[] _arrayString;
        int[] _arrayInt;
    }

    static class ClassWithNestedCollections {
        String[][] _arrayString;
        int[][][] _arrayInt;
        byte[][][][] _arrayByte;
    }

    // TODO generics! ((ParameterizedType)clazz.getGenericSuperclass()).getActualTypeArguments()[0]
    // TODO colecciones anidadas
    // TODO change(object).field("regex").please()
    // TODO detect android annotations StringDef, etc.
    // TODO fields with collections
    // TODO random color
    // TODO random bitmap as byte array

}