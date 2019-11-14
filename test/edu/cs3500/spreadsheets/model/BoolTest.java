package edu.cs3500.spreadsheets.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Bools.
 */
public class BoolTest {
  Bool testBool = new Bool(true);
  Bool testFalse = new Bool(false);

  // Test evaluate of bool
  @Test
  public void evaluate() {
    assertFalse(testFalse.evaluate());
    assertTrue(testBool.evaluate());
  }

  @Test
  public void getRaw() {
    assertEquals(testFalse.getRaw(), "false");
    assertEquals(testBool.getRaw(), "true");
  }


  @Test(expected = IllegalArgumentException.class)
  public void getDbl() {
    assertEquals(testFalse.getDbl(), testFalse);
    assertEquals(testBool.getDbl(), testBool);
  }
}