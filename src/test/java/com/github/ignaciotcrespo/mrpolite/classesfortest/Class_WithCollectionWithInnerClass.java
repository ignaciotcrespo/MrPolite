package com.github.ignaciotcrespo.mrpolite.classesfortest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithCollectionWithInnerClass {

    List<InnerClass> list;

    public void assertValidData() {
        assertThat(list).isNotEmpty().hasOnlyElementsOfType(InnerClass.class);
        for (InnerClass innerClass: list) {
            assertThat(innerClass.text).isNotEmpty();
        }
    }

    class InnerClass {
        String text;
    }

}
