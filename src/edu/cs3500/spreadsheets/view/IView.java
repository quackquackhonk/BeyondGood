package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
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
}
