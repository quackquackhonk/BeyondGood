package edu.cs3500.spreadsheets.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling all key events in the controller. Has multiple maps in order to run the
 * correct action based on the specific key event. Copied from MVC Lecture notes code.
 */
public class KeyboardListener implements KeyListener {
  private Map<Character, Runnable> keyTypedMap = new HashMap<>();
  private Map<Integer, Runnable> keyPressedMap = new HashMap<>();
  private Map<Integer, Runnable> keyReleasedMap = new HashMap<>();

  /**
   * Set the map for key typed events. Key typed events in Java Swing are characters.
   *
   * @param map the map of key typed events.
   */
  public void setKeyTypedMap(Map<Character, Runnable> map) {
    keyTypedMap = map;
  }

  /**
   * Set the map for key pressed events. Key pressed events in Java Swing are integer codes.
   *
   * @param map the map of key pressed events.
   */
  public void setKeyPressedMap(Map<Integer, Runnable> map) {
    keyPressedMap = map;
  }

  /**
   * Set the map for key released events. Key released events in Java Swing are integer codes.
   *
   * @param map the map of key released events.
   */
  public void setKeyReleasedMap(Map<Integer, Runnable> map) {
    keyReleasedMap = map;
  }

  /**
   * This is called when the view detects that a key has been typed. Find if anything has been
   * mapped to this key character and if so, execute it.
   *
   * @param e the KeyEvent that was detected by the view.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    if (keyTypedMap.containsKey(e.getKeyChar()))
      keyTypedMap.get(e.getKeyChar()).run();
  }

  /**
   * This is called when the view detects that a key has been pressed. Find if anything has been
   * mapped to this key code and if so, execute it
   *
   * @param e the KeyEvent that was detected by the view.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    if (keyPressedMap.containsKey(e.getKeyCode()))
      keyPressedMap.get(e.getKeyCode()).run();
  }

  /**
   * This is called when the view detects that a key has been released. Find if anything has been
   * mapped to this key code and if so, execute it
   *
   * @param e the KeyEvent that was detected by the view.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    if (keyReleasedMap.containsKey(e.getKeyCode()))
      keyReleasedMap.get(e.getKeyCode()).run();
  }
}
