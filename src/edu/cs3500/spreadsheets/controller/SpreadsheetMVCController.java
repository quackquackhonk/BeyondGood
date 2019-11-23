package edu.cs3500.spreadsheets.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.view.IView;

public class SpreadsheetMVCController implements SpreadsheetController, SpreadsheetPublisher {
  private IWriteWorkSheetModel model;
  private IView view;
  private List<SpreadsheetObserver> observerList;

  /**
   * Initializes the controller to have control over the given model.
   * @param model the model to have control over.
   */
  public SpreadsheetMVCController(IWriteWorkSheetModel model) {
    this.model = model;
    // TODO: set up observer for the model.
  }

  @Override
  public void setView(IView view) {
    this.view = view;

    this.view.addActionListener(this.configureButtonListener());
    this.view.addKeyListener(this.configureKeyboardListener());
    this.view.addMouseListener(this.configureMouseListener());
  }

  @Override
  public void addObserver(SpreadsheetObserver observer) {
    this.observerList.add(observer);
  }

  @Override
  public void removeObserver(SpreadsheetObserver observer) {
    this.observerList.remove(observer);
  }

  @Override
  public void notifyObservers(String event) {
    this.observerList.forEach(observer -> observer.update(event));
  }

  /**
   * Creates a ButtonListener with all the specified button functionality for this specific
   * controller to have. This ButtonListener can then be passed into the view in order for the
   * view to start listening for those specific events.
   * @return the configured ButtonListener.
   */
  private ButtonListener configureButtonListener() {
    ButtonListener btn = new ButtonListener();

    return btn;
  }

  /**
   * Creates a new KeyboardListener with all the specified keyboard functionality that this
   * controller needs. This KeyboardListener can then be passed into the view so that the view
   * can start listening for those specific events.
   * @return the configured KeyboardListener.
   */
  private KeyboardListener configureKeyboardListener() {
    KeyboardListener kbd = new KeyboardListener();

    //TODO: finish

    return kbd;
  }

  /**
   * Creates a new MouseEventListener with all the specified functionality that this controller
   * needs. This MouseListener can then be passed into the view so that the view can start
   * listening for those specific events.
   * @return the configured MouseEventListener.
   */
  private MouseEventListener configureMouseListener() {
    MouseEventListener mel = new MouseEventListener();
    Map<Integer, MouseRunnable> mouseClickMap = new HashMap<>();

    mouseClickMap.put(MouseEvent.BUTTON1, loc -> System.out.println(view.coordFromLoc(loc.x,
            loc.y)));
    //mouseClickMap.put(MouseEvent.BUTTON1, loc -> System.out.println(loc));

    mel.setMouseClickedMap(mouseClickMap);

    // TODO: finish

    return mel;
  }

}
