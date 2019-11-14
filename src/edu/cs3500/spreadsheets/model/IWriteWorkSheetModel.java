package edu.cs3500.spreadsheets.model;

/**
 * To represent a model for a worksheet. Maintains state and enforces class invariants.
 */
public interface IWriteWorkSheetModel<CellContents> extends IReadWorkSheetModel<CellContents> {

  /**
   * Replaces cell at the given location with the cell created from the provided value.
   *
   * @param location is the coordinates of the cell
   * @param value    is the row input, parsable as an s-expression
   */
  void updateCell(Coord location, String value);

  /**
   * Prints result of evaluated cell at given coordinate.
   *
   * @param coord is string coordinate
   */
  void evaluateIndCell(String coord);

  /**
   * Shifts given cell contents based on x and y axis.
   *
   * @param c is a content to be shifted
   * @param x is a column
   * @param y is a row
   */
  void shiftCells(CellContents c, int x, int y);

  /**
   * Applies origin cell to all cells within range.
   *
   * @param start  starting coordinate
   * @param finish ending coordinate
   */
  void dragChange(Coord start, Coord finish);

  /**
   * Checks the validity of the model.
   */
  void setupModel();

  /**
   * Build a cell at a given location provided with col and row numbers.
   *
   * @param col      represents the col number
   * @param row      represents the row number
   * @param contents the content of the cell
   * @throws IllegalStateException    if the model was not checked
   * @throws IllegalArgumentException if the location of the cell is invalid
   */
  void buildCell(int col, int row, CellContents contents);

  /**
   * Sets a cell given raw String containing a coordinate and raw cell contents.
   */
  void setCell(int col, int row, String s);
}
