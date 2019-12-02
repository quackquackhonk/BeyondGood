package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetAdapterModel;
import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.controller.IController;
import edu.cs3500.spreadsheets.provider.model.Coord;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetEditableGraphicView;
import edu.cs3500.spreadsheets.provider.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.IView;
import java.io.IOException;

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
    super.confirmInput();
  }

  /**
   * To reject the contents that were entered in the bar.
   */
  @Override
  public void reject() {
    super.clearInput();
  }

  /**
   * To select the specific cell according to its coordinate.
   *
   * @param c the coordinate of the cell.
   */
  @Override
  public void selectCell(Coord c) {
    //super.clickOnCellAt();
  }

  /**
   * move the selected cell to the upward direction by pressing "up" arrow key.
   */
  @Override
  public void moveSelectCellUp() {
    super.cellSelectWithKey(0, -1);
  }

  /**
   * move the selected cell to the downward direction by pressing "down" arrow key.
   */
  @Override
  public void moveSelectCellDown() {
    super.cellSelectWithKey(0, 1);
  }

  /**
   * move the selected cell to the left direction by pressing "left" arrow key.
   */
  @Override
  public void moveSelectCellLeft() {
    super.cellSelectWithKey(-1, 0);
  }

  /**
   * move the selected cell to the right direction by pressing "right" arrow key.
   */
  @Override
  public void moveSelectCellRight() {
    super.cellSelectWithKey(1, 0);
  }

  /**
   * use the Delete key to clear a cell’s contents.
   */
  @Override
  public void deleteCellContent() {
    super.deleteCellContents();
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
    view.repaint();
    view.render();
  }
}
