package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Cell that stores a String.
 */
public class Str implements Value<String> {
  private final String str;

  /**
   * Creates a Str cell.
   *
   * @param str - String to store
   */
  public Str(String str) {
    this.str = str;
  }

  /**
   * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
   * boolean.
   */
  @Override
  public String evaluate() {
    return this.getRaw().replace("\\", "\\\\");
  }

  @Override
  public String getRaw() {
    return '"' + this.str + '"';
  }

  @Override
  public String toString() {
    // this should be formatted to have escape characters.
    return this.str;
  }

  @Override
  public String stringParams() {
    return this.getRaw();
  }

  /**
   * Evaluates current cell to its base Value.
   *
   * @param v the visitor used to evaluate cell
   * @return base Value of cell.
   */
  @Override
  public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
    return v.visitStr(this);
  }

  /**
   * Returns this CellContents in ArrayList form.
   *
   * @param v represent a visitor for evaluation
   * @return
   */
  @Override
  public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
    return v.getContentStr(this);
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
}
