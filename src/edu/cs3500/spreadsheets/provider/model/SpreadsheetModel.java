package edu.cs3500.spreadsheets.provider.model;

/**
 * The model of a spreadsheet.
 */
public interface SpreadsheetModel {

  /**
   * Returns the Cell at the specified col and row, 1-indexed numeric value..
   *
   * @param x col .
   * @param y row.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  Cell getCellAt(int x, int y);

  /**
   * Returns the Cell at the specified coordinate.
   *
   * @param c coordinate.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  Cell getCellAt(Coord c);

  /**
   * set the cell at the given coordinates and fills in its raw contents.
   *
   * @param x    the column of the new cell (1-indexed)
   * @param y    the row of the new cell (1-indexed)
   * @param data the raw contents of the new cell: may be {@code null}, or any string. Strings
   *             beginning with an {@code =} character should be treated as formulas; all other
   *             strings should be treated as number or boolean values if possible, and string
   *             values otherwise.
   * @throws IllegalArgumentException if the index is invalid.
   */
  void setCell(int x, int y, String data) throws IllegalArgumentException;


  /**
   * remove the cell at the given coordinates.
   * @param c coordinate.
   */
  void removeCell(Coord c);

  /**
   * add a row to the spreadsheet.
   */
  void addRow();


  /**
   * add a row to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the row that is added.
   * @throws IllegalArgumentException if the index is invalid.
   */
  void addRow(int index) throws IllegalArgumentException;

  /**
   * add a column to the spreadsheet.
   */
  void addColumn();

  /**
   * add a column to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the column that is added.
   * @throws IllegalArgumentException if the index is invalid.
   */
  void addColumn(int index) throws IllegalArgumentException;

  /**
   * remove a row to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the row that is removed.
   * @throws IllegalArgumentException if the index is invalid.
   */
  void removeRow(int index) throws IllegalArgumentException;

  /**
   * remove a column to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the column that is removed.
   * @throws IllegalArgumentException if the index is invalid.
   */
  void removeColumn(int index) throws IllegalArgumentException;

  /**
   * Returns width of the spreadsheet.
   *
   * @return width.
   */
  int getWidth();

  /**
   * Returns height of the spreadsheet.
   *
   * @return height.
   */
  int getHeight();
}

