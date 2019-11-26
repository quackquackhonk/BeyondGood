package edu.cs3500.spreadsheets.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * A class to test the methods and functionality of the model.
 */
public class WorkSheetModelTest {
  SexpVisitParser parser = new SexpVisitParser();

  // This works
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListHasCycle() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=A1");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  //
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListHasCycleSelf() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A1");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // Can't have lone range cell
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListHasCycleSelfRange() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A1:A3");
    builder.createCell(1, 2, "4");
    builder.createCell(1, 3, "4");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals(model.evaluateCell("A1"), "#Reference");

  }

  // Can't have lone range cell
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListHasCycleSelfRangeSum() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A1:A3)");
    builder.createCell(1, 2, "4");
    builder.createCell(1, 3, "4");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // Can't have lone range cell
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListHasCycleSelfRangeSumBad() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2:A4)");
    builder.createCell(1, 2, "4");
    builder.createCell(1, 3, "4");
    builder.createCell(1, 4, "=(SUM A1 1)");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // This works
  @Test
  public void checkBuildAdjListNoCycle() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2:A3)");
    builder.createCell(1, 2, "1");
    builder.createCell(1, 3, "2");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  // One removed cycle within Ops, works
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListInOps() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=(PRODUCT A3 2)");
    builder.createCell(1, 3, "=A1");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  // One removed cycle within Ops, works
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjHasCycle() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A1");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  // Cycle with children connected to cycle. All cells connected to cycle are caught.
  // *************** WORKS ********************************************************
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjHasCycleWithBacktrack() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=(SUM A4 F5)");
    builder.createCell(1, 4, "=A1");
    builder.createCell(1, 5, "=A2");
    builder.createCell(6, 5, "=D5");
    builder.createCell(4, 5, "5");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjHasCycleNestedChildren() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=(SUM A4 F5)");
    builder.createCell(1, 4, "=A1");
    builder.createCell(1, 5, "=A2");
    builder.createCell(6, 5, "=D5");
    builder.createCell(4, 5, "5");
    builder.createCell(7, 5, "=A5");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // No cycle, with references.
  // **************** WORKS ********************************************************
  @Test
  public void checkBuildAdjHasNoCycle() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=A2");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("2.000000", model.evaluateCell("A1"));
  }


  // Test Sum -------------------------------------------------------
  @Test
  public void testSumEval() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM 1 2)");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testSumEval2() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM 1)");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("1.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testSumEvalNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (SUM 1 2) 3)");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("6.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testSumEvalNestNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (SUM 1 2) (SUM 1 (SUM 1 2)))");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("7.000000", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSumEvalError() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (SUM true 2) (SUM 1 (SUM 1 2)))");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  @Test
  public void testSumEvalBigCoord() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A10000000)");
    builder.createCell(1, 10000000, "2");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("2.000000", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSumEvalStr() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (SUM \"hi\" 2) (SUM 1 (SUM 1 2)))");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  @Test
  public void testSumRefCell() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2 1)");
    builder.createCell(1, 2, "1");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("2.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testSumRefCellNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2 1)");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=A4");
    builder.createCell(1, 4, "=A5");
    builder.createCell(1, 5, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("2.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testSumRefCellLotsRef() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2 1)");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=A4");
    builder.createCell(1, 4, "=A5");
    builder.createCell(1, 5, "=A6");
    builder.createCell(1, 6, "=A7");
    builder.createCell(1, 7, "=A8");
    builder.createCell(1, 8, "=A9");
    builder.createCell(1, 9, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("2.000000", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSumRefCellRange() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2)");
    builder.createCell(1, 2, "=A3:A5");
    builder.createCell(1, 3, "1");
    builder.createCell(1, 4, "1");
    builder.createCell(1, 5, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // This works
  @Test(expected = IllegalArgumentException.class)
  public void checkBuildAdjListNoCyclef() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A4)");
    builder.createCell(1, 4, "=A2:A3");
    builder.createCell(1, 2, "1");
    builder.createCell(1, 3, "2");
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  // Test Product ----------------------------------------------------------
  @Test
  public void testProdRefCellLotsRef() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT A2 1)");
    builder.createCell(1, 2, "=A3");
    builder.createCell(1, 3, "=A4");
    builder.createCell(1, 4, "=A5");
    builder.createCell(1, 5, "=A6");
    builder.createCell(1, 6, "=A7");
    builder.createCell(1, 7, "=A8");
    builder.createCell(1, 8, "=A9");
    builder.createCell(1, 9, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("1.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProd() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT A2 A3 A4)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("8.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT (PRODUCT A2) A3 A4)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("8.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNestNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT (PRODUCT (PRODUCT 2 (PRODUCT 2 2)) A3) A4)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("32.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNestSum() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT (SUM 1 1))");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("2.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNestSumNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(PRODUCT (SUM 1 1) 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("4.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNestProdNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (PRODUCT 1 1) 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("3.000000", model.evaluateCell("A1"));
  }

  @Test
  public void testProdNestSumRange() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM (PRODUCT A2:A4 1) 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("10.000000", model.evaluateCell("A1"));
  }

  // GREATERTHAN ------------------------------
  @Test
  public void testGreater() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(> (PRODUCT A2:A4 1) 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("true", model.evaluateCell("A1"));
  }

  @Test
  public void testGreaterSimple() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(> 1 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("false", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreatNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(> A2:A3 (SUM 2 3 (PRODUCT 10 2)))");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("false", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreaterBool() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(> 1 (< 3 (PRODUCT 10 2)))");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreaterBoolInBool() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(> 1 true)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // LESSTHAN ------------------------------
  @Test
  public void testLess() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(< (PRODUCT A2:A4 1) 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("false", model.evaluateCell("A1"));
  }

  @Test
  public void testLessSimple() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(< 1 2)");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("true", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessNest() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(< A2:A3 (SUM 2 3 (PRODUCT 10 2)))");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
    assertEquals("false", model.evaluateCell("A1"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLessBool() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(< 1 (< 3 (PRODUCT 10 2)))");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.evaluateCell("A1");
  }

  // ====================================================================================

  @Test
  public void testGetCell() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.setCell(1, 1, "2");
    String evaled = model.evaluateCell("A1");
    assertEquals("2.000000", evaled);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGetCellBad() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "1");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.setCell(1, 1, "A1");
  }

  @Test
  public void testGetCellBadRange() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2:A3)");

    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    model.setCell(1, 2, "=(SUM 1 2)");
    assertEquals("3.000000", model.evaluateCell("A2"));
  }

  @Test
  public void testGetCellNull() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "=(SUM A2)");

    IWriteWorkSheetModel model = builder.createWorksheet();
    assertEquals("0.000000", model.evaluateCell("A1"));
  }

  // Doesn't return an exception.
  @Test
  public void testEmptySheet() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setupModel();
    assertEquals(0, model.activeCells().size());
  }

  @Test
  public void addedCellsExist() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    builder.createCell(1, 1, "2");
    builder.createCell(1, 2, "2");
    builder.createCell(1, 3, "2");
    builder.createCell(1, 4, "2");

    IWriteWorkSheetModel model = builder.createWorksheet();
    assertEquals(model.activeCells().size(), 4);
  }

  @Test
  public void addedBlankCells() {
    WorkSheetModel.SheetBuilder builder = new WorkSheetModel.SheetBuilder();
    IWriteWorkSheetModel model = builder.createWorksheet();
    model.setCell(1, 1, null);
    assertEquals(model.activeCells().size(), 1);
  }
}