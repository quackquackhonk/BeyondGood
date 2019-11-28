package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.view.IView;

/**
 * Interface for writing a controller for the BeyondGood Spreadsheet. Extends ControllerFeatures
 */
public interface SpreadsheetController extends ControllerFeatures {

  /**
   * Sets the view that the controller has control over and initializes the IView for potential
   * interaction.
   *
   * @param view the view for the controller to use.
   */
  void setView(IView view);

}
