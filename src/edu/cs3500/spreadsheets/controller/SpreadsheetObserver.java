package edu.cs3500.spreadsheets.controller;

/**
 * Interface for observing changes in the spreadsheet in order to update the model.
 */
public interface SpreadsheetObserver {

  /**
   * Signals an update based on the given event.
   * @param event the event that just happened.
   */
  void update(String event);

}
