package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.IWorkSheetModel;
import edu.cs3500.spreadsheets.model.WorkSheetModel;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Test class for textual view.
 */
public class SpreadsheetTextualViewTest {
    String nL = System.getProperty("line.separator");

    // Tests a file is saved.
    @Test
    public void testRender() throws IOException {
        WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
        builder.createCell(1,1, "2");
        builder.createCell(1,2,"3");
        IWorkSheetModel model = builder.createWorksheet();

        StringBuilder log = new StringBuilder();
        IView view = new SpreadsheetTextualView(model, log);
        view.render();
        assertEquals( "A2 3.000000" + "\n" + "A1 2.000000" + "\n", log.toString());
    }

    // Tests a file is saved.
    @Test
    public void testRenderFormulas() throws IOException {
        WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
        builder.createCell(1,1, "2");
        builder.createCell(1,2,"3");
        builder.createCell(1,3,"=(SUM 1 2 3)");
        IWorkSheetModel model = builder.createWorksheet();

        StringBuilder log = new StringBuilder();
        IView view = new SpreadsheetTextualView(model, log);
        view.render();
        assertEquals( "A2 3.000000\n" +
                "A1 2.000000\n" +
                "A3 =(SUM 1.000000 2.000000 3.000000)" + "\n", log.toString());
    }

    // Tests a file is saved
    @Test
    public void testRenderFormulaRef() throws IOException {
        WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
        builder.createCell(1,1, "2");
        builder.createCell(1,2,"3");
        builder.createCell(1,3,"=(SUM A1 A2 3)");
        IWorkSheetModel model = builder.createWorksheet();

        StringBuilder log = new StringBuilder();
        IView view = new SpreadsheetTextualView(model, log);
        view.render();
        assertEquals( "A2 3.000000\n" +
                "A1 2.000000\n" +
                "A3 =(SUM A1 A2 3.000000)" + "\n", log.toString());
    }

    // Render formula with colon range.
    @Test
    public void testRenderFormulaRangeRef() throws IOException {
        WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
        builder.createCell(1,1, "2");
        builder.createCell(1,2,"3");
        builder.createCell(1,3,"=(SUM A1:A2)");
        IWorkSheetModel model = builder.createWorksheet();

        StringBuilder log = new StringBuilder();
        IView view = new SpreadsheetTextualView(model, log);
        view.render();
        assertEquals( "A2 3.000000\n" +
                "A1 2.000000\n" +
                "A3 =(SUM A1:A2)" + "\n", log.toString());
    }

    // Read in a file that was previously saved.
    @Test
    public void readInPrevSave() throws IOException {
        WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
        builder.createCell(1,1, "2.000000");
        builder.createCell(1,2,"3.000000");
        builder.createCell(1,3,"=(SUM A1:A2)");
        IWorkSheetModel model = builder.createWorksheet();

        StringBuilder log = new StringBuilder();
        IView view = new SpreadsheetTextualView(model, log);
        view.render();
        assertEquals( "A2 3.000000\n" +
                "A1 2.000000\n" +
                "A3 =(SUM A1:A2)" + "\n", log.toString());
    }
}