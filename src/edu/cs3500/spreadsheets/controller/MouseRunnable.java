package edu.cs3500.spreadsheets.controller;

import java.awt.Point;

/**
 * Function object for handling mouse events. Allows for the passing in of the coordinates of the
 * click when running.
 */
public interface MouseRunnable {

  /**
   * Runs the MouseRunnable function with the given point as the location.
   *
   * @param loc the point that the event occured on.
   */
  void run(Point loc);

}
