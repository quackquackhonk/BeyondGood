//package edu.cs3500.spreadsheets.model;
//
//import edu.cs3500.spreadsheets.provider.model.Cell;
//import edu.cs3500.spreadsheets.provider.model.Coord;
//import edu.cs3500.spreadsheets.provider.model.IProvSpreadsheetModel;
//
//public class WorksheetAdapter extends WorkSheetModel implements IProvSpreadsheetModel {
//
//  /**
//   * Returns the Cell at the specified col and row, 1-indexed numeric value..
//   *
//   * @param x col .
//   * @param y row.
//   * @return the cell at the given position, or <code>null</code> if no cell is there
//   */
//  @Override
//  public Cell getCellAt(int x, int y) {
//    return null;
//  }
//
//  /**
//   * Returns the Cell at the specified coordinate.
//   *
//   * @param c coordinate.
//   * @return the cell at the given position, or <code>null</code> if no cell is there
//   */
//  @Override
//  public Cell getCellAt(Coord c) {
//    return null;
//  }
//
//  /**
//   * remove the cell at the given coordinates.
//   *
//   * @param c coordinate.
//   */
//  @Override
//  public void removeCell(Coord c) {
//
//  }
//
//  /**
//   * add a row to the spreadsheet.
//   */
//  @Override
//  public void addRow() {
//
//  }
//
//  /**
//   * add a row to the given index of the spreadsheet. 0-indexed.
//   *
//   * @param index of the row that is added.
//   * @throws IllegalArgumentException if the index is invalid.
//   */
//  @Override
//  public void addRow(int index) throws IllegalArgumentException {
//
//  }
//
//  /**
//   * add a column to the spreadsheet.
//   */
//  @Override
//  public void addColumn() {
//
//  }
//
//  /**
//   * add a column to the given index of the spreadsheet. 0-indexed.
//   *
//   * @param index of the column that is added.
//   * @throws IllegalArgumentException if the index is invalid.
//   */
//  @Override
//  public void addColumn(int index) throws IllegalArgumentException {
//
//  }
//
//  /**
//   * remove a row to the given index of the spreadsheet. 0-indexed.
//   *
//   * @param index of the row that is removed.
//   * @throws IllegalArgumentException if the index is invalid.
//   */
//  @Override
//  public void removeRow(int index) throws IllegalArgumentException {
//
//  }
//
//  /**
//   * remove a column to the given index of the spreadsheet. 0-indexed.
//   *
//   * @param index of the column that is removed.
//   * @throws IllegalArgumentException if the index is invalid.
//   */
//  @Override
//  public void removeColumn(int index) throws IllegalArgumentException {
//
//  }
//
//  /**
//   * Returns width of the spreadsheet.
//   *
//   * @return width.
//   */
//  @Override
//  public int getWidth() {
//    return 0;
//  }
//
//  /**
//   * Returns height of the spreadsheet.
//   *
//   * @return height.
//   */
//  @Override
//  public int getHeight() {
//    return 0;
//  }
//}