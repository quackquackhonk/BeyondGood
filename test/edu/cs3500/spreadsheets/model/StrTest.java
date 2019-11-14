package edu.cs3500.spreadsheets.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * test class for Str.
 */
public class StrTest {

  Str str = new Str("hello \\");

  @Test
  public void evaluate() {
    Str str = new Str("hello \\");
    assertEquals('"' + "hello \\\\" + '"', str.evaluate());
  }

  @Test
  public void getRaw() {
    assertEquals('"' + "hello \\" + '"', str.getRaw());
  }
}