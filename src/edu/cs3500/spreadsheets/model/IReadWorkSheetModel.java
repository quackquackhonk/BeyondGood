package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * Represents a Read-only Worksheet Model. Contains getter methods
 * that report the state of the model. No methods can mutate the model.
 */
public interface IReadWorkSheetModel<CellContents> {

  /**
   * Returns the set of all cells in the model.
   *
   * @return active Coords
   */
  HashSet<Coord> activeCells();

  /**
   * Returns the evaluated value of a CellContents as a String.
   *
   * @param coord represents a Coord in string form.
   */
  String evaluateCell(String coord);

  /**
   * Returns the evaluated value of a CellContents as a String.
   */
  String evaluateCell(int col, int row);

  /**
   * Returns the evaluated value of a CellContents as a String.
   */
  Value evaluateCellValue(Coord c);

  /**
   * Evaluates the CellContents at the input into a string.
   *
   * @param coord target cell
   * @return evaluated cell contents.
   * @throws IllegalArgumentException if cell is in a cycle or formatted incorrectly.
   */
  Value evaluateCellCheck(String coord);

  /**
   * Evaluates the CellContents at the input into a String.
   * @param coord the target cell.
   * @return the evaluated cell contents.
   */
  String evaluateCellCheckString(String coord);

  /**
   * Returns the raw text of the cell at given Coordinate.
   * Raw = the String used to create the cell, not its evaluated result.
   * ex: =(SUM 1 2), not 3.
   *
   * @param coord is a coordinate
   * @throws IllegalArgumentException if no cell at given Coord
   */
  String getCellText(Coord coord) throws IllegalArgumentException;

  /**
   * Returns the raw text of the cell.
   */
  String getCellText(int col, int row);

  /**
   * Return CellContents at a provided Coord.
   *
   * @param location is the coordinates of the cell
   * @return the CellContents at the provided location. Null if nothing there.
   */
  CellContents getCell(Coord location);

  /**
   * Returns if the model have any cells that can't evaluate or are in cycles.
   */
  Boolean hasErrors();

  /**
   * Returns the highest row number out of all cells in the sheet.
   *
   * @return the width of the row
   */
  int getMaxRow();

  /**
   * Returns the lowest row number out of all cells in the sheet.
   *
   * @return returns
   */
  int getMinRow();

  /**
   * Returns the lowest column number out of all cells in the sheet.
   *
   * @return highest column number in sheet
   */
  int getMinCol();

  /**
   * Returns the highest row number out of all cells in the sheet.
   *
   * @return the height of the col
   */
  int getMaxCol();

}
