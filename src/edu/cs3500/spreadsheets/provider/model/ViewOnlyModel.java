package edu.cs3500.spreadsheets.provider.model;


/**
 * a ViewOnlyModel to ensure no mutate.
 */
public class ViewOnlyModel implements SpreadsheetModel {
  SpreadsheetModel model;

  /**
   * To construct the ViewOnlyModel.
   * @param spreadsheet the SpreadsheetModel
   */
  public ViewOnlyModel(SpreadsheetModel spreadsheet) {
    this.model = spreadsheet;
  }

  @Override
  public Cell getCellAt(int x, int y) {
    return model.getCellAt(x, y);
  }

  @Override
  public Cell getCellAt(Coord c) {
    return model.getCellAt(c);
  }

  @Override
  public void setCell(int x, int y, String s) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void removeCell(Coord c) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void addColumn() {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void addColumn(int index) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void removeColumn(int index) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public int getWidth() {
    return model.getWidth();
  }

  @Override
  public int getHeight() {
    return model.getHeight();
  }

  @Override
  public void addRow() {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void addRow(int index) {
    throw new UnsupportedOperationException("View Only");
  }

  @Override
  public void removeRow(int index) {
    throw new UnsupportedOperationException("View Only");
  }

}
