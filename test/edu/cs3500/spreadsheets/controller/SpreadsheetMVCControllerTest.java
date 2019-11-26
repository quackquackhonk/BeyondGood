package edu.cs3500.spreadsheets.controller;

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

}