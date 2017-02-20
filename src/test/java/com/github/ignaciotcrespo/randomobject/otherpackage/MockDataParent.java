package com.github.ignaciotcrespo.randomobject.otherpackage;

/**
 * Created by crespo on 2/20/17.
 */
public class MockDataParent {
    public static class MockDataInnerStatic {
        public String textPublic;
        String textDefault;
        protected String textProtected;
        private String textPrivate;

        public boolean hasValidData() {
            return has(textDefault)
                    && has(textProtected)
                    && has(textPublic)
                    && has(textPrivate);
        }

        private boolean has(String text) {
            return text != null && text.length() > 0;
        }
    }
}
