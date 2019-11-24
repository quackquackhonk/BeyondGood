package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.Consumer;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Interface for views of IWorkSheetModels.
 */
public interface IView {
  /**
   * Renders state of model.
   */
  void render() throws IOException;

  /**
   * Make the view visible. This is usually called after the view is constructed.
   */
  void makeVisible();

  /**
   * Provide the view with a callback option to process a command.
   *
   * @param callback object
   */
  void setCommandCallback(Consumer<String> callback);

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error message.
   */
  void showErrorMessage(String error);

  /**
   * Forces view to have a method to set up listeners for buttons. For Swing views, this method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   * @param listener the ActionListener to add.
   */
  void addActionListener(ActionListener listener);

  /**
   * Forces view to have a method to set up listeners for key events. For Swing views, this method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   * @param listener the KeyListener to add.
   */
  void addKeyListener(KeyListener listener);

  /**
   * Forces view to have a method to set up listeners for mouse events. For Swing views, this
   * method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   * @param listener the MouseListener to add.
   */
  void addMouseListener(MouseListener listener);

  /**
   * Sets default user input.
   */
  void setInputText(String s);

  /**
   * Gets the text the user has inputted in the input field.
   */
  String getInputText();

  /**
   * Determine corresponding Coord from x position and y position on the worksheet.
   *
   * @param x x position
   * @param y y position
   * @return Coord that corresponds to inputs
   */
  Coord coordFromLoc(int x, int y);

  /**
   * The Coord of the cell currently selected by the user.
   *
   * @return Coord of highlighted cell
   */
  Coord getSelectedCell();

  /**
   * Initializes view by passing in the cells to display and the range of cells to display.
   *
   * @param stringCells All cells in the sheet.
   * @param maxCol render cells up to this column
   * @param maxRow render cells up to this row
   */
  void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow);

  /**
   * Update the view with new cells.
   *
   * @param coord location of cell.
   * @param cell contents of new cell in String form
   */
  void updateView(Coord coord, String cell);

  /**
   * Reverts input state prior to user modification.
   */
  void resetInput();
}
