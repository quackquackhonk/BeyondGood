package edu.cs3500.spreadsheets.controller;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.view.IView;

public class SpreadsheetMVCController implements SpreadsheetController {
  private IWriteWorkSheetModel model;
  private IView view;

  /**
   * Initializes the controller to have control over the given model.
   * @param model the model to have control over.
   */
  public SpreadsheetMVCController(IWriteWorkSheetModel model) {
    this.model = model;
    // TODO: set up observer for the model.
  }

  // Get active model cells, draw them
  private HashMap<Coord, String> cellsFromModel(IWriteWorkSheetModel model) {
    HashSet<Coord> modelCells = model.activeCells();
    HashMap<Coord, String> stringCells = new HashMap<>();

    // Display cycle/formula errors
    for (Coord c : modelCells) {
      try {
        String cellResult = model.evaluateCellCheck(c.toString());
        // only add the cell if it is not empty
        if (!cellResult.equals("")) {
          stringCells.put(c, cellResult);
        }
      } catch (IllegalArgumentException e) {
        String msg = e.getMessage();
        //System.out.println("MVC: " + c.toString() + ": " + msg);
        //System.out.println(msg + " "+ c.toString());
        if (msg.contains("cycle")) {
          stringCells.put(c, "#REF!");
        } else if (msg.contains("Formula")) {
          stringCells.put(c, "#VALUE!");
        }
      }
    }
    return stringCells;
  }

  // Reevaluate cells affected by addition of new cell.
  private HashMap<Coord, String> recalculateCells(HashSet<Coord> cells) {
    HashMap<Coord, String> stringCells = new HashMap<>();
    System.out.println(cells + " recalculating");
    for (Coord c : cells) {
      try {
        String cellResult = model.evaluateCellCheck(c.toString());
        System.out.println(cellResult + " is result");
        // only add the cell if it is not empty

          stringCells.put(c, cellResult);

      } catch (IllegalArgumentException e) {
        String msg = e.getMessage();
        System.out.println(msg + " "+ c.toString());
        if (msg.contains("cycle")) {
          stringCells.put(c, "#REF!");
        } else if (msg.contains("Formula")) {
          stringCells.put(c, "#VALUE!");
        }
      }
    }
    return stringCells;
  }

  @Override
  public void setView(IView view) {
    /* TODO: Decouple model from controller.
             - pass HashMap<Coord, String> to view
             - pass max row and max col to view
             - update these on "confirm formula" and adding new rows/columns
     */
    HashMap<Coord, String> stringCells = cellsFromModel(this.model);

    this.view = view;
    view.setupView(stringCells, model.getMaxCol(), model.getMaxRow());
    view.addFeatures(this);
    System.out.println("View setup");
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    view.makeVisible();

  }

  /**
   * Creates a ButtonListener with all the specified button functionality for this specific
   * controller to have. This ButtonListener can then be passed into the view in order for the
   * view to start listening for those specific events.
   * @return the configured ButtonListener.
   */
  private ButtonListener configureButtonListener() {
    ButtonListener btn = new ButtonListener();
    Map<String, Runnable> buttonClickedActions = new HashMap<>();

    // Create new cell from user text input.
    buttonClickedActions.put("confirm input", this::confirmInput);

    // Clear user text input.
    buttonClickedActions.put("clear input", this::clearInput);

    // adds a column
    buttonClickedActions.put("add column", this::addColumn);

    // adds a row
    buttonClickedActions.put("add row", this::addRow);
    btn.setButtonClickedActionMap(buttonClickedActions);
    return btn;
  }

  @Override
  public void confirmInput() {
    String input = view.getInputText();
    Coord location = view.getSelectedCell();
    if(location != null && input != null) {
      HashSet<Coord> toUpdate = model.setCellAllowErrors(location, input);
      System.out.println(toUpdate);
      HashMap<Coord, String> updatedCells = this.recalculateCells(toUpdate);

      // Update cells dependent on newly added cells
      for(Coord c : updatedCells.keySet()) {
        view.updateView(c, updatedCells.get(c));
        System.out.println("Updating " + c);
      }
    }
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void clearInput() {
    Coord target = view.getSelectedCell();
    String cellText;
    try {
      cellText = model.getCellText(target);
    } catch (IllegalArgumentException n) {
      cellText = "";
    }

    cellText = this.addEqualsIfRef(cellText);

    view.setInputText(cellText);
  }

  @Override
  public void addColumn() {
    String toAdd = view.getColToAdd();
    //String of any length of only alphabetical characters
    final Pattern colName = Pattern.compile("([A-Za-z]+)");
    Matcher m = colName.matcher(toAdd);
    // valid col name
    if (m.matches()) {
      int colIdx = Coord.colNameToIndex(toAdd);
      // TODO: ADD COLUMN
      view.resizeView(colIdx, model.getMaxRow());
      try {
        view.render();
      } catch (IOException e) {
        e.printStackTrace();
      }
      view.showErrorMessage("added column at: " + colIdx);
    } else { // does not match
      view.showErrorMessage("Please enter a valid column name (alphabetical characters only)");
    }
  }

  @Override
  public void addRow() {
    String toAdd = view.getRowToAdd();
    try {
      int rowIdx = Integer.parseInt(toAdd);
      if (rowIdx <= 0) {
        view.showErrorMessage("Row index must be greater than 0");
      } else {
        view.resizeView(model.getMaxCol(), rowIdx);
        try {
          view.render();
        } catch (IOException e) {
          e.printStackTrace();
        }
        view.showErrorMessage("ADDED ROW AT: " + rowIdx);

      }
    } catch (NumberFormatException e) {
      view.showErrorMessage(toAdd + " is not a valid row index. Please enter a valid number.");
    }
  }

  @Override
  public void clickOnCellAt(Point loc) {
    Coord target = view.coordFromLoc(loc.x, loc.y);
    String cellText;
    try {
      cellText = model.getCellText(target);
    } catch (IllegalArgumentException n) {
      cellText = "";
    }

    cellText = this.addEqualsIfRef(cellText);

    view.setInputText(cellText);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Appends an '=' onto the beginning of the given string if it is only a cell reference (single
   * or multi-cell)
   * @param cellText the string to append.
   * @return cellText or "=" + cellText.
   */
  private String addEqualsIfRef(String cellText) {
    // check if this is ONLY a reference, append = to beginning.
    final Pattern singleCellRef = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");
    Matcher singleMatch = singleCellRef.matcher(cellText);
    final Pattern multiCellRef =
            Pattern.compile("([A-Za-z]+)([1-9][0-9]*)([:])([A-Za-z]+)([1-9][0-9]*)");
    Matcher multiMatch = multiCellRef.matcher(cellText);
    if (singleMatch.matches() || multiMatch.matches()) {
      cellText = "=" + cellText;
    }
    return cellText;
  }
}
