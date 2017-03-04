package com.github.ignaciotcrespo.randomobject;

import com.github.ignaciotcrespo.randomobject.classesfortest.Class_WithFieldWithGenerics;
import com.github.ignaciotcrespo.randomobject.classesfortest.enums.EnumOneValue;
import com.github.ignaciotcrespo.randomobject.classesfortest.generics.*;
import org.junit.Test;

import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;

import static com.github.ignaciotcrespo.randomobject.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_generics_Tests {

    @Test
    public void genericEnumTest() {
        Class_WithSuperclassGenericEnum dto = one(Class_WithSuperclassGenericEnum.class).please();

        assertThat(dto).isNotNull();
        assertThat(dto.status).isEqualTo(EnumOneValue.DEFAULT);
    }

    @Test
    public void generic_superclassGeneric() {
        Class_WithGenerics_T_And_SuperclassGenerics_T<String> object = one(Class_WithGenerics_T_And_SuperclassGenerics_T.class)
                .withGenerics(String.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object_T).isNotEmpty();
    }

    @Test
    public void generic_superclassGeneric_KV() {
        Class_WithGenerics_T_And_SuperclassGenerics_KV<String> object = one(Class_WithGenerics_T_And_SuperclassGenerics_KV.class)
                .withGenerics(String.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object_K).isInstanceOf(Integer.class);
        assertThat(object.object_V).isInstanceOf(String.class).isNotEmpty();
    }

    @Test
    public void generic_superclassGeneric_TT() {
        Class_WithGenerics_T_And_SuperclassGenerics_TT<String> object = one(Class_WithGenerics_T_And_SuperclassGenerics_TT.class)
                .withGenerics(String.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object_K).isInstanceOf(String.class).isNotEmpty();
        assertThat(object.object_V).isInstanceOf(String.class).isNotEmpty();
    }

    @Test
    public void generic_superclassGeneric_KV_VK() {
        Class_WithGenerics_KV_And_SuperclassGenerics_VK<Integer, String> object = one(Class_WithGenerics_KV_And_SuperclassGenerics_VK.class)
                .withGenerics(Integer.class, String.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object_K).isInstanceOf(String.class).isNotEmpty();
        assertThat(object.object_V).isInstanceOf(Integer.class).isNotNull();
    }

    @Test
    public void generic_superclassGeneric_KV_VK_KV() {
        Class_WithGenerics_KV_And_SuperclassGenerics_VK_And_SuperSuperClassKV<Date, Short> object = one(Class_WithGenerics_KV_And_SuperclassGenerics_VK_And_SuperSuperClassKV.class)
                .withGenerics(Date.class, Short.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object_K).isInstanceOf(Date.class).isNotNull();
        assertThat(object.object_V).isInstanceOf(Short.class).isNotNull();
    }

    @Test
    public void generic_generic_KV_VK() {
        Class_WithGenerics_KV_VK object = one(Class_WithGenerics_KV_VK.class)
                .please();

        assertThat(object).isNotNull();
        assertThat(object.object.object_K).isInstanceOf(Date.class).isNotNull();
        assertThat(object.object.object_V).isInstanceOf(Long.class).isNotNull();
    }

    @Test
    public void generics() throws Exception {
        Class_WithGenerics_T<String> object = one(Class_WithGenerics_T.class)
                .withGenerics(String.class)
                .please();

        assertThat(object.object_T).isNotEmpty();

    }

    @Test
    public void genericsMulti() throws Exception {
        Class_WithGenerics_KV<Integer, String> object = one(Class_WithGenerics_KV.class)
                .withGenerics(Integer.class, String.class)
                .please();

        assertThat(object.object_K).isNotNull();
        assertThat(object.object_V).isNotEmpty();
    }

    @Test
    public void genericsMulti_withoutGenerics() throws Exception {
        Class_WithGenerics_KV<Integer, String> object = one(Class_WithGenerics_KV.class)
                .please();

        assertNull(object.object_K);
        assertNull(object.object_V);
    }

    @Test
    public void genericsMulti_withWrongNumberOfGenerics() throws Exception {
        Class_WithGenerics_KV<Integer, String> object = one(Class_WithGenerics_KV.class)
                .withGenerics(String.class)
                .please();

        assertNull(object.object_K);
        assertNull(object.object_V);
    }

    @Test
    public void genericsMulti_wrongTypesInGenerics() throws Exception {
        Class_WithGenerics_KV<Integer, String> object = one(Class_WithGenerics_KV.class)
                .withSeed(1234) // using seed to compare values later
                .withGenerics(String.class, Integer.class)
                .please();

        // THIS IS CRAZY! The int field is a string, the string field is an int! Can't figure out how to fix it
        assertEquals(object.object_K, "a58668a8-4281-97d2-f38c-2dda3cc8eb78");
        assertEquals(object.object_V, -611652875);
    }

    @Test
    public void genericsNested() throws Exception {
        Class_WithGenerics_T<Class_WithGenerics_Other<String>> object = one(Class_WithGenerics_T.class)
                .withGenerics(Class_WithGenerics_Other.class)
                .please();

        assertThat(object.object_T).isInstanceOf(Class_WithGenerics_Other.class);
        assertThat(object.object_T.object_T_other).isNull();

    }

    @Test
    public void genericClassInside() throws Exception {
        Class_WithFieldWithGenerics object = one(Class_WithFieldWithGenerics.class).please();

        assertThat(object.fieldWithGenericParam.object_T).isInstanceOf(String.class);
    }

    @Test
    public void genericWildcard() throws Exception {
        Class_genericWildcard object = one(Class_genericWildcard.class).please();

        assertThat(object).isNotNull();
        assertThat(object.list).isNotEmpty().hasOnlyElementsOfType(Class_WithGenerics_T.class).allMatch(new Predicate<Class_WithGenerics_T<? extends Set>>() {
            @Override
            public boolean test(Class_WithGenerics_T<? extends Set> cgT_set) {
                assertThat((Set) cgT_set.object_T).isNotEmpty();
                return true;
            }
        });
        assertThat(object.listNested).isNotEmpty().hasOnlyElementsOfType(Class_WithGenerics_T.class);
        for (Class_WithGenerics_T<Class_WithGenerics_T<? extends Set>> cgT_cgT_set : object.listNested) {
            Class_WithGenerics_T<? extends Set> cgT_set = cgT_cgT_set.object_T;
            assertThat(cgT_set).isInstanceOf(Class_WithGenerics_T.class);
            assertThat((Set)cgT_set.object_T).isNotEmpty();
        }

    }
}