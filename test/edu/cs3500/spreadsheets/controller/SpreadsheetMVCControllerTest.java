package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.SpreadsheetGUIViewEditable;

import static org.junit.Assert.*;

/**
 * Tests for the Controller.
 */
public class SpreadsheetMVCControllerTest {

  WorksheetReader.WorksheetBuilder<IWriteWorkSheetModel> builder
          = new WorkSheetModel.SheetBuilder();
  IWriteWorkSheetModel model;
  IView view;
  SpreadsheetController controller;

  private void init(String filename){
    File file = new File(filename);
    Readable fileReader;
    try {
      fileReader = new FileReader(file);
      model = WorksheetReader.read(builder, fileReader);
    } catch (FileNotFoundException e) {
      try {
        file.createNewFile();
        fileReader = new FileReader(file);
        model = WorksheetReader.read(builder, fileReader);
      } catch (IOException er) {
        er.printStackTrace();
      }
    }

    view = new MockView();
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);
  }

  // Correct cell selected on mouse event.
  @Test
  public void testControllerClick() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();

    controller.clickOnCellAt(new Point(0, 0));
    expectedOutput.append("clicked on (0,0)\n");
    expectedOutput.append("selected cell A1\n");
    expectedOutput.append("set input text to 3.000000\n");

    // click on empty cell
    controller.clickOnCellAt(new Point(100, 100));
    expectedOutput.append("clicked on (100,100)\n" +
            "selected cell B4\n" +
            "set input text to \n");

    controller.clickOnCellAt(new Point(440, 20));
    expectedOutput.append("clicked on (440,20)\n");
    expectedOutput.append("selected cell F1\n");
    expectedOutput.append("set input text to =(SUM A1:D1)");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
  }

  @Test
  public void testControllerClearText() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();

    controller.clickOnCellAt(new Point(0, 0));
    expectedOutput.append("clicked on (0,0)\n");
    expectedOutput.append("selected cell A1\n");
    expectedOutput.append("set input text to 3.000000\n");

    view.setInputText("12312316231");
    expectedOutput.append("set input text to 12312316231\n");
    controller.clearInput();
    expectedOutput.append("reset input\n");
    expectedOutput.append("set input text to 3.000000\n");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
  }

  // Test selection can change
  @Test
  public void testMultipleControllerClick() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();

    controller.clickOnCellAt(new Point(0, 0));
    expectedOutput.append("clicked on (0,0)\n");
    expectedOutput.append("selected cell A1\n");
    expectedOutput.append("set input text to 3.000000\n");

    controller.clickOnCellAt(new Point(81, 0));
    expectedOutput.append("clicked on (81,0)\n");
    expectedOutput.append("selected cell B1\n");
    expectedOutput.append("set input text to 4.000000");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
  }

  // Test input text passed to controller correctly.
  @Test
  public void testConfirmInput() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();

    view.setInputText("123");

    controller.confirmInput();
    expectedOutput.append("set input text to 123");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
  }

  // Test input text passed to controller correctly and cell is made in model.
  @Test
  public void testConfirmInputNewCell() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();

    controller.clickOnCellAt(new Point(400, 0));
    view.setInputText("123");

    controller.confirmInput();
    //model.getCellText(new Coord(5, 1));

    expectedOutput.append("clicked on (400,0)\n"
        + "selected cell F1\n"
        + "set input text to =(SUM A1:D1)\n"
        + "set input text to 123\n"
        // Controller creates new cell and passes this to view.
        // This tests information can be passed from the controller to the view correctly.
        + "updated view by adding 123.000000 at coord F1\n"
        + "updated view by adding false at coord J1");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
    assertEquals(model.getCellText(new Coord(6, 1)), "123.000000");
  }

  // Controller to model
  @Test
  public void testControllerSetsViewCell() {
    StringBuilder expectedOutput = new StringBuilder();
    view = new MockView();
    model = new MockWorksheetModel();
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);
    model.setCellAllowErrors(new Coord(0,0), "123");
  }

  @Test
  public void testControllerAddColumn() {
    this.init("good1.txt");
    StringBuilder expectedOutput = new StringBuilder();
    model = new MockWorksheetModel();
    view = new MockView();
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);

    controller.addColumn();
    expectedOutput.append("ERROR: Please enter a valid column name (alphabetical characters only)" +
                    "\n");

    view.setColToAdd("2192132");
    expectedOutput.append("set addColField to 2192132\n");
    controller.addColumn();
    expectedOutput.append("ERROR: Please enter a valid column name (alphabetical characters only)" +
                    "\n");

    view.setColToAdd("Z");
    expectedOutput.append("set addColField to Z\n");
    controller.addColumn();
    expectedOutput.append("resized view with new maxCol: 26 and new maxRow: 0\n");
    expectedOutput.append("set addColField to \n");
    view.setColToAdd("A");
    expectedOutput.append("set addColField to A\n");
    controller.addColumn();
    // this means that it was not resized.
    expectedOutput.append("resized view with new maxCol: 1 and new maxRow: 0\n");
    expectedOutput.append("set addColField to \n");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString(), testView.log.toString());
  }

  @Test
  public void testControllerAddRow() {
    StringBuilder expectedOutput = new StringBuilder();
    model = new MockWorksheetModel();
    view = new MockView();
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);

    controller.addRow();
    expectedOutput.append("ERROR: Please enter a valid row name (numerical characters only)" +
            "\n");

    view.setRowToAdd("ABC");
    expectedOutput.append("set addRowField to ABC\n");
    controller.addRow();
    expectedOutput.append("ERROR: Please enter a valid row name (numerical characters only)" +
            "\n");

    view.setRowToAdd("26");
    expectedOutput.append("set addRowField to 26\n");
    controller.addRow();
    expectedOutput.append("resized view with new maxCol: 0 and new maxRow: 26\n");
    expectedOutput.append("set addRowField to \n");
    view.setRowToAdd("1");
    expectedOutput.append("set addRowField to 1\n");
    controller.addRow();
    // this means that it was not resized.
    expectedOutput.append("resized view with new maxCol: 0 and new maxRow: 1\n");
    expectedOutput.append("set addRowField to \n");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString(), testView.log.toString());
  }

}