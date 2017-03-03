package com.github.ignaciotcrespo.randomobject.classesfortest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class Class_WithAllFieldsInnerClasses {

    InnerClassDefault mInnerClassDefault;
    InnerClassPrivate mInnerClassPrivate;
    InnerClassProtected mInnerClassProtected;
    InnerClassPublic mInnerClassPublic;

    public void assertValidData(int levelsTree) {
        assertThat(mInnerClassDefault).isNotNull();
        assertThat(mInnerClassProtected).isNotNull();
        assertThat(mInnerClassPublic).isNotNull();
        assertThat(mInnerClassPrivate).isNotNull();
        if (levelsTree > 1) {
            mInnerClassDefault.assertValidData();
            mInnerClassProtected.assertValidData();
            mInnerClassPublic.assertValidData();
            mInnerClassPrivate.assertValidData();
        } else {
            mInnerClassDefault.assertEmptyData();
            mInnerClassProtected.assertEmptyData();
            mInnerClassPublic.assertEmptyData();
            mInnerClassPrivate.assertEmptyData();
        }
    }

    class InnerClassDefault {

        private String text;

        public void assertValidData() {
            assertThat(text).isNotNull().isNotEmpty();
        }

        public void assertEmptyData() {
            assertThat(text).isNull();
        }
    }

    private class InnerClassPrivate {

        private String text;

        public void assertValidData() {
            assertThat(text).isNotNull().isNotEmpty();
        }

        public void assertEmptyData() {
            assertThat(text).isNull();
        }

    }

    protected class InnerClassProtected {

        private String text;

        public void assertValidData() {
            assertThat(text).isNotNull().isNotEmpty();
        }

        public void assertEmptyData() {
            assertThat(text).isNull();
        }

    }

    public class InnerClassPublic {

        private String text;

        public void assertValidData() {
            assertThat(text).isNotNull().isNotEmpty();
        }

        public void assertEmptyData() {
            assertThat(text).isNull();
        }

    }

}
