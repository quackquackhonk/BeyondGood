package edu.cs3500.spreadsheets.view;


import static org.junit.Assert.assertEquals;

import edu.cs3500.spreadsheets.model.IWriteWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import java.io.IOException;
import org.junit.Test;

/**
 * Test class for textual view.
 */
public class SpreadsheetTextualViewTest {

  // Tests a file is saved.
  @Test
  public void testRender() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2");
    builder.createCell(1, 2, "3");
    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 3.000000" + "\n" + "A1 2.000000" + "\n", log.toString());
  }

  // Tests a file is saved with formulas.
  @Test
  public void testRenderFormulas() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2");
    builder.createCell(1, 2, "3");
    builder.createCell(1, 3, "=(SUM 1 2 3)");
    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 3.000000\n" +
        "A1 2.000000\n" +
        "A3 =(SUM 1.000000 2.000000 3.000000)" + "\n", log.toString());
  }

  // Tests a file is saved with formulas that have references in them
  @Test
  public void testRenderFormulaRef() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2");
    builder.createCell(1, 2, "3");
    builder.createCell(1, 3, "=(SUM A1 A2 3)");
    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 3.000000\n" +
        "A1 2.000000\n" +
        "A3 =(SUM A1 A2 3.000000)" + "\n", log.toString());
  }

  // Render formula with colon range.
  @Test
  public void testRenderFormulaRangeRef() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2");
    builder.createCell(1, 2, "3");
    builder.createCell(1, 3, "=(SUM A1:A2)");
    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 3.000000\n" +
        "A1 2.000000\n" +
        "A3 =(SUM A1:A2)" + "\n", log.toString());
  }

  // Read in a file that was previously saved.
  @Test
  public void readInPrevSave() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2.000000");
    builder.createCell(1, 2, "3.000000");
    builder.createCell(1, 3, "=(SUM A1:A2)");
    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 3.000000\n" +
        "A1 2.000000\n" +
        "A3 =(SUM A1:A2)" + "\n", log.toString());
  }

  // Cells with errors are saved properly.
  @Test
  public void saveErrors() throws IOException {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A1");
    builder.createCell(1, 3, "=(SUM A1:A2)");
    builder.createCell(1, 4, "=(PRODUCT 1 2)");
    builder.createCell(1, 5, "=(> A1 A2)");

    IWriteWorkSheetModel model = builder.createWorksheet();

    StringBuilder log = new StringBuilder();
    IView view = new SpreadsheetTextualView(model, log);
    view.render();
    assertEquals("A2 A1\n"
        + "A1 A2\n"
        + "A5 =(> A1 A2)\n"
        + "A4 =(PRODUCT 1.000000 2.000000)\n"
        + "A3 =(SUM A1:A2)\n", log.toString());
  }
}