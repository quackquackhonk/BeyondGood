package edu.cs3500.spreadsheets.provider.controller;

import java.io.IOException;

/**
 * Provides a controller for interacting with the spreadsheet.
 */

public interface IController {


  /**
   * run the controller.
   * @throws IOException when io error occur.
   */
  void run() throws IOException;


}
