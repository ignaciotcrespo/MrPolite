package com.github.ignaciotcrespo.mrpolite.classesfortest;

import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.has;
import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.hasNot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class SuperSuperClass_Public {

    public String grandgrandparent_textPublic;
    String grandgrandparent_textDefault;
    protected String grandgrandparent_textProtected;
    private String grandgrandparent_textPrivate;

    public final String grandparent_textPublicFinal = "public_final";
    final String grandparent_textDefaultFinal = "default_final";
    protected final String grandparent_textProtectedFinal = "protected_final";
    private final String grandparent_textPrivateFinal = "private_final";

    public static String grandparent_textPublicStatic;
    static String grandparent_textDefaultStatic;
    protected static String grandparent_textProtectedStatic;
    private static String grandparent_textPrivateStatic;

    public static final String grandparent_textPublicStaticFinal = "public_static_final";
    static final String grandparent_textDefaultStaticFinal = "default_static_final";
    protected static final String grandparent_textProtectedStaticFinal = "protected_static_final";
    private static final String grandparent_textPrivateStaticFinal = "private_static_final";

    GrandParentInnerClass mGrandParentInnerClass;
    GrandParentInnerClassStatic mGrandParentInnerClassStatic;

    public void assertValidData(int levelsTree) {
        has(grandgrandparent_textDefault);
        has(grandgrandparent_textProtected);
        has(grandgrandparent_textPublic);
        has(grandgrandparent_textPrivate);

        has(grandparent_textDefaultStatic);
        has(grandparent_textProtectedStatic);
        has(grandparent_textPublicStatic);
        has(grandparent_textPrivateStatic);

        // final fields should not be set
        assertThat(grandparent_textPublicFinal).isSameAs("public_final");
        assertThat(grandparent_textDefaultFinal).isSameAs("default_final");
        assertThat(grandparent_textProtectedFinal).isSameAs("protected_final");
        assertThat(grandparent_textPrivateFinal).isSameAs("private_final");

        assertThat(grandparent_textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(grandparent_textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(grandparent_textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(grandparent_textPrivateStaticFinal).isSameAs("private_static_final");

        assertThat(mGrandParentInnerClass).isNotNull();
        assertThat(mGrandParentInnerClassStatic).isNotNull();
        if (levelsTree > 3) {
            mGrandParentInnerClass.assertValidData();
            mGrandParentInnerClassStatic.assertValidData();
        } else {
            mGrandParentInnerClass.assertEmptyData();
            mGrandParentInnerClassStatic.assertEmptyData();
        }
    }

    public void assertEmptyData() {
        hasNot(grandgrandparent_textDefault);
        hasNot(grandgrandparent_textProtected);
        hasNot(grandgrandparent_textPublic);
        hasNot(grandgrandparent_textPrivate);

        hasNot(grandparent_textDefaultStatic);
        hasNot(grandparent_textProtectedStatic);
        hasNot(grandparent_textPublicStatic);
        hasNot(grandparent_textPrivateStatic);

        // final fields should not be set
        assertThat(grandparent_textPublicFinal).isSameAs("public_final");
        assertThat(grandparent_textDefaultFinal).isSameAs("default_final");
        assertThat(grandparent_textProtectedFinal).isSameAs("protected_final");
        assertThat(grandparent_textPrivateFinal).isSameAs("private_final");

        assertThat(grandparent_textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(grandparent_textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(grandparent_textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(grandparent_textPrivateStaticFinal).isSameAs("private_static_final");

        assertThat(mGrandParentInnerClass).isNull();
        assertThat(mGrandParentInnerClassStatic).isNull();
    }

    public static void resetStaticFields() {
        grandparent_textDefaultStatic = null;
        grandparent_textPrivateStatic = null;
        grandparent_textProtectedStatic = null;
        grandparent_textPublicStatic = null;
        GrandParentInnerClassStatic.resetStaticFields();
    }

    // ----- BEGIN: INNER CLASS ------ //

    public class GrandParentInnerClass {
        public String grandParentInner_textPublic;
        String grandParentInner_textDefault;
        protected String grandParentInner_textProtected;
        private String grandParentInner_textPrivate;

        public final String grandParentInner_textPublicFinal = "public_final";
        final String grandParentInner_textDefaultFinal = "default_final";
        protected final String grandParentInner_textProtectedFinal = "protected_final";
        private final String grandParentInner_textPrivateFinal = "private_final";

        public static final String grandParentInner_textPublicStaticFinal = "public_static_final";
        static final String grandParentInner_textDefaultStaticFinal = "default_static_final";
        protected static final String grandParentInner_textProtectedStaticFinal = "protected_static_final";
        private static final String grandParentInner_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(grandParentInner_textDefault);
            has(grandParentInner_textProtected);
            has(grandParentInner_textPublic);
            has(grandParentInner_textPrivate);

            // final fields should not be set
            assertThat(grandParentInner_textPublicFinal).isSameAs("public_final");
            assertThat(grandParentInner_textDefaultFinal).isSameAs("default_final");
            assertThat(grandParentInner_textProtectedFinal).isSameAs("protected_final");
            assertThat(grandParentInner_textPrivateFinal).isSameAs("private_final");

            assertThat(grandParentInner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(grandParentInner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(grandParentInner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(grandParentInner_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(grandParentInner_textDefault);
            hasNot(grandParentInner_textProtected);
            hasNot(grandParentInner_textPublic);
            hasNot(grandParentInner_textPrivate);

            // final fields should not be set
            assertThat(grandParentInner_textPublicFinal).isSameAs("public_final");
            assertThat(grandParentInner_textDefaultFinal).isSameAs("default_final");
            assertThat(grandParentInner_textProtectedFinal).isSameAs("protected_final");
            assertThat(grandParentInner_textPrivateFinal).isSameAs("private_final");

            assertThat(grandParentInner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(grandParentInner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(grandParentInner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(grandParentInner_textPrivateStaticFinal).isSameAs("private_static_final");
        }
    }

    // ----- END: INNER CLASS ------ //

    // ----- BEGIN: STATIC INNER CLASS ------ //
    public static class GrandParentInnerClassStatic {
        public String grandParentInnerStatic_textPublic;
        String grandParentInnerStatic_textDefault;
        protected String grandParentInnerStatic_textProtected;
        private String grandParentInnerStatic_textPrivate;

        public final String grandParentInnerStatic_textPublicFinal = "public_final";
        final String grandParentInnerStatic_textDefaultFinal = "default_final";
        protected final String grandParentInnerStatic_textProtectedFinal = "protected_final";
        private final String grandParentInnerStatic_textPrivateFinal = "private_final";

        public static String grandParentInnerStatic_textPublicStatic;
        static String grandParentInnerStatic_textDefaultStatic;
        protected static String grandParentInnerStatic_textProtectedStatic;
        private static String grandParentInnerStatic_textPrivateStatic;

        public static final String grandParentInnerStatic_textPublicStaticFinal = "public_static_final";
        static final String grandParentInnerStatic_textDefaultStaticFinal = "default_static_final";
        protected static final String grandParentInnerStatic_textProtectedStaticFinal = "protected_static_final";
        private static final String grandParentInnerStatic_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(grandParentInnerStatic_textDefault);
            has(grandParentInnerStatic_textProtected);
            has(grandParentInnerStatic_textPublic);
            has(grandParentInnerStatic_textPrivate);

            has(grandParentInnerStatic_textDefaultStatic);
            has(grandParentInnerStatic_textProtectedStatic);
            has(grandParentInnerStatic_textPublicStatic);
            has(grandParentInnerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(grandParentInnerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(grandParentInnerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(grandParentInnerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(grandParentInnerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(grandParentInnerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(grandParentInnerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(grandParentInnerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(grandParentInnerStatic_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(grandParentInnerStatic_textDefault);
            hasNot(grandParentInnerStatic_textProtected);
            hasNot(grandParentInnerStatic_textPublic);
            hasNot(grandParentInnerStatic_textPrivate);

            hasNot(grandParentInnerStatic_textDefaultStatic);
            hasNot(grandParentInnerStatic_textProtectedStatic);
            hasNot(grandParentInnerStatic_textPublicStatic);
            hasNot(grandParentInnerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(grandParentInnerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(grandParentInnerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(grandParentInnerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(grandParentInnerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(grandParentInnerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(grandParentInnerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(grandParentInnerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(grandParentInnerStatic_textPrivateStaticFinal).isSameAs("private_static_final");
        }

        public static void resetStaticFields() {
            grandParentInnerStatic_textDefaultStatic = null;
            grandParentInnerStatic_textPrivateStatic = null;
            grandParentInnerStatic_textProtectedStatic = null;
            grandParentInnerStatic_textPublicStatic = null;
        }
    }
    // ----- END: STATIC INNER CLASS ------ //
}
