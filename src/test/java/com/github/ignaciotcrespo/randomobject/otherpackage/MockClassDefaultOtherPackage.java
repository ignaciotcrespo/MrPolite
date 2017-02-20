package com.github.ignaciotcrespo.randomobject.otherpackage;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class MockClassDefaultOtherPackage extends MockParentClassDefaultOtherPackage {

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

    public void assertValidData() {
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

    }

    private void has(String text) {
        assertThat(text)
                .isNotNull()
                .isNotEmpty();
    }

}
