package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ControllerFeatures;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.HashMap;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Interface for views of IWorkSheetModels.
 */
public interface IView {
  /**
   * Repaint the view.
   */
  void render();

  /**
   * Make the view visible. If this isn't called, the result of render() isn't visible.
   */
  void makeVisible();

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error message.
   */
  void showErrorMessage(String error);

  /**
   * Forces view to have a method to set up listeners for buttons. For Swing views, this method will
   * already be implemented through Java Swing. For non-swing views, this will need to be written.
   *
   * @param listener the ActionListener to add.
   */
  void addActionListener(ActionListener listener);

  /**
   * Forces view to have a method to set up listeners for key events. For Swing views, this method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   *
   * @param listener the KeyListener to add.
   */
  void addKeyListener(KeyListener listener);

  /**
   * Forces view to have a method to set up listeners for mouse events. For Swing views, this method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   *
   * @param listener the MouseListener to add.
   */
  void addMouseListener(MouseListener listener);

  /**
   * Sets the text in the formula bar text box.
   */
  void setInputText(String s);

  /**
   * Gets the text from the formula bar text box.
   */
  String getInputText();

  /**
   * Determine corresponding Coord from x position and y position on the worksheet.
   * X position and y position can correspond a mouse's position.
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
   * Initializes view by passing in the cells it needs display and the range of cells to display.
   *
   * @param stringCells All cells in the sheet.
   * @param maxCol      render cells up to this column
   * @param maxRow      render cells up to this row
   */
  void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow);

  /**
   * Update the view with a single new cell instead of passing in ALL cells.
   *
   * @param coord location of cell.
   * @param cell  contents of new cell in String form
   */
  void updateView(Coord coord, String cell);

  /**
   * Resets the text in the formula bar textbox to what it was before.
   */
  void resetInput();

  /**
   * Expand the range of cells to be displayed by the view to the new given ranges.
   */
  void resizeView(int maxCol, int maxRow);

  /**
   * Gets the String in the the "add column" text box.
   *
   * @return the column to add.
   */
  String getColToAdd();

  /**
   * Sets the String in the the "add column" text box.
   *
   * @param s the text to set
   */
  void setColToAdd(String s);

  /**
   * Gets the String in the the "add row" text box.
   *
   * @return the row to add.
   */
  String getRowToAdd();

  /**
   * Sets the String in the the "add row" text box.
   *
   * @param s the text to set
   */
  void setRowToAdd(String s);

  /**
   * Add ControllerFeatures to the view to facilitate Controller/View communication.
   *
   * @param f the features to add
   */
  void addFeatures(ControllerFeatures f);

  /**
   * Changed the currently highlighted cell using the arrow keys of a keyboard.
   *
   * @param coord new selection based on keyboard input
   */
  void cellSelectWithKey(Coord coord);

  /**
   * Reset focus of the view such that keyboard interactivity can occur.
   */
  void resetFocus();
}


