package com.github.jwenjian.jreturn;

import org.junit.Assert;
import org.junit.Test;

public class JReturnTest {

    @Test
    public void testSingleValue() {

        try {
            JReturn.single("Hello, JReturn").unwrap(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertEquals(e.getMessage(), "class of single value cannot be null!");
        }

        try {
            JReturn.single("Hello, JReturn").unwrap(Integer.class);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "java.lang.String cannot be cast to java.lang.Integer!");
        }

        String singleValue = JReturn.single("Hello, JReturn").unwrap(String.class);
        Assert.assertEquals("Hello, JReturn", singleValue);

    }

    @Test
    public void testMultiWithKeyPair() {
        JReturn.R r = JReturn.withKey("test", "value").toR();
        String result = r.unwrap("test", String.class);

        Assert.assertTrue("value".equals(result));

    }
}
