package com.github.ignaciotcrespo.mrpolite.utils;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class TextUtils {
    public static void hasNot(String text) {
        assertThat(text).isNull();
    }

    public static void has(String text) {
        assertThat(text).isNotEmpty();
    }
}
