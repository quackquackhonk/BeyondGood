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
    expectedOutput.append("set input text to 3.000000");

    MockView testView = (MockView) view;
    assertEquals(expectedOutput.toString().trim(), testView.log.toString().trim());
  }

}