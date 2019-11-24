package edu.cs3500.spreadsheets.controller;

import org.junit.Test;

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
    view = new SpreadsheetGUIViewEditable();
    controller = new SpreadsheetMVCController(model);
    controller.setView(view);
  }

  @Test
  public void testSetView() {
    // TODO: once View is finished, finish this test
  }

  //public void test

}