package edu.cs3500.spreadsheets.provider.model;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * To represent a formula content in the cell. A formula is one of: a value. a reference to a
 * rectangular region of cells in the spreadsheet. a function applied to one or more formulas as its
 * arguments.
 */
public interface Formula {

  /**
   * To get the value of the formula in the cell.
   *
   * @return the cellvalue of the formula.
   */
  CellValue getCellValue();

  /**
   * To get the value of the formula in the cell using accumulator.
   *
   * @param acc accumulator that record the cell value that has been evaluated.
   * @return the cellvalue of the formula.
   */
  CellValue getCellValue(Map<Coord, CellValue> acc);


  /**
   * Check whether the formula refer to itself.
   * @param l list of Coord visited.
   * @return if the formula is cyclic reference.
   */
  boolean checkCyclicReference(List<Coord> l, Set<Coord> noCycles);
}
