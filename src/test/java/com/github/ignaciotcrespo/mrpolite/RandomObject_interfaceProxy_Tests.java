package com.github.ignaciotcrespo.mrpolite;

import org.junit.Test;

import java.util.Calendar;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by crespo on 2/20/17.
 */
public class RandomObject_interfaceProxy_Tests {

    @Test
    public void interface_proxy_generateValues() throws Exception {
        Interface_ForTest object = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();

        assertThat(object.getInt()).isEqualTo(1708819343);
        assertThat(object.getString()).isEqualTo("475836d3-ea65-ef55-55f8-fd4f18c906be");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 584);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DATE, 14);
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 31);
        calendar.set(Calendar.SECOND, 06);
        calendar.set(Calendar.MILLISECOND, 0);
        assertThat(object.getCalendar()).isEqualTo(calendar);
    }

    @Test
    public void interface_proxy_sameValuesForSameObject() throws Exception {
        Interface_ForTest object = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();

        assertThat(object.getInt()).isEqualTo(object.getInt());
        assertThat(object.getCalendar()).isEqualTo(object.getCalendar());
        assertThat(object.getString()).isEqualTo(object.getString());
        assertThat(object.getString2()).isEqualTo(object.getString2());
    }

    @Test
    public void interface_proxy_differentValuesForSameType() throws Exception {
        Interface_ForTest object = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();

        assertThat(object.getString()).isNotEqualTo(object.getString2());
    }

    @Test
    public void interface_proxy_sameSeed_sameValues() throws Exception {
        Interface_ForTest object = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();
        Interface_ForTest object2 = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();

        assertThat(object.getInt()).isEqualTo(object2.getInt());
        assertThat(object.getCalendar()).isEqualTo(object2.getCalendar());
        assertThat(object.getString()).isEqualTo(object2.getString());
        assertThat(object.getString2()).isEqualTo(object2.getString2());
    }

    @Test
    public void interface_proxy_otherSeed_otherValues() throws Exception {
        Interface_ForTest object = one(Interface_ForTest.class)
                .withSeed(4352)
                .please();
        Interface_ForTest object2 = one(Interface_ForTest.class)
                .withSeed(319868)
                .please();

        assertThat(object.getInt()).isNotEqualTo(object2.getInt());
        assertThat(object.getCalendar()).isNotEqualTo(object2.getCalendar());
        assertThat(object.getString()).isNotEqualTo(object2.getString());
        assertThat(object.getString2()).isNotEqualTo(object2.getString2());
    }

    interface Interface_ForTest {
        int getInt();

        String getString();

        String getString2();

        Calendar getCalendar();
    }
}