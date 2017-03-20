package com.github.ignaciotcrespo.mrpolite.issues;

import com.github.ignaciotcrespo.mrpolite.MrPolite;
import org.assertj.core.api.Assertions;

import static com.github.ignaciotcrespo.mrpolite.MrPolite.one;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for issue https://github.com/ignaciotcrespo/MrPolite/issues/2.
 */
public class Issue2Test {

    static final String OLOLO = "ololo";

    @org.junit.Test
    public void withClassEqualTo() throws Exception {
        Test test = one(Test.class)
                .withClassEqualTo(ITest.class, new ITestImpl())
                .please();

        assertThat(test.str.toString()).isEqualTo(OLOLO);
    }

    private interface ITest {
    }

    private static class ITestImpl implements ITest {
        @Override
        public String toString() {
            return OLOLO;
        }
    }

    private static class Test {
        ITest str;
    }

}