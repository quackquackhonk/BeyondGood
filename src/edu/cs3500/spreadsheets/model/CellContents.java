package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents the contents of a cell.
 * Cells can be Values (Str, Dbl, Bool), Ops (formulas that store the cells they evaluate),
 * or References (Cell that refers to another cell).
 */
public interface CellContents<K> {

  /**
   * Returns Value associated with the cell contents.
   */
  K evaluate();

  /**
   * Returns the string implementation of CellContents.
   */
  String getRaw();

  /**
   * Returns unevaluated String version of CellContents, for dependency checking.
   * Ops will return any Coords they reference, and these can be parsed.
   */
  String stringParams();

  /**
   * Evaluates current cell to its base Value.
   * Throws an exception if this CellContents can't be evaluated.
   *
   * @param v the visitor used to evaluate cell
   * @return base Value of cell.
   */
  Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) throws IllegalArgumentException;

  /**
   * Returns the CellContents of the given cell in an ArrayList.
   *
   * @param v represent a visitor for evaluation
   * @return
   */
  ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v);
}
