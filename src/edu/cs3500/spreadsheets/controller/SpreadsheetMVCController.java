package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.view.IView;
import java.awt.Point;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MVC Controller for IWriteWorksheetModels and IViews. Facilitates communication between the two
 * that otherwise could not happen.
 */
public class SpreadsheetMVCController implements SpreadsheetController {

  private IWriteWorkSheetModel model;
  private IView view;

  /**
   * Initializes the controller to have control over the given model.
   *
   * @param model the model to have control over.
   */
  public SpreadsheetMVCController(IWriteWorkSheetModel model) {
    this.model = model;
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
        System.out.println(msg + " " + c.toString());
        if (msg.contains("cycle")) {
          stringCells.put(c, "#REF!");
        } else if (msg.contains("Formula")) {
          stringCells.put(c, "#VALUE!");
        }
      }
    }
    return stringCells;
  }

  /**
   * Sets the view that the controller has control over and initializes the IView for potential
   * interaction.
   *
   * @param view the view to control.
   */
  @Override
  public void setView(IView view) {
    HashMap<Coord, String> stringCells = cellsFromModel(this.model);

    this.view = view;
    view.setupView(stringCells, model.getMaxCol(), model.getMaxRow());
    view.addFeatures(this);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
    view.makeVisible();

  }

  /**
   * Fetches the (possibly) new cell data from the user input and puts it into the currently
   * selected cell.
   */
  @Override
  public void confirmInput() {
    String input = view.getInputText();
    Coord location = view.getSelectedCell();
    if (location != null && input != null) {
      HashSet<Coord> toUpdate = model.setCellAllowErrors(location, input);
      System.out.println(toUpdate);
      HashMap<Coord, String> updatedCells = this.recalculateCells(toUpdate);

      // Update cells dependent on newly added cells
      for (Coord c : updatedCells.keySet()) {
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

  /**
   * Clears the input from the user input and reverts back to the old cell contents before they were
   * modified by the user.
   */
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

    view.resetInput();
    view.setInputText(cellText);
  }

  /**
   * Adds a column at the index specified by the column adding text field. If the given column is
   * further out in the grid than the view is currently rendering, expand the size of the grid to
   * accommodate the new size.
   */
  @Override
  public void addColumn() {
    String toAdd = view.getColToAdd();
    //String of any length of only alphabetical characters
    final Pattern colName = Pattern.compile("([A-Za-z]+)");
    Matcher m = colName.matcher(toAdd);
    // valid col name
    if (m.matches()) {
      int colIdx = Coord.colNameToIndex(toAdd);
      view.resizeView(colIdx, model.getMaxRow());
      view.setColToAdd("");
      try {
        view.render();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else { // does not match
      view.showErrorMessage("Please enter a valid column name (alphabetical characters only)");
    }
  }

  /**
   * Adds as a row at the index specified by the row adding text field. If the given row index is
   * further out in the grid than the max row of the grid in the view, expand the size of the grid
   * to accommodate the new size.
   */
  @Override
  public void addRow() {
    String toAdd = view.getRowToAdd();
    try {
      int rowIdx = Integer.parseInt(toAdd);
      if (rowIdx <= 0) {
        view.showErrorMessage("Row index must be greater than 0");
      } else {
        view.resizeView(model.getMaxCol(), rowIdx);
        view.setRowToAdd("");
        try {
          view.render();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (NumberFormatException e) {
      view.showErrorMessage("Please enter a valid row name (numerical characters only)");
    }
  }

  /**
   * Selects the cell that was clicked on.
   *
   * @param loc the point that was clicked on, given in the x and y pixel coordinates relative to
   *            the grid JPanel in the view.
   */
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

  @Override
  public void deleteCellContents() {
    System.out.println("clearing cell contents");
    // clears the input text
    view.setInputText("");
    // confirms the blank input
    this.confirmInput();
  }

  /**
   * Changed the currently highlighted cell using the arrow keys of a keyboard.
   *
   * @param x change in x direction
   * @param y change in y direction
   */
  @Override
  public void cellSelectWithKey(int x, int y) {
    view.cellSelectWithKey(x, y);
    try {
      view.render();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Appends an '=' onto the beginning of the given string if it is only a cell reference (single or
   * multi-cell).
   *
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
