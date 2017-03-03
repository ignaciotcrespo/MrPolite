package com.github.ignaciotcrespo.randomobject.otherpackage;

import static com.github.ignaciotcrespo.randomobject.utils.TextUtils.has;
import static com.github.ignaciotcrespo.randomobject.utils.TextUtils.hasNot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassDefaultOtherPackage extends MockParentClassDefaultOtherPackage {

    public String textOtherPackagePublic;
    String textOtherPackageDefault;
    protected String textOtherPackageProtected;
    private String textOtherPackagePrivate;

    public final String textOtherPackagePublicFinal = "public_final";
    final String textOtherPackageDefaultFinal = "default_final";
    protected final String textOtherPackageProtectedFinal = "protected_final";
    private final String textOtherPackagePrivateFinal = "private_final";

    public static String textOtherPackagePublicStatic;
    static String textOtherPackageDefaultStatic;
    protected static String textOtherPackageProtectedStatic;
    private static String textOtherPackagePrivateStatic;

    public static final String textOtherPackagePublicStaticFinal = "public_static_final";
    static final String textOtherPackageDefaultStaticFinal = "default_static_final";
    protected static final String textOtherPackageProtectedStaticFinal = "protected_static_final";
    private static final String textOtherPackagePrivateStaticFinal = "private_static_final";

    public void assertValidData() {
        has(textOtherPackageDefault);
        has(textOtherPackageProtected);
        has(textOtherPackagePublic);
        has(textOtherPackagePrivate);

        has(textOtherPackageDefaultStatic);
        has(textOtherPackageProtectedStatic);
        has(textOtherPackagePublicStatic);
        has(textOtherPackagePrivateStatic);

        // final fields should not be set
        assertThat(textOtherPackagePublicFinal).isSameAs("public_final");
        assertThat(textOtherPackageDefaultFinal).isSameAs("default_final");
        assertThat(textOtherPackageProtectedFinal).isSameAs("protected_final");
        assertThat(textOtherPackagePrivateFinal).isSameAs("private_final");

        assertThat(textOtherPackagePublicStaticFinal).isSameAs("public_static_final");
        assertThat(textOtherPackageDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(textOtherPackageProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(textOtherPackagePrivateStaticFinal).isSameAs("private_static_final");

    }

    public void assertEmptyData() {
        hasNot(textOtherPackageDefault);
        hasNot(textOtherPackageProtected);
        hasNot(textOtherPackagePublic);
        hasNot(textOtherPackagePrivate);

        hasNot(textOtherPackageDefaultStatic);
        hasNot(textOtherPackageProtectedStatic);
        hasNot(textOtherPackagePublicStatic);
        hasNot(textOtherPackagePrivateStatic);

        // final fields should not be set
        assertThat(textOtherPackagePublicFinal).isSameAs("public_final");
        assertThat(textOtherPackageDefaultFinal).isSameAs("default_final");
        assertThat(textOtherPackageProtectedFinal).isSameAs("protected_final");
        assertThat(textOtherPackagePrivateFinal).isSameAs("private_final");

        assertThat(textOtherPackagePublicStaticFinal).isSameAs("public_static_final");
        assertThat(textOtherPackageDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(textOtherPackageProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(textOtherPackagePrivateStaticFinal).isSameAs("private_static_final");
    }

    public static void resetStaticFields() {
        textOtherPackageDefaultStatic = null;
        textOtherPackagePrivateStatic = null;
        textOtherPackageProtectedStatic = null;
        textOtherPackagePublicStatic = null;
    }
}
