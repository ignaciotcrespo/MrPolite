package com.github.ignaciotcrespo.randomobject.classesfortest;

import java.util.List;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 3/3/17.
 */
public class Class_WithCollectionWithInnerClass {

    List<InnerClass> list;

    public void assertValidData() {
        assertThat(list).isNotEmpty().hasOnlyElementsOfType(InnerClass.class).allMatch(new Predicate<InnerClass>() {
            @Override
            public boolean test(InnerClass innerClass) {
                return !innerClass.text.isEmpty();
            }
        });
    }

    class InnerClass {
        String text;
    }

}
