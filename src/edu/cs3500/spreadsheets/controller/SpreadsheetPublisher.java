package edu.cs3500.spreadsheets.controller;

/**
 * Interface for the publisher in the "Observer-publisher" pattern. Used on anything that is
 * being observed by a SpreadsheetObserver.
 */
public interface SpreadsheetPublisher {

  /**
   * Adds an observer to observe any events to this class.
   * @param observer the observer to add.
   */
  void addObserver(SpreadsheetObserver observer);

  /**
   * Stops an observer from observing this class.
   * @param observer the observer to remove.
   */
  void removeObserver(SpreadsheetObserver observer);

  /**
   * Notifies all observers of the given event. Calls observer.observe(event) on all
   * SpreadsheetObservers.
   * @param event the event to notify the observers of.
   */
  void notifyObservers(String event);

}
