package com.github.ignaciotcrespo.mrpolite.classesfortest;

import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.has;
import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.hasNot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class SuperClass_Public extends SuperSuperClass_Public {

    public String parent_textPublic;
    String parent_textDefault;
    protected String parent_textProtected;
    private String parent_textPrivate;

    public final String parent_textPublicFinal = "public_final";
    final String parent_textDefaultFinal = "default_final";
    protected final String parent_textProtectedFinal = "protected_final";
    private final String parent_textPrivateFinal = "private_final";

    public static String parent_textPublicStatic;
    static String parent_textDefaultStatic;
    protected static String parent_textProtectedStatic;
    private static String parent_textPrivateStatic;

    public static final String parent_textPublicStaticFinal = "public_static_final";
    static final String parent_textDefaultStaticFinal = "default_static_final";
    protected static final String parent_textProtectedStaticFinal = "protected_static_final";
    private static final String parent_textPrivateStaticFinal = "private_static_final";

    ParentInnerClass parent_mParentInnerClass;
    ParentInnerClassStatic parent_mParentInnerClassStatic;

    public void assertValidData(int levelsTree) {
        has(parent_textDefault);
        has(parent_textProtected);
        has(parent_textPublic);
        has(parent_textPrivate);

        has(parent_textDefaultStatic);
        has(parent_textProtectedStatic);
        has(parent_textPublicStatic);
        has(parent_textPrivateStatic);

        // final fields should not be set
        assertThat(parent_textPublicFinal).isSameAs("public_final");
        assertThat(parent_textDefaultFinal).isSameAs("default_final");
        assertThat(parent_textProtectedFinal).isSameAs("protected_final");
        assertThat(parent_textPrivateFinal).isSameAs("private_final");

        assertThat(parent_textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(parent_textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(parent_textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(parent_textPrivateStaticFinal).isSameAs("private_static_final");

        assertThat(parent_mParentInnerClass).isNotNull();
        assertThat(parent_mParentInnerClassStatic).isNotNull();
        if (levelsTree > 2) {
            parent_mParentInnerClass.assertValidData();
            parent_mParentInnerClassStatic.assertValidData();
            super.assertValidData(levelsTree);
        } else {
            parent_mParentInnerClass.assertEmptyData();
            parent_mParentInnerClassStatic.assertEmptyData();
            super.assertEmptyData();
        }

    }

    public void assertEmptyData() {
        hasNot(parent_textDefault);
        hasNot(parent_textProtected);
        hasNot(parent_textPublic);
        hasNot(parent_textPrivate);

        hasNot(parent_textDefaultStatic);
        hasNot(parent_textProtectedStatic);
        hasNot(parent_textPublicStatic);
        hasNot(parent_textPrivateStatic);

        // final fields should not be set
        assertThat(parent_textPublicFinal).isSameAs("public_final");
        assertThat(parent_textDefaultFinal).isSameAs("default_final");
        assertThat(parent_textProtectedFinal).isSameAs("protected_final");
        assertThat(parent_textPrivateFinal).isSameAs("private_final");

        assertThat(parent_textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(parent_textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(parent_textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(parent_textPrivateStaticFinal).isSameAs("private_static_final");

        assertThat(parent_mParentInnerClass).isNull();
        assertThat(parent_mParentInnerClassStatic).isNull();
    }

    public static void resetStaticFields() {
        parent_textDefaultStatic = null;
        parent_textPrivateStatic = null;
        parent_textProtectedStatic = null;
        parent_textPublicStatic = null;
        ParentInnerClassStatic.resetStaticFields();
    }

    // ----- BEGIN: INNER CLASS ------ //

    public class ParentInnerClass {
        public String parentInner_textPublic;
        String parentInner_textDefault;
        protected String parentInner_textProtected;
        private String parentInner_textPrivate;

        public final String parentInner_textPublicFinal = "public_final";
        final String parentInner_textDefaultFinal = "default_final";
        protected final String parentInner_textProtectedFinal = "protected_final";
        private final String parentInner_textPrivateFinal = "private_final";

        public static final String parentInner_textPublicStaticFinal = "public_static_final";
        static final String parentInner_textDefaultStaticFinal = "default_static_final";
        protected static final String parentInner_textProtectedStaticFinal = "protected_static_final";
        private static final String parentInner_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(parentInner_textDefault);
            has(parentInner_textProtected);
            has(parentInner_textPublic);
            has(parentInner_textPrivate);

            // final fields should not be set
            assertThat(parentInner_textPublicFinal).isSameAs("public_final");
            assertThat(parentInner_textDefaultFinal).isSameAs("default_final");
            assertThat(parentInner_textProtectedFinal).isSameAs("protected_final");
            assertThat(parentInner_textPrivateFinal).isSameAs("private_final");

            assertThat(parentInner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(parentInner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(parentInner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(parentInner_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(parentInner_textDefault);
            hasNot(parentInner_textProtected);
            hasNot(parentInner_textPublic);
            hasNot(parentInner_textPrivate);

            // final fields should not be set
            assertThat(parentInner_textPublicFinal).isSameAs("public_final");
            assertThat(parentInner_textDefaultFinal).isSameAs("default_final");
            assertThat(parentInner_textProtectedFinal).isSameAs("protected_final");
            assertThat(parentInner_textPrivateFinal).isSameAs("private_final");

            assertThat(parentInner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(parentInner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(parentInner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(parentInner_textPrivateStaticFinal).isSameAs("private_static_final");
        }
    }

    // ----- END: INNER CLASS ------ //

    // ----- BEGIN: STATIC INNER CLASS ------ //
    public static class ParentInnerClassStatic {
        public String parentInnerStatic_textPublic;
        String parentInnerStatic_textDefault;
        protected String parentInnerStatic_textProtected;
        private String parentInnerStatic_textPrivate;

        public final String parentInnerStatic_textPublicFinal = "public_final";
        final String parentInnerStatic_textDefaultFinal = "default_final";
        protected final String parentInnerStatic_textProtectedFinal = "protected_final";
        private final String parentInnerStatic_textPrivateFinal = "private_final";

        public static String parentInnerStatic_textPublicStatic;
        static String parentInnerStatic_textDefaultStatic;
        protected static String parentInnerStatic_textProtectedStatic;
        private static String parentInnerStatic_textPrivateStatic;

        public static final String parentInnerStatic_textPublicStaticFinal = "public_static_final";
        static final String parentInnerStatic_textDefaultStaticFinal = "default_static_final";
        protected static final String parentInnerStatic_textProtectedStaticFinal = "protected_static_final";
        private static final String parentInnerStatic_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(parentInnerStatic_textDefault);
            has(parentInnerStatic_textProtected);
            has(parentInnerStatic_textPublic);
            has(parentInnerStatic_textPrivate);

            has(parentInnerStatic_textDefaultStatic);
            has(parentInnerStatic_textProtectedStatic);
            has(parentInnerStatic_textPublicStatic);
            has(parentInnerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(parentInnerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(parentInnerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(parentInnerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(parentInnerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(parentInnerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(parentInnerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(parentInnerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(parentInnerStatic_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(parentInnerStatic_textDefault);
            hasNot(parentInnerStatic_textProtected);
            hasNot(parentInnerStatic_textPublic);
            hasNot(parentInnerStatic_textPrivate);

            hasNot(parentInnerStatic_textDefaultStatic);
            hasNot(parentInnerStatic_textProtectedStatic);
            hasNot(parentInnerStatic_textPublicStatic);
            hasNot(parentInnerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(parentInnerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(parentInnerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(parentInnerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(parentInnerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(parentInnerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(parentInnerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(parentInnerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(parentInnerStatic_textPrivateStaticFinal).isSameAs("private_static_final");
        }

        public static void resetStaticFields() {
            parentInnerStatic_textDefaultStatic = null;
            parentInnerStatic_textPrivateStatic = null;
            parentInnerStatic_textProtectedStatic = null;
            parentInnerStatic_textPublicStatic = null;
        }
    }
    // ----- END: STATIC INNER CLASS ------ //
}
