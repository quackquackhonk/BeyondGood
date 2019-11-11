package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;
import org.junit.Test;

import static edu.cs3500.spreadsheets.sexp.Parser.parse;
import static org.junit.Assert.assertEquals;

/**
 * test class for PRODUCT.
 */
public class PRODUCTTest {
    SexpVisitParser sexpParser = new SexpVisitParser();

    // Test construction of Bool using visitor pattern
    @Test
    public void testSumConstruct() {
        Sexp parsed = parse("(SUM 1 2)");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("1.000000 2.000000", bool.stringParams());
    }

    // Test construction of Bool using visitor pattern
    @Test
    public void testSumConstructStrings() {
        Sexp parsed = parse("(SUM \"hi\" 2)");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("\"hi\" 2.000000", bool.stringParams());
    }

    // Test construction of Bool using visitor pattern
    @Test
    public void testSumNested() {
        Sexp parsed = parse("(SUM (SUM 1 2) 2)");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("1.000000 2.000000 2.000000", bool.stringParams());
    }

    // Test construction of SUM
    @Test
    public void testSumNestedTwice() {
        Sexp parsed = parse("(SUM (SUM 1 2) (SUM 1 2))");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("1.000000 2.000000 1.000000 2.000000", bool.stringParams());
    }

    // Test construction of SUM
    @Test
    public void testSumNestedNested() {
        Sexp parsed = parse("(SUM (SUM (SUM 1 2) 2) (SUM 1 2))");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("1.000000 2.000000 2.000000 1.000000 2.000000", bool.stringParams());
    }

    // Test construction of SUM
    @Test
    public void testSumNestedRefs() {
        Sexp parsed = parse("(SUM A1 (SUM 1 2))");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("A1 1.000000 2.000000", bool.stringParams());
    }

    // Test construction of SUM
    @Test
    public void testSumNestedRefString() {
        Sexp parsed = parse("(SUM \"hi\" (SUM 1 2))");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("\"hi\" 1.000000 2.000000", bool.stringParams());
    }
}