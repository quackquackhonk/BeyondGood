package edu.cs3500.spreadsheets.provider.model;


/**
 * To represent a cell in the spreadsheet. An individual spreadsheet cell may: contain a value.
 * contain a formula.
 */
public interface Cell {

  /**
   * To get the value of every single cell.
   *
   * @return the cellvalue of each cell.
   */
  CellValue getValue();

  /**
   * To get the raw content of every single cell.
   *
   * @return the a String of raw content of each cell.
   */
  String getRowContent();
}

