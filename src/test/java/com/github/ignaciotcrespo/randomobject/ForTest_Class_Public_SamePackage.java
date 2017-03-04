package com.github.ignaciotcrespo.randomobject;

import static com.github.ignaciotcrespo.randomobject.utils.TextUtils.has;
import static com.github.ignaciotcrespo.randomobject.utils.TextUtils.hasNot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class ForTest_Class_Public_SamePackage extends ForTest_SuperClass_Public_SamePackage {

    public String textPublic;
    String textDefault;
    protected String textProtected;
    private String textPrivate;

    public final String textPublicFinal = "public_final";
    final String textDefaultFinal = "default_final";
    protected final String textProtectedFinal = "protected_final";
    private final String textPrivateFinal = "private_final";

    public static String textPublicStatic;
    static String textDefaultStatic;
    protected static String textProtectedStatic;
    private static String textPrivateStatic;

    public static final String textPublicStaticFinal = "public_static_final";
    static final String textDefaultStaticFinal = "default_static_final";
    protected static final String textProtectedStaticFinal = "protected_static_final";
    private static final String textPrivateStaticFinal = "private_static_final";

    ForTest_Class_Default_SamePackage mClassDefaultSamePackage;
    InnerClass mInnerClass;
    InnerClassStatic mInnerClassStatic;

    public void assertValidData(int levelsTree) {
        has(textDefault);
        has(textProtected);
        has(textPublic);
        has(textPrivate);

        has(textDefaultStatic);
        has(textProtectedStatic);
        has(textPublicStatic);
        has(textPrivateStatic);

        // final fields should not be set
        assertThat(textPublicFinal).isSameAs("public_final");
        assertThat(textDefaultFinal).isSameAs("default_final");
        assertThat(textProtectedFinal).isSameAs("protected_final");
        assertThat(textPrivateFinal).isSameAs("private_final");

        assertThat(textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(textPrivateStaticFinal).isSameAs("private_static_final");

        assertThat(mClassDefaultSamePackage).isNotNull();
        assertThat(mInnerClass).isNotNull();
        assertThat(mInnerClassStatic).isNotNull();
        if (levelsTree > 1) {
            mClassDefaultSamePackage.assertValidData();
            mInnerClass.assertValidData();
            mInnerClassStatic.assertValidData();
        } else {
            mClassDefaultSamePackage.assertEmptyData();
            mInnerClass.assertEmptyData();
            mInnerClassStatic.assertEmptyData();
        }
    }

    public static void resetStaticFields() {
        textDefaultStatic = null;
        textPrivateStatic = null;
        textProtectedStatic = null;
        textPublicStatic = null;
        InnerClassStatic.resetStaticFields();
    }

    // ----- BEGIN: INNER CLASS ------ //

    public class InnerClass {
        public String inner_textPublic;
        String inner_textDefault;
        protected String inner_textProtected;
        private String inner_textPrivate;

        public final String inner_textPublicFinal = "public_final";
        final String inner_textDefaultFinal = "default_final";
        protected final String inner_textProtectedFinal = "protected_final";
        private final String inner_textPrivateFinal = "private_final";

        public static final String inner_textPublicStaticFinal = "public_static_final";
        static final String inner_textDefaultStaticFinal = "default_static_final";
        protected static final String inner_textProtectedStaticFinal = "protected_static_final";
        private static final String inner_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(inner_textDefault);
            has(inner_textProtected);
            has(inner_textPublic);
            has(inner_textPrivate);

            // final fields should not be set
            assertThat(inner_textPublicFinal).isSameAs("public_final");
            assertThat(inner_textDefaultFinal).isSameAs("default_final");
            assertThat(inner_textProtectedFinal).isSameAs("protected_final");
            assertThat(inner_textPrivateFinal).isSameAs("private_final");

            assertThat(inner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(inner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(inner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(inner_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(inner_textDefault);
            hasNot(inner_textProtected);
            hasNot(inner_textPublic);
            hasNot(inner_textPrivate);

            // final fields should not be set
            assertThat(inner_textPublicFinal).isSameAs("public_final");
            assertThat(inner_textDefaultFinal).isSameAs("default_final");
            assertThat(inner_textProtectedFinal).isSameAs("protected_final");
            assertThat(inner_textPrivateFinal).isSameAs("private_final");

            assertThat(inner_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(inner_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(inner_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(inner_textPrivateStaticFinal).isSameAs("private_static_final");
        }
    }

    // ----- END: INNER CLASS ------ //

    // ----- BEGIN: STATIC INNER CLASS ------ //
    public static class InnerClassStatic {
        public String innerStatic_textPublic;
        String innerStatic_textDefault;
        protected String innerStatic_textProtected;
        private String innerStatic_textPrivate;

        public final String innerStatic_textPublicFinal = "public_final";
        final String innerStatic_textDefaultFinal = "default_final";
        protected final String innerStatic_textProtectedFinal = "protected_final";
        private final String innerStatic_textPrivateFinal = "private_final";

        public static String innerStatic_textPublicStatic;
        static String innerStatic_textDefaultStatic;
        protected static String innerStatic_textProtectedStatic;
        private static String innerStatic_textPrivateStatic;

        public static final String innerStatic_textPublicStaticFinal = "public_static_final";
        static final String innerStatic_textDefaultStaticFinal = "default_static_final";
        protected static final String innerStatic_textProtectedStaticFinal = "protected_static_final";
        private static final String innerStatic_textPrivateStaticFinal = "private_static_final";

        public void assertValidData() {
            has(innerStatic_textDefault);
            has(innerStatic_textProtected);
            has(innerStatic_textPublic);
            has(innerStatic_textPrivate);

            has(innerStatic_textDefaultStatic);
            has(innerStatic_textProtectedStatic);
            has(innerStatic_textPublicStatic);
            has(innerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(innerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(innerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(innerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(innerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(innerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(innerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(innerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(innerStatic_textPrivateStaticFinal).isSameAs("private_static_final");

        }

        public void assertEmptyData() {
            hasNot(innerStatic_textDefault);
            hasNot(innerStatic_textProtected);
            hasNot(innerStatic_textPublic);
            hasNot(innerStatic_textPrivate);

            hasNot(innerStatic_textDefaultStatic);
            hasNot(innerStatic_textProtectedStatic);
            hasNot(innerStatic_textPublicStatic);
            hasNot(innerStatic_textPrivateStatic);

            // final fields should not be set
            assertThat(innerStatic_textPublicFinal).isSameAs("public_final");
            assertThat(innerStatic_textDefaultFinal).isSameAs("default_final");
            assertThat(innerStatic_textProtectedFinal).isSameAs("protected_final");
            assertThat(innerStatic_textPrivateFinal).isSameAs("private_final");

            assertThat(innerStatic_textPublicStaticFinal).isSameAs("public_static_final");
            assertThat(innerStatic_textDefaultStaticFinal).isSameAs("default_static_final");
            assertThat(innerStatic_textProtectedStaticFinal).isSameAs("protected_static_final");
            assertThat(innerStatic_textPrivateStaticFinal).isSameAs("private_static_final");
        }

        public static void resetStaticFields() {
            innerStatic_textDefaultStatic = null;
            innerStatic_textPrivateStatic = null;
            innerStatic_textProtectedStatic = null;
            innerStatic_textPublicStatic = null;
        }
    }
    // ----- END: STATIC INNER CLASS ------ //
}
