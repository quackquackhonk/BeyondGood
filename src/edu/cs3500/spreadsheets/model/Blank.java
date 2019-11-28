package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Blank cells represent empty cells.
 */
public class Blank implements Value {

  /**
   * Creates new Blank cell.
   */
  public Blank() {
    // Blank cells have no contents.
  }

  /**
   * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
   * boolean.
   */
  @Override
  public Object evaluate() {
    return null;
  }

  /**
   * Returns the string implementation of cell contents.
   */
  @Override
  public String getRaw() {
    return "";
  }

  /**
   * Returns unevaluated String version of CellContents, for dependency checking.
   */
  @Override
  public String stringParams() {
    return "";
  }

  /**
   * Returns the string implementation of cell contents.
   */
  @Override
  public String toString() {
    return "";
  }

  /**
   * Evaluates current cell to its base Value.
   *
   * @param v the visitor used to evaluate cell
   * @return base Value of cell.
   */
  @Override
  public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
    return v.visitBlank(this);
  }

  /**
   * Returns the contents of the given cell in an ArrayList.
   *
   * @param v represent a visitor for evaluation
   * @return
   */
  @Override
  public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
    return new ArrayList<CellContents>();
  }

  /**
   * returns Dbl if its a Dbl.
   *
   * @return
   */
  @Override
  public Dbl getDbl() {
    throw new IllegalArgumentException();
  }

  /**
   * Return this Str.
   *
   * @return Str will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  @Override
  public Str getStr() throws IllegalArgumentException {
    throw new IllegalArgumentException();
  }

  /**
   * Return this Bool.
   *
   * @return Bool will return itself, Bool/Dbl throw exceptions.
   * @throws IllegalArgumentException if Value is not a Str
   */
  @Override
  public Bool getBool() throws IllegalArgumentException {
    throw new IllegalArgumentException();
  }

}