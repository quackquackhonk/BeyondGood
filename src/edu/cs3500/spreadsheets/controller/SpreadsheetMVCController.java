package edu.cs3500.spreadsheets.controller;

import java.util.List;

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
    //TODO: set up listeners for the view.
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

    //TODO: finish

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

}
