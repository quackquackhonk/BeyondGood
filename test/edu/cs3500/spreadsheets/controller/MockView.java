package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.SpreadsheetGUIViewEditable;

public class MockView extends SpreadsheetGUIViewEditable implements IView {

  public StringBuilder log = new StringBuilder();

  @Override
  public void render() {
    return;
  }

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void showErrorMessage(String error) {
    log.append("ERROR: " + error + "\n");
  }

  @Override
  public Coord coordFromLoc(int x, int y) {
    Coord ret = super.coordFromLoc(x, y);
    log.append("clicked on (" + x + "," + y + ")" + "\n");
    log.append("selected cell " + ret.toString() + "\n");
    return ret;
  }

  @Override
  public void setInputText(String s) {
    super.setInputText(s);
    log.append("set input text to " + s + "\n");
  }

  @Override
  public void updateView(Coord coord, String cell) {
    super.updateView(coord, cell);
    log.append("updated view by adding " + cell + " at coord " + coord.toString() + "\n");
  }

  @Override
  public void resetInput() {
    super.resetInput();
    log.append("reset input\n");
  }

  @Override
  public void resizeView(int maxCol, int maxRow) {
    super.resizeView(maxCol, maxRow);
    log.append("resized view with new maxCol: " +  maxCol + " and new maxRow: " + maxRow + "\n");
  }

  @Override
  public void setColToAdd(String s) {
    super.setColToAdd(s);
    log.append("set addColField to " + s + "\n");
  }

  @Override
  public void setRowToAdd(String s) {
    super.setRowToAdd(s);
    log.append("set addRowField to " + s + "\n");
  }

}
