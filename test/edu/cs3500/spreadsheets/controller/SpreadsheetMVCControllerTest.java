package edu.cs3500.spreadsheets.controller;

import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.IView;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for the Controller.
 */
public class SpreadsheetMVCControllerTest {

  WorksheetReader.WorksheetBuilder<IWriteWorkSheetModel> builder
      = new WorkSheetModel.SheetBuilder();
  IWriteWorkSheetModel model;
  IView view;
  SpreadsheetController controller;

  private void init(String filename) {
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

  // Input from view to Controller verified as working by previous test testConfirmInput()
  // Test that Controller sends info to the view properly.
  @Test
  public void testControllerPassesToModel() {
    StringBuilder expectedOutput = new StringBuilder();
    view = new MockView();
    model = new MockWorksheetModel(expectedOutput);
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);

    controller.clickOnCellAt(new Point(0, 0));
    controller.clickOnCellAt(new Point(80, 0));

    assertEquals("A1 was passedB1 was passed", expectedOutput.toString().trim());
  }
}