package com.mikalai;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * User: nike
 * Date: 11/14/13
 */

public class SimpleTest {

    private Simple app = new Simple();
    @Test
    public void testNothing() throws Exception {

        assertTrue(app.doNothing());
        assertTrue(true);
    }
}
