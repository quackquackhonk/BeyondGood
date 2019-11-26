package edu.cs3500.spreadsheets.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling all MouseEvents in the controller. Has maps of Runnables as to execute the
 * correct action based on the specific type of MouseEvent. Only "clicking" events have maps.
 * There's no need for entering and exiting to have maps since they have only handle one specific
 * case for mouse handling.
 */
public class MouseEventListener implements MouseListener {
  private Map<Integer, MouseRunnable> mouseClickedMap = new HashMap<>();
  private Map<Integer, MouseRunnable> mousePressedMap = new HashMap<>();
  private Map<Integer, MouseRunnable> mouseReleasedMap = new HashMap<>();
  private Runnable mouseEnteredRunnable = defaultRunnable();
  private Runnable mouseExitedRunnable = defaultRunnable();

  private Runnable defaultRunnable() {
    return () -> {
      return;
    };
  }

  /**
   * Sets the mouseClicked map to the given map. Moused clicked events are integers representing the
   * button that was clicked.
   *
   * @param map the mouseClicked map.
   */
  public void setMouseClickedMap(Map<Integer, MouseRunnable> map) {
    this.mouseClickedMap = map;
  }

  /**
   * Sets the mousePressed map to the given map. Moused pressed events are integers representing the
   * button that was pressed.
   *
   * @param map the mousePressed map.
   */
  public void setMousePressedmap(Map<Integer, MouseRunnable> map) {
    this.mousePressedMap = map;
  }

  /**
   * Sets the mouseReleased map to the given map. Moused released events are integers representing
   * the button that was released.
   *
   * @param map the mouseReleased map.
   */
  public void setMouseReleasedMap(Map<Integer, MouseRunnable> map) {
    this.mouseReleasedMap = map;
  }

  /**
   * Sets the mouseEnteredRunnable to the given runnable.
   *
   * @param r the Runnable to set.
   */
  public void setMouseEnteredRunnable(Runnable r) {
    this.mouseEnteredRunnable = r;
  }

  /**
   * Sets the mouseExitedRunnable to the given runnable.
   *
   * @param r the Runnable to set.
   */
  public void setMouseExitedRunnable(Runnable r) {
    this.mouseExitedRunnable = r;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.mouseClickedMap.containsKey(e.getButton())) {
      this.mouseClickedMap.get(e.getButton()).run(e.getPoint());
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    if (this.mousePressedMap.containsKey(e.getButton())) {
      this.mousePressedMap.get(e.getButton()).run(e.getPoint());
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if (this.mousePressedMap.containsKey(e.getButton())) {
      this.mousePressedMap.get(e.getButton()).run(e.getPoint());
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    this.mouseEnteredRunnable.run();
  }

  @Override
  public void mouseExited(MouseEvent e) {
    this.mouseExitedRunnable.run();
  }
}
