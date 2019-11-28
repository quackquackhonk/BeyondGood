package edu.cs3500.spreadsheets.model;

/**
 * A function object for evaluating CellContents. Calls relevant evaluation methods in the
 * model based on type of CellContents.
 */

public interface IEvalVisitor<Value> {
  /**
   * Process a boolean value.
   *
   * @param b the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitBool(Bool b) throws IllegalArgumentException;

  /**
   * Processes a Blank.
   *
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitBlank(Blank b) throws IllegalArgumentException;

  /**
   * Process a Str value.
   *
   * @param s the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitStr(Str s) throws IllegalArgumentException;

  /**
   * Process a Dbl value.
   *
   * @param d the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitDbl(Dbl d) throws IllegalArgumentException;

  /**
   * Process a ReferenceCell value.
   *
   * @param r the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitRefCell(ReferenceCell r) throws IllegalArgumentException;

  /**
   * Process a SUM value.
   *
   * @param s the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitSUM(SUM s) throws IllegalArgumentException;

  /**
   * Process a PRODUCT value.
   *
   * @param p the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitPRODUCT(PRODUCT p) throws IllegalArgumentException;

  /**
   * Process a GREATERTHAN value.
   *
   * @param g the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */
  Value visitGREATERTHAN(GREATERTHAN g) throws IllegalArgumentException;

  /**
   * Process a LESSTHAN value.
   *
   * @param l the value
   * @return the desired result
   * @throws IllegalArgumentException if CC can't be evaluated to a Value
   */

  Value visitLESSTHAN(LESSTHAN l) throws IllegalArgumentException;
}
