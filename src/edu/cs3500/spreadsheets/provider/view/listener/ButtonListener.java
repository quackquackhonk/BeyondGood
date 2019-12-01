package edu.cs3500.spreadsheets.provider.view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * This class represents a Button listener. It is configurable by the controller that
 * instantiates it.
 * This listener keeps a map, one each for button hit.
 * The latter part of that pair is actually a function object, i.e. an object of a class
 * that implements the Runnable interface
 * This class implements the ActionListener interface, so that its object can be used as a
 * valid ActionListener for Java Swing.
 */
public class ButtonListener implements ActionListener {
  Map<String, Runnable> buttonClickedActions;

  /**
   * Empty default constructor.
   */
  public ButtonListener() {
    //empty constructor
  }

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters
   */

  public void setButtonClickedActionMap(Map<String, Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {

      buttonClickedActions.get(e.getActionCommand()).run();
    }
  }
}