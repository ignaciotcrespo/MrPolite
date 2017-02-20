package com.github.ignaciotcrespo.randomobject.otherpackage;

import org.assertj.core.api.Assertions;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassInnerAll {

    InnerClassDefault mInnerClassDefault;
    InnerClassPrivate mInnerClassPrivate;
    InnerClassProtected mInnerClassProtected;
    InnerClassPublic mInnerClassPublic;

    public void assertValidData() {
        mInnerClassDefault.assertValidData();
        mInnerClassProtected.assertValidData();
        mInnerClassPublic.assertValidData();
        mInnerClassPrivate.assertValidData();
    }

    class InnerClassDefault {

        private String text;

        public void assertValidData() {
            Assertions.assertThat(text).isNotNull().isNotEmpty();
        }

    }

    private class InnerClassPrivate {

        private String text;

        public void assertValidData() {
            Assertions.assertThat(text).isNotNull().isNotEmpty();
        }

    }

    protected class InnerClassProtected {

        private String text;

        public void assertValidData() {
            Assertions.assertThat(text).isNotNull().isNotEmpty();
        }

    }

    public class InnerClassPublic {

        private String text;

        public void assertValidData() {
            Assertions.assertThat(text).isNotNull().isNotEmpty();
        }

    }

}
