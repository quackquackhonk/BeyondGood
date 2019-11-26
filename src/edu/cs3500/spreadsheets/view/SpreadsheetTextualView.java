package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ControllerFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Textual view for ISpreadSheetModels.
 */
public class SpreadsheetTextualView implements IView {
  IReadWorkSheetModel model;
  Appendable out;

  /**
   * Constructs a view class.
   *
   * @param model model to render
   * @param out   render to this.
   */
  public SpreadsheetTextualView(IReadWorkSheetModel model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public void render() {
    try {
      HashSet<Coord> allCells = model.activeCells();
      for (Coord c : allCells) {
        String cellText = model.getCellText(c);
        if (!cellText.equals("")) {
          String toAdd = c.toString() + " " + cellText;
          out.append(toAdd);
          out.append("\n");
        }
      }
    } catch (IOException e) {
      System.out.println("Problem writing to output file.");
      e.printStackTrace();
    }
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("Textual view does not need to be made visible");
  }

  @Override
  public void showErrorMessage(String error) {
    throw new UnsupportedOperationException("Textual view processes all cells and saves errors");
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    throw new UnsupportedOperationException("Cannot add key listeners to textual view");
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    throw new UnsupportedOperationException("Cannot add mouse listeners to textual view");
  }

  @Override
  public void addActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("Cannot add action listeners to textual view");
  }

  @Override
  public String getInputText() {
    throw new UnsupportedOperationException("Textual view does not set input text");
  }

  @Override
  public Coord coordFromLoc(int x, int y) {
    throw new UnsupportedOperationException("Textual view cannot convert a pixel coord to a Coord");
  }

  @Override
  public Coord getSelectedCell() {
    throw new UnsupportedOperationException("Textual view does not select cells");
  }

  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {
    throw new UnsupportedOperationException("Textual view does not need setup");
  }

  @Override
  public void updateView(Coord coord, String cell) {
    throw new UnsupportedOperationException("Textual view never updates");
  }

  @Override
  public void resetInput() {
    throw new UnsupportedOperationException("Textual view has no input to reset");
  }

  @Override
  public String getColToAdd() {
    throw new UnsupportedOperationException("Read-only views cannot add columns");
  }

  @Override
  public void setColToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add columns");
  }

  @Override
  public String getRowToAdd() {
    throw new UnsupportedOperationException("Read-only views cannot add rows");
  }

  @Override
  public void setRowToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add rows");
  }

  @Override
  public void addFeatures(ControllerFeatures f) {
    throw new UnsupportedOperationException("Textual view has no features");
  }

  @Override
  public void cellSelectWithKey(Coord coord) {
    throw new UnsupportedOperationException("Textual view has no selected cell");
  }

  @Override
  public void resetFocus() {
    throw new UnsupportedOperationException("Textual view does not have focus");
  }

  @Override
  public void resizeView(int maxCol, int maxRow) {
    throw new UnsupportedOperationException("Textual view cannot be resized");
  }

  @Override
  public void setInputText(String s) {
    throw new UnsupportedOperationException("Textual view has no input");
  }
}
