package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a cell with a Boolean as a value.
 */
public class Bool implements Value<Boolean> {
  private final boolean val;

  /**
   * Constructs a Bool cell that stores a boolean.
   *
   * @param val - boolean to store
   */
  public Bool(boolean val) {
    this.val = val;
  }

  /**
   * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
   * boolean.
   *
   * @return true or false, this.val
   */
  @Override
  public Boolean evaluate() {
    return this.val;
  }

  /**
   * returns string version of Bool.
   */
  @Override
  public String toString() {
    return Boolean.toString(this.val);
  }

  /**
   * Returns class as string.
   *
   * @return String representation of this.val.
   */
  @Override
  public String getRaw() {
    return Boolean.toString(this.val);
  }

  /**
   * Returns unevaluated String version of CellContents, for dependency checking.
   */
  @Override
  public String stringParams() {
    return Boolean.toString(this.val);
  }

  /**
   * Evaluates current cell to its base Value.
   *
   * @param v the visitor used to evaluate cell
   * @return base Value of cell.
   */
  @Override
  public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
    return v.visitBool(this);
  }

  /**
   * Returns this CellContents in ArrayList form.
   *
   * @param v represent a visitor for evaluation
   * @return
   */
  @Override
  public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
    return v.getContentBool(this);
  }

  /**
   * returns Dbl if its a Dbl.
   *
   * @return
   */
  @Override
  public Dbl getDbl() {
    throw new IllegalArgumentException("not a Dbl");
  }

  /**
   * Return this Str.
   *
   * @return Str will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  @Override
  public Str getStr() throws IllegalArgumentException {
    throw new IllegalArgumentException("Not a Str");
  }

  /**
   * Return this Bool.
   *
   * @return Bool will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  @Override
  public Bool getBool() throws IllegalArgumentException {
    return this;
  }
}
