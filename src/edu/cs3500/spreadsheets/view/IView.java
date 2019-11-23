package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.function.Consumer;

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
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error message.
   */
  void showErrorMessage(String error);

  /**
   * this is to force the view to have a method to set up the keyboard.
   * The name has been chosen deliberately. This is the same method signature to
   * add a key listener in Java Swing.
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a
   * method.
   *
   * @param listener
   */
  void addKeyListener(KeyListener listener);

  /**
   * this is to force the view to have a method to set up actions for buttons.
   * All the buttons must be given this action listener
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a
   * method.
   *
   * @param listener
   */

  void addActionListener(ActionListener listener);

  /**
   * Gets the text inputted by th user that may be used to create a new cell.
   */
  String getInputText();

  /**
   * Sets the default input text that the user can then modify.
   */
  void setInputText(String s);
}
