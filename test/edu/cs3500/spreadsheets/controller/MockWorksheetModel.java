package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.WorkSheetModel;

import java.util.HashSet;

/**
 * Mock model to confirm inputs passed to it by the controller are correct.
 */
public class MockWorksheetModel extends WorkSheetModel {

  public StringBuilder log;

  // Constructs the mock model.
  MockWorksheetModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public HashSet<Coord> setCellAllowErrors(Coord coord, String cell) {
    log.append(coord).append(" ").append(cell).append("\n");
    return null;
  }

  @Override
  public void setCell(int col, int row, String s) {
    log.append(col + " " + row + " " + s).append("\n");
  }

  @Override
  public HashSet<Coord> activeCells() {
    return super.activeCells();
  }

  @Override
  public String evaluateCell(String coord) {
    return super.evaluateCell(coord);
  }

  @Override
  public String evaluateCell(int col, int row) {
    return super.evaluateCell(col, row);
  }

  @Override
  public String evaluateCellCheck(String coord) {
    return super.evaluateCellCheck(coord);
  }

  @Override
  public String getCellText(Coord coord) {
    log.append(coord + " was passed\n");
    return coord + " was passed";
  }

  @Override
  public String getCellText(int col, int row) {
    return super.getCellText(col, row);
  }

  /**
   * Does the model have any cells in error. Includes cycles and invalid formulas.
   */
  @Override
  public Boolean hasErrors() {
    return super.hasErrors();
  }

  @Override
  public int getMaxRow() {
    return super.getMaxRow();
  }

  @Override
  public int getMinRow() {
    return super.getMinRow();
  }

  @Override
  public int getMinCol() {
    return super.getMinCol();
  }

  @Override
  public int getMaxCol() {
    return super.getMaxCol();
  }
}
