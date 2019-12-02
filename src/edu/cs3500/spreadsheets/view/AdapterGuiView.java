package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ControllerFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetEditableGraphicView;
import java.util.HashMap;

public class AdapterGuiView extends SpreadsheetEditableGraphicView implements IView {

  /**
   * constructor for SpreadsheetEditableGraphicView.
   *
   * @param model a viewonly model.
   */
  public AdapterGuiView(ViewOnlyModel model) {
    super(model);
  }

  /**
   * Make the view visible. If this isn't called, the result of render() isn't visible.
   */
  @Override
  public void makeVisible() {
    this.render();
  }

  /**
   * Sets the text in the formula bar text box.
   *
   * @param s
   */
  @Override
  public void setInputText(String s) {
    this.setInputString(s);
  }

  /**
   * Gets the text from the formula bar text box.
   */
  @Override
  public String getInputText() {
    return this.getInputString();
  }

  /**
   * Determine corresponding Coord from x position and y position on the worksheet. X position and y
   * position can correspond a mouse's position.
   *
   * @param x x position
   * @param y y position
   * @return Coord that corresponds to inputs
   */
  @Override
  public Coord coordFromLoc(int x, int y) {
    return null;
  }

  /**
   * The Coord of the cell currently selected by the user.
   *
   * @return Coord of highlighted cell
   */
  @Override
  public Coord getSelectedCell() {
    return null;
  }

  /**
   * Initializes view by passing in the cells it needs display and the range of cells to display.
   *
   * @param stringCells All cells in the sheet.
   * @param maxCol      render cells up to this column
   * @param maxRow      render cells up to this row
   */
  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {

  }

  /**
   * Update the view with a single new cell instead of passing in ALL cells.
   *
   * @param coord location of cell.
   * @param cell  contents of new cell in String form
   */
  @Override
  public void updateView(Coord coord, String cell) {

  }

  /**
   * Resets the text in the formula bar textbox to what it was before.
   */
  @Override
  public void resetInput() {

  }

  /**
   * Expand the range of cells to be displayed by the view to the new given ranges.
   *
   * @param maxCol
   * @param maxRow
   */
  @Override
  public void resizeView(int maxCol, int maxRow) {

  }

  /**
   * Gets the String in the the "add column" text box.
   *
   * @return the column to add.
   */
  @Override
  public String getColToAdd() {
    return null;
  }

  /**
   * Sets the String in the the "add column" text box.
   *
   * @param s the text to set
   */
  @Override
  public void setColToAdd(String s) {

  }

  /**
   * Gets the String in the the "add row" text box.
   *
   * @return the row to add.
   */
  @Override
  public String getRowToAdd() {
    return null;
  }

  /**
   * Sets the String in the the "add row" text box.
   *
   * @param s the text to set
   */
  @Override
  public void setRowToAdd(String s) {

  }

  /**
   * Add ControllerFeatures to the view to facilitate Controller/View communication.
   *
   * @param f the features to add
   */
  @Override
  public void addFeatures(ControllerFeatures f) {

  }

  /**
   * Changed the currently highlighted cell using the arrow keys of a keyboard.
   *
   * @param coord new selection based on keyboard input
   */
  @Override
  public void cellSelectWithKey(Coord coord) {

  }
}
