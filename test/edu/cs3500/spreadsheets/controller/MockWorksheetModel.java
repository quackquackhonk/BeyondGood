package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;

import java.util.HashSet;
import java.util.Locale;

/**
 * Mock model that takes in an Appendable for testing purposes.
  */
public class MockWorksheetModel extends WorkSheetModel  {
  public StringBuilder log = new StringBuilder();

  /**
   * Creates and sets cell at given Coordinate regardless of errors it creates, returns set of cells
   * dependent on new cell.
   *
   * @param coord
   * @param cell
   */
  @Override
  public HashSet<Coord> setCellAllowErrors(Coord coord, String cell) {
    return super.setCellAllowErrors(coord, cell);
  }

  /**
   * Replaces cell at the given location with the cell created from the provided value.
   *
   * @param location is the coordinates of the cell
   * @param value    is the row input, parsable as an s-expression
   */
  @Override
  public void updateCell(Coord location, String value) {
    super.updateCell(location, value);
  }

  /**
   * Prints result of evaluated cell at given coordinate.
   *
   * @param coord is string coordinate
   */
  @Override
  public void evaluateIndCell(String coord) {
    super.evaluateIndCell(coord);

  }

  /**
   * Applies origin cell to all cells within range.
   *
   * @param start  starting coordinate
   * @param finish ending coordinate
   */
  @Override
  public void dragChange(Coord start, Coord finish) {
    super.dragChange(start, finish);
  }

  /**
   * Checks the validity of the model.
   */
  @Override
  public void setupModel() {
    super.setupModel();
  }

  /**
   * Remove cell at Coordinate.
   *
   * @param target
   */
  @Override
  public void removeCell(Coord target) {
    super.removeCell(target);
  }

  /**
   * Sets a cell given raw String containing a coordinate and raw cell contents.
   *
   * @param col
   * @param row
   * @param s
   */
  @Override
  public void setCell(int col, int row, String s) {
    super.setCell(col, row, s);
  }

  /**
   * Returns the set of Coords initiated or depended on by other Coords.
   *
   * @return active Coords
   */
  @Override
  public HashSet<Coord> activeCells() {
    return super.activeCells();
  }

  /**
   * Prints result of evaluated cell at given coordinate.
   *
   * @param coord represents a String coordinate
   */
  @Override
  public String evaluateCell(String coord) {
    return super.evaluateCell(coord);
  }

  /**
   * Returns a String of the evaluated Cell at the given column and row.
   *
   * @param col
   * @param row
   */
  @Override
  public String evaluateCell(int col, int row) {
    return super.evaluateCell(col, row);
  }

  /**
   * Evaluates a cell into a string, throws errors if in a cycle or poorly formatted.
   *
   * @param coord target cell
   * @return evaluated cell contents.
   */
  @Override
  public String evaluateCellCheck(String coord) {
    return super.evaluateCellCheck(coord);
  }

  /**
   * Returns the raw text of the cell at given Coordinate.
   *
   * @param coord is a coordinate
   */
  @Override
  public String getCellText(Coord coord) {
    log.append(super.getCellText(coord)).append(" at ").append(coord).append("\n");
    return super.getCellText(coord);
  }

  /**
   * Returns the raw text.
   *
   * @param col
   * @param row
   */
  @Override
  public String getCellText(int col, int row) {
    return getCellText(col, row);
  }

  /**
   * Does the model have any cells in error. Includes cycles and invalid formulas.
   */
  @Override
  public Boolean hasErrors() {
    return super.hasErrors();
  }

  /**
   * Returns the max width of sheet.
   *
   * @return the width of the row
   */
  @Override
  public int getMaxRow() {
    return super.getMaxRow();
  }

  /**
   * Returns the min width of sheet.
   *
   * @return the width of the row
   */
  @Override
  public int getMinRowWidth() {
    return super.getMinRowWidth();
  }

  /**
   * Returns the min height of sheet.
   *
   * @return the height of the col
   */
  @Override
  public int getMinColHeight() {
    return super.getMinColHeight();
  }

  /**
   * Returns the max height of the sheet.
   *
   * @return the height of the col
   */
  @Override
  public int getMaxCol() {
    return super.getMaxCol();
  }
}
