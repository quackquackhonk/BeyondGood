package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.provider.model.Coord;

/**
 * To represent various functions of a spreadsheet.
 */
public interface Features {

  /**
   * To confirm the contents that were entered in the bar.
   */
  void confirm();

  /**
   * To reject the contents that were entered in the bar.
   */
  void reject();

  /**
   * To select the specific cell according to its coordinate.
   *
   * @param c the coordinate of the cell.
   */
  void selectCell(Coord c);



  /**
   * move the selected cell to the upward direction by pressing "up" arrow key.
   */
  void moveSelectCellUp();

  /**
   * move the selected cell to the downward direction by pressing "down" arrow key.
   */
  void moveSelectCellDown();

  /**
   * move the selected cell to the left direction by pressing "left" arrow key.
   */
  void moveSelectCellLeft();

  /**
   * move the selected cell to the right direction by pressing "right" arrow key.
   */
  void moveSelectCellRight();

  /**
   * use the Delete key to clear a cell’s contents.
   */
  void deleteCellContent();

  /**
   * To save the current file in the spreadsheet.
   */
  void saveFile();

  /**
   * To load a new file in the spreadsheet.
   *
   * @param fileName name of the loaded file.
   */
  void loadFile(String fileName);
}
