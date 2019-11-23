package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.view.IView;

/**
 * Interface for writing a controller for the BeyondGood Spreadsheet.
 */
public interface SpreadsheetController {

  /**
   * Sets the view that the controller has control over. Sets up any event listeners that may be
   * necessary as well.
   * @param view the view to control.
   */
  void setView(IView view);




}
