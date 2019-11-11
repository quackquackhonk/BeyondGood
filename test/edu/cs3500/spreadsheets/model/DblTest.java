package edu.cs3500.spreadsheets.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test class for Dbls.
 */
public class DblTest {
    Dbl test = new Dbl(1);

    @Test
    public void evaluate() {
        assertEquals(new Double(1), test.evaluate());
    }

    @Test
    public void testToString() {
        assertEquals("1.000000", test.toString());
    }

    @Test
    public void getRaw() {
        assertEquals("1.000000", test.getRaw());
    }

    @Test
    public void getDbl() {
        assertEquals(test, test.getDbl());
    }
}