package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Constants;

import java.util.Calendar;

import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.has;
import static com.github.ignaciotcrespo.mrpolite.utils.TextUtils.hasNot;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class ForTest_Class_Default_SamePackage extends ForTest_SuperClass_Default_SamePackage {

    public String textPublic;
    String textDefault;
    protected String textProtected;
    private String textPrivate;

    public final Calendar calPublicFinal = Constants.CALENDAR;
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
        assertThat(calPublicFinal).isSameAs(Constants.CALENDAR);
        assertThat(textDefaultFinal).isSameAs("default_final");
        assertThat(textProtectedFinal).isSameAs("protected_final");
        assertThat(textPrivateFinal).isSameAs("private_final");

        assertThat(textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(textPrivateStaticFinal).isSameAs("private_static_final");

    }

    public void assertEmptyData() {
        hasNot(textDefault);
        hasNot(textProtected);
        hasNot(textPublic);
        hasNot(textPrivate);

        hasNot(textDefaultStatic);
        hasNot(textProtectedStatic);
        hasNot(textPublicStatic);
        hasNot(textPrivateStatic);

        // final fields should not be set
        assertThat(calPublicFinal).isSameAs(Constants.CALENDAR);
        assertThat(textDefaultFinal).isSameAs("default_final");
        assertThat(textProtectedFinal).isSameAs("protected_final");
        assertThat(textPrivateFinal).isSameAs("private_final");

        assertThat(textPublicStaticFinal).isSameAs("public_static_final");
        assertThat(textDefaultStaticFinal).isSameAs("default_static_final");
        assertThat(textProtectedStaticFinal).isSameAs("protected_static_final");
        assertThat(textPrivateStaticFinal).isSameAs("private_static_final");
    }

    public static void resetStaticFields() {
        textDefaultStatic = null;
        textPrivateStatic = null;
        textProtectedStatic = null;
        textPublicStatic = null;
    }
}
