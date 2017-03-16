package com.github.ignaciotcrespo.mrpolite.issues;

import com.github.ignaciotcrespo.mrpolite.MrPolite;
import org.assertj.core.api.Assertions;

/**
 * Test for issue https://github.com/ignaciotcrespo/MrPolite/issues/2.
 */
public class Issue2Test {

    static final String OLOLO = "ololo";

    @org.junit.Test
    public void name() throws Exception {
        Test test = MrPolite.one(Test.class).withClassEqualTo(ITest.class, new ITestImpl()).please();

        Assertions.assertThat(test.str).isEqualTo(OLOLO);

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