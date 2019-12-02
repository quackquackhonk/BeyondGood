package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.provider.model.Cell;
import edu.cs3500.spreadsheets.provider.model.CellValue;

/**
 * Interface for adapting CellContents to Cells for Provider code.
 */
public class ccToCellAdapter implements Cell {
  CellContents cell;
  Value value;
  /**
   * Constructs CellContents to Cell adapter object.
   */
  public ccToCellAdapter (CellContents cell, Value value) {
    this.cell = cell;
    this.value = value;
  }

  /**
   * To get the value of every single cell.
   *
   * @return the cellvalue of each cell.
   */
  @Override
  public CellValue getValue() {

    return new vToCellValueAdapter(this.value);
  }

  /**
   * To get the raw content of every single cell.
   *
   * @return the a String of raw content of each cell.
   */
  @Override
  public String getRowContent() {
    return this.cell.getRaw();
  }
}
