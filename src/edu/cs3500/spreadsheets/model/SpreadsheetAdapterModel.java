package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorkSheetModel.EvalVisitor;
import edu.cs3500.spreadsheets.provider.model.Cell;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;

/**
 * Class adapter for making our model work with Provider's view. Takes in an instance of our model
 * and translates target implementation methods.
 */
public class SpreadsheetAdapterModel implements SpreadsheetModel {
  private IWriteWorkSheetModel<CellContents> model;

  /**
   * Constructs adapter class. Takes in a WorkSheetModel.
   */
  public SpreadsheetAdapterModel(IWriteWorkSheetModel<CellContents> model) {
    this.model = model;
  }

  /**
   * Returns the Cell at the specified col and row, 1-indexed numeric value..
   *
   * @param x col .
   * @param y row.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  @Override
  public Cell getCellAt(int x, int y) {
    Coord target = new Coord(x,y);
    CellContents cell = this.model.getCell(new Coord(x, y));
    Cell toRet = null;
    try {
      if(cell != null) {
        toRet = new ccToCellAdapter(cell, this.model.evaluateCellCheck(target.toString()));
      }
    } catch(IllegalArgumentException e) {
      if (e.getMessage().contains("Formula")) {
        toRet = new ccToCellAdapter(cell, new Str("#VALUE!"));
      } else {
        System.out.println(e.getMessage());
        e.printStackTrace();
      }
    }
    return toRet;
  }

  /**
   * Returns the Cell at the specified coordinate.
   *
   * @param c coordinate.
   * @return the cell at the given position, or <code>null</code> if no cell is there
   */
  @Override
  public Cell getCellAt(edu.cs3500.spreadsheets.provider.model.Coord c) {
    return this.getCellAt(c.col, c.row);
  }

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
  @Override
  public void setCell(int x, int y, String data) throws IllegalArgumentException {
    this.model.setCellAllowErrors(new Coord(x, y), data);
  }

  /**
   * remove the cell at the given coordinates.
   *
   * @param c coordinate.
   */
  @Override
  public void removeCell(edu.cs3500.spreadsheets.provider.model.Coord c) {
    this.model.setCellAllowErrors(new Coord(c.col, c.row), "");
  }

  /**
   * add a row to the spreadsheet.
   */
  @Override
  public void addRow() {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * add a row to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the row that is added.
   * @throws IllegalArgumentException if the index is invalid.
   */
  @Override
  public void addRow(int index) throws IllegalArgumentException {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * add a column to the spreadsheet.
   */
  @Override
  public void addColumn() {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * add a column to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the column that is added.
   * @throws IllegalArgumentException if the index is invalid.
   */
  @Override
  public void addColumn(int index) throws IllegalArgumentException {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * remove a row to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the row that is removed.
   * @throws IllegalArgumentException if the index is invalid.
   */
  @Override
  public void removeRow(int index) throws IllegalArgumentException {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * remove a column to the given index of the spreadsheet. 0-indexed.
   *
   * @param index of the column that is removed.
   * @throws IllegalArgumentException if the index is invalid.
   */
  @Override
  public void removeColumn(int index) throws IllegalArgumentException {
    throw new UnsupportedOperationException("View Only");
  }

  /**
   * Returns width of the spreadsheet.
   *
   * @return width.
   */
  @Override
  public int getWidth() {
    return this.model.getMaxCol();
  }

  /**
   * Returns height of the spreadsheet.
   *
   * @return height.
   */
  @Override
  public int getHeight() {
    return this.model.getMaxRow();
  }
}
