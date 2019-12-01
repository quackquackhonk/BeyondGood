package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.ActionListener;
import java.io.IOException;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.model.Coord;

/**
 * To represent the visible parts of a spread sheet.
 */
public interface SpreadsheetView {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

  /**
   * To realize the function from a spreadsheet.
   *
   * @param features functions of the spreadsheet.
   */
  void addFeatures(Features features);


  /**
   * this is to force the view to have a method to set up actions for buttons. All the buttons must
   * be given this action listener. Thus our Swing-based implementation of this interface will
   * already have such a method.
   *
   * @param listener ActionListener
   */
  void addActionListener(ActionListener listener);


  /**
   * Get the string which user inputs.
   *
   * @return the string that user input.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  String getInputString() throws UnsupportedOperationException;

  /**
   * mutate the exist string to the given string.
   *
   * @param s string that need to be set.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  void setInputString(String s) throws UnsupportedOperationException;

  /**
   * To get the coordinate of the selected cell.
   *
   * @return the coordinate.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  Coord getSelectCoord() throws UnsupportedOperationException;


  /**
   * To set the coordinate of the selected cell.
   *
   * @param c coordinate.
   * @throws UnsupportedOperationException if the view is not editable.
   */
  void setSelectedCoord(Coord c) throws UnsupportedOperationException;

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached to
   * it, so that keyboard events will still flow through.
   */
  void resetFocus();

  /**
   * To display the view once again.
   */
  void repaint();

  /**
   * To get the name of the title.
   *
   * @return the string of the filename.
   */
  String getTitle();

  /**
   * Transmit an message to the view.
   *
   * @param m the message.
   */
  void showMessage(String m);

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error the error message.
   */
  void showErrorMessage(String error);


}
