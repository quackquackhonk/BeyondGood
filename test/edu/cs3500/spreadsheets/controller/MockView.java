package edu.cs3500.spreadsheets.controller;

import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.SpreadsheetGUIViewEditable;

public class MockView extends SpreadsheetGUIViewEditable implements IView {

  public StringBuilder log = new StringBuilder();

  @Override
  public void render() throws IOException {
    return;
  }

  @Override
  public void makeVisible() {
    return;
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    throw new UnsupportedOperationException("Not necessary for testing");
  }

  @Override
  public void showErrorMessage(String error) {
    return;
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
    log.append("reset input" + "\n");
  }

  @Override
  public void resizeView(int maxCol, int maxRow) {
    super.resizeView(maxCol, maxRow);
    log.append("resized view with new maxCol: " +  maxCol + " and new maxRow: " + maxRow + "\n");
  }

}