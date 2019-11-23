package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
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
}
