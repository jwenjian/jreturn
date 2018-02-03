package com.github.jwenjian.jreturn;

import org.junit.Assert;
import org.junit.Test;

public class JReturnTest {

    @Test
    public void testSingleValue() {

        try {
            JReturn.withSingleValue().wrap("Hello, JReturn").getSingleValue(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertEquals(e.getMessage(), "class of single value cannot be null!");
        }

        try {
            JReturn.withSingleValue().wrap("Hello, JReturn").getSingleValue(Integer.class);
        } catch (Exception e) {
            Assert.assertEquals(e.getMessage(), "java.lang.String cannot be cast to java.lang.Integer!");
        }

        String singleValue = JReturn.withSingleValue().wrap("Hello, JReturn").getSingleValue(String.class);
        Assert.assertEquals("Hello, JReturn", singleValue);

    }
}
