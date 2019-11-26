package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ControllerFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * Textual view for ISpreadSheetModels.
 */
public class SpreadsheetTextualView implements IView {
  IReadWorkSheetModel model;
  Appendable out;

  /**
   * Constructs a view class.
   *
   * @param model model to render
   * @param out   render to this.
   */
  public SpreadsheetTextualView(IReadWorkSheetModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  /**
   * Renders state of model.
   */
  @Override
  public void render() throws IOException {
    HashSet<Coord> allCells = model.activeCells();
    for (Coord c : allCells) {
      String cellText = model.getCellText(c);
      if (!cellText.equals("")) {
        String toAdd = c.toString() + " " + cellText;
        out.append(toAdd);
        out.append("\n");
      }
    }
  }

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  @Override
  public void makeVisible() {
    // Not necessary in textual view
  }

  /**
   * Provide the view with a callback option to process a command.
   *
   * @param callback object
   */
  @Override
  public void setCommandCallback(Consumer<String> callback) {

  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error message.
   */
  @Override
  public void showErrorMessage(String error) {
    throw new UnsupportedOperationException("Textual view processes all cells and saves errors");
  }

  /**
   * this is to force the view to have a method to set up the keyboard. The name has been chosen
   * deliberately. This is the same method signature to add a key listener in Java Swing.
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener
   */
  @Override
  public void addKeyListener(KeyListener listener) {

  }

  /**
   * Forces view to have a method to set up listeners for mouse events. For Swing views, this method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   *
   * @param listener the MouseListener to add.
   */
  @Override
  public void addMouseListener(MouseListener listener) {

  }

  /**
   * this is to force the view to have a method to set up actions for buttons. All the buttons must
   * be given this action listener
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener
   */
  @Override
  public void addActionListener(ActionListener listener) {

  }

  /**
   * Gets the text inputted by th user that may be used to create a new cell.
   */
  @Override
  public String getInputText() {
    // Text only View doesn't set input text
    return null;
  }

  @Override
  public Coord coordFromLoc(int x, int y) {
    // TODO: put something here.
    return null;
  }

  @Override
  public Coord getSelectedCell() {
    return null;
    // No selection in text view.
  }

  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {
    // textual view doesn't need setup.
  }

  @Override
  public void updateView(Coord coord, String cell) {
    // Read only view not updated.
  }

  /**
   * Reverts input state prior to user modification.
   */
  @Override
  public void resetInput() {
    // No input to reset.
  }

  @Override
  public String getColToAdd() {
    throw new UnsupportedOperationException("Read-only views cannot add columns");
  }

  @Override
  public void setColToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add columns");
  }

  @Override
  public String getRowToAdd() {
    throw new UnsupportedOperationException("Read-only views cannot add rows");
  }

  @Override
  public void setRowToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add rows");
  }

  @Override
  public void addFeatures(ControllerFeatures f) {
    // NO features to add
  }

  /**
   * Changed the currently highlighted cell using the arrow keys of a keyboard.
   *
   * @param coord new selection based on keyboard input
   */
  @Override
  public void cellSelectWithKey(Coord coord) {
    // Text view doesn't change selection
  }

  /**
   * Reset focus of the view such that keyboard interactivity can occur.
   */
  @Override
  public void resetFocus() {

  }

  /**
   * Expand the range of cells to be displayed by the view to the new given ranges.
   *
   * @param maxCol
   * @param maxRow
   */
  @Override
  public void resizeView(int maxCol, int maxRow) {
    // Read only view is not resized ever.
  }

  /**
   * Sets the default input text that the user can then modify.
   */
  @Override
  public void setInputText(String s) {
    // Text only View doesn't set input text
  }
}
