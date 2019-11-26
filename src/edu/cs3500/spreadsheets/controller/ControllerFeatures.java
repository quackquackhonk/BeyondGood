package edu.cs3500.spreadsheets.controller;

import java.awt.*;

/**
 * Interface containing all the features desired by a Controller for the Spreadsheets. Features
 * can be added or removed by extending this interface.
 */
public interface ControllerFeatures {

  /**
   * Fetches the (possibly) new cell data from the user input and puts it into the currently
   * selected cell.
   */
  void confirmInput();

  /**
   * Clears the input from the user input and reverts back to the old cell contents before they
   * were modified by the user.
   */
  void clearInput();

  /**
   * Adds a column at the index specified by the column adding text field.
   * If the given column is further out in the grid than the view is currently rendering, expand
   * the size of the grid to accommodate the new size.
   */
  void addColumn();

  /**
   * Adds as a row at the index specified by the row adding text field.
   * If the given row index is further out in the grid than the max row of the grid in the view,
   * expand the size of the grid to accommodate the new size.
   */
  void addRow();

  /**
   * Selects the cell that was clicked on.
   * @param loc the point that was clicked on, given in the x and y pixel coordinates relative to
   *            the grid JPanel in the view.
   */
  void clickOnCellAt(Point loc);

  /**
   * Changed the currently highlighted cell using the arrow keys of a keyboard.
   *
   * @param x change in x direction
   * @param y change in y direction
   */
  void cellSelectWithKey(int x, int y);

}
