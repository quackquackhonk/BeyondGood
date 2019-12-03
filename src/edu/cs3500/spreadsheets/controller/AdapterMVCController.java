package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetAdapterModel;
import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.controller.IController;
import edu.cs3500.spreadsheets.provider.model.Cell;
import edu.cs3500.spreadsheets.provider.model.Coord;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetEditableGraphicView;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.IView;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class AdapterMVCController extends SpreadsheetMVCController implements Features,
    IController {

  SpreadsheetModel model;
  SpreadsheetView view;

  /**
   * Initializes the controller to have control over the given model.
   *
   * @param model the model to have control over.
   */
  public AdapterMVCController(IWriteWorkSheetModel model, IView v) {
    super(model);
    //super.setView(v);

    this.model = new SpreadsheetAdapterModel(model);
    this.view = new SpreadsheetEditableGraphicView(new ViewOnlyModel(this.model));
  }

  /**
   * To confirm the contents that were entered in the bar.
   */
  @Override
  public void confirm() {
    String input = view.getInputString();
    Coord location = view.getSelectCoord();
    if (location != null && input != null) {
      model.setCell(location.col, location.row, input);
    }
    view.resetFocus();
    view.repaint();
  }

  /**
   * To reject the contents that were entered in the bar.
   */
  @Override
  public void reject() {
    String currentRaw = model.getCellAt(view.getSelectCoord()).getRowContent();
    view.setInputString(currentRaw);
  }

  /**
   * To select the specific cell according to its coordinate.
   *
   * @param c the coordinate of the cell.
   */
  @Override
  public void selectCell(Coord c) {
    view.setSelectedCoord(c);
    Cell toDisplay = model.getCellAt(c);
    String cont;
    if (toDisplay == null) {
      cont = "";
    } else {
      cont = toDisplay.getRowContent();
    }
    view.setInputString(cont);
  }

  /**
   * move the selected cell to the upward direction by pressing "up" arrow key.
   */
  @Override
  public void moveSelectCellUp() {
    Coord curCell = view.getSelectCoord();
    this.selectCell(new Coord(curCell.col, curCell.row-1));
    view.repaint();
  }

  /**
   * move the selected cell to the downward direction by pressing "down" arrow key.
   */
  @Override
  public void moveSelectCellDown() {
    Coord curCell = view.getSelectCoord();
    this.selectCell(new Coord(curCell.col, curCell.row+1));
    view.repaint();
  }

  /**
   * move the selected cell to the left direction by pressing "left" arrow key.
   */
  @Override
  public void moveSelectCellLeft() {
    Coord curCell = view.getSelectCoord();
    this.selectCell(new Coord(curCell.col-1, curCell.row));
    view.repaint();
  }

  /**
   * move the selected cell to the right direction by pressing "right" arrow key.
   */
  @Override
  public void moveSelectCellRight() {
    Coord curCell = view.getSelectCoord();
    this.selectCell(new Coord(curCell.col+1, curCell.row));
    view.repaint();
  }

  /**
   * use the Delete key to clear a cell’s contents.
   */
  @Override
  public void deleteCellContent() {
    Coord target = view.getSelectCoord();
    model.removeCell(target);
    view.repaint();
  }

  /**
   * To save the current file in the spreadsheet.
   */
  @Override
  public void saveFile() {
    // We didn't implement this
  }

  /**
   * To load a new file in the spreadsheet.
   *
   * @param fileName name of the loaded file.
   */
  @Override
  public void loadFile(String fileName) {
    // We didn't implement this
  }

  /**
   * run the controller.
   *
   * @throws IOException when io error occur.
   */
  @Override
  public void run() throws IOException {
    view.addFeatures(this);
    view.repaint();
    view.render();
  }
}
