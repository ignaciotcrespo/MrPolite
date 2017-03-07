package com.github.ignaciotcrespo.mrpolite;

import com.github.ignaciotcrespo.mrpolite.classesfortest.Constants;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_overrideFinals_Tests {

    @Test
    public void overrideFinal_false() throws Exception {
        Class_withFinalFields object = one(Class_withFinalFields.class)
                .please();

        object.assertValid();
    }

    @Test
    public void overrideFinal_true() throws Exception {
        Class_withFinalFields object = one(Class_withFinalFields.class)
                .overrideFinals()
                .please();

        object.assertValidFinals();
    }

    static class Class_withFinalFields {
        String text;
        final String textFinal = "final_text";
        int _int;
        final int _intFinal = 99;
        Calendar calendar;
        final Calendar calendarFinal = Constants.CALENDAR;
        List<String> list;
        final List<String> listFinal = null;

        public void assertValid() {
            assertThat(text).isNotEmpty();
            assertThat(getField(this, "textFinal").toString()).isEqualTo("final_text");
            assertThat(_int).isNotEqualTo(0);
            assertThat((Integer) getField(this, "_intFinal")).isEqualTo(99);
            assertThat(calendar).isNotNull();
            assertThat(calendarFinal).isSameAs(Constants.CALENDAR);
            assertThat(list).isNotEmpty().hasOnlyElementsOfType(String.class);
            assertThat(listFinal).isNull();
        }

        public void assertValidFinals() {
            assertThat(text).isNotEmpty();
            assertThat(getField(this, "textFinal").toString()).isNotEmpty().isNotEqualTo("final_text");
            assertThat(_int).isNotEqualTo(0);
            assertThat((Integer) getField(this, "_intFinal")).isNotEqualTo(99);
            assertThat(calendar).isNotNull();
            assertThat(calendarFinal).isNotEqualTo(Constants.CALENDAR);
            assertThat(list).isNotEmpty().hasOnlyElementsOfType(String.class);
            assertThat(listFinal).isNotEmpty().hasOnlyElementsOfType(String.class);
        }


        static Object getField(Object parent, String fieldName) {
            Field field = null;
            try {
                field = parent.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                return field.get(parent);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return field;
        }
    }

}