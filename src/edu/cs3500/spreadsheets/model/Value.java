package edu.cs3500.spreadsheets.model;

/**
 * Interface to organize Value type CellContents. Values are Str, Dbl, and Bool.
 * These are base types that cannot be further collapsed, unlike reference cells or Ops.
 */
public interface Value<K> extends CellContents<K> {

  /**
   * Returns a Dbl if the Value is of type Dbl, otherwise throws an exception.
   * Used for evaluation in formulas. If a SUM requests the Dbl from a Str, the exception
   * is thrown as a Str is not a valid parameter type for SUM.
   */
  Dbl getDbl() throws IllegalArgumentException;

  /**
   * Return this Str.
   *
   * @return Str will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  Str getStr() throws IllegalArgumentException;

  /**
   * Return this Bool.
   *
   * @return Bool will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  Bool getBool() throws IllegalArgumentException;


}
