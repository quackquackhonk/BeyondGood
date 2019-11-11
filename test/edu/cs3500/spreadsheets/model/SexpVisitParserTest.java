package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;
import org.junit.Test;

import java.util.ArrayList;

import static edu.cs3500.spreadsheets.sexp.Parser.parse;
import static org.junit.Assert.assertEquals;

/**
 * Tests the creation of proper CellContents classes using SexpVisitParser visitor.
 * Also tests:
 * CellContent
 * - evaluate()
 * - stringParams()
 */
public class SexpVisitParserTest {
    SexpVisitParser sexpParser = new SexpVisitParser();

    // Test construction of Bool using visitor pattern
    @Test
    public void testVisitBooleanTest() {
        Sexp parsed = parse("true");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals(true, bool.evaluate());
    }

    // Creation of Dbl
    @Test
    public void visitNumberTest() {
        Sexp parsed = parse("4");
        CellContents dbl = parsed.accept(sexpParser);
        assertEquals(4.0, dbl.evaluate());
    }

    // Creation of RefCell
    @Test
    public void visitSymbolTest() {
        Sexp parsed = parse("A1");
        CellContents ref = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 1);
        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        assertEquals(coordList, ref.evaluate());
    }

    // Creation of RefCell
    @Test
    public void visitSymbolTestVisitor() {
        ReferenceCell ref = sexpParser.visitSymbol("A1");
        Coord coord = new Coord(1, 1);
        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        assertEquals(coordList, ref.evaluate());
    }

    // Imporperly formatted coordinate.
    // Can't be a string, must be cell reference of method name, is neither, should throw error.
    @Test(expected = IllegalArgumentException.class)
    public void checkCoordTest() {
        Sexp parsed = parse("A4A");
        CellContents refCell = parsed.accept(sexpParser);
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords
    @Test
    public void getListCoordsTest() {
        Sexp parsed = parse("A2:C2");
        CellContents dbl = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 2);
        Coord coord1 = new Coord(2, 2);
        Coord coord2 = new Coord(3, 2);

        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        coordList.add(coord1);
        coordList.add(coord2);
        assertEquals(coordList, dbl.evaluate());
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords
    @Test
    public void getListCoordsTestArea() {
        Sexp parsed = parse("A1:C2");
        CellContents dbl = parsed.accept(sexpParser);
        Coord coord1 = new Coord(1, 1);
        Coord coord2 = new Coord(1, 2);
        Coord coord3 = new Coord(2, 1);
        Coord coord4 = new Coord(2, 2);
        Coord coord5 = new Coord(3, 1);
        Coord coord6 = new Coord(3, 2);

        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord1);
        coordList.add(coord2);
        coordList.add(coord3);
        coordList.add(coord4);
        coordList.add(coord5);
        coordList.add(coord6);
        assertEquals(coordList, dbl.evaluate());
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords.
    // Reversing order of given Coords shouldn't matter.
    @Test
    public void getListCoordsReverse() {
        Sexp parsed = parse("C2:A2");
        CellContents dbl = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 2);
        Coord coord1 = new Coord(2, 2);
        Coord coord2 = new Coord(3, 2);

        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        coordList.add(coord1);
        coordList.add(coord2);
        assertEquals(coordList, dbl.evaluate());
    }

    // Second Coord in range is invalid
    @Test(expected = IllegalArgumentException.class)
    public void getListCoordsTestExceptionSecond() {
        Sexp parsed = parse("A1:C1C");
        CellContents dbl = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 1);
        Coord coord1 = new Coord(2, 1);
        Coord coord2 = new Coord(3, 1);

        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        coordList.add(coord1);
        coordList.add(coord2);
        assertEquals(coordList, dbl.evaluate());
    }

    // First Coord in range is invalid
    @Test(expected = IllegalArgumentException.class)
    public void getListCoordsTestExceptionFirst() {
        Sexp parsed = parse("A1A:C1");
        CellContents dbl = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 1);
        Coord coord1 = new Coord(2, 1);
        Coord coord2 = new Coord(3, 1);

        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        coordList.add(coord1);
        coordList.add(coord2);
        assertEquals(coordList, dbl.evaluate());
    }

    @Test
    public void visitStringTest() {
        Sexp parsed = parse("\"hi\"");
        CellContents dbl = parsed.accept(sexpParser);
        assertEquals("\"hi\"", dbl.evaluate());
    }


    // stringParams() testing ------------------------------------------------------
    @Test
    public void visitStringTestRaw() {
        Sexp parsed = parse("\"hi\"");
        CellContents dbl = parsed.accept(sexpParser);
        assertEquals("\"hi\"", dbl.stringParams());
    }

    // Test construction of Bool using visitor pattern
    @Test
    public void visitBooleanTestRaw() {
        Sexp parsed = parse("true");
        CellContents bool = parsed.accept(sexpParser);
        assertEquals("true", bool.stringParams());
    }

    // Creation of Dbl
    @Test
    public void visitNumberTestRaw() {
        Sexp parsed = parse("4");
        CellContents dbl = parsed.accept(sexpParser);
        assertEquals("4.000000", dbl.stringParams());
    }

    // Creation of RefCell
    @Test
    public void visitSymbolTestRaw() {
        Sexp parsed = parse("A1");
        CellContents ref = parsed.accept(sexpParser);
        Coord coord = new Coord(1, 1);
        ArrayList<Coord> coordList = new ArrayList<>();
        coordList.add(coord);
        assertEquals("A1", ref.stringParams());
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords.
    // Reversing order of given Coords shouldn't matter.
    @Test
    public void getListCoordsReverseRaw() {
        Sexp parsed = parse("C2:A2");
        CellContents dbl = parsed.accept(sexpParser);

        assertEquals("A2 B2 C2", dbl.stringParams());
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords
    @Test
    public void getListCoordsTestAreaRaw() {
        Sexp parsed = parse("A1:C2");
        CellContents dbl = parsed.accept(sexpParser);

        assertEquals("A1 A2 B1 B2 C1 C2", dbl.stringParams());
    }

    // Create RefCell w/ArrayList of all Coords between two given Coords
    @Test
    public void getListCoordsTestBigColumn() {
        Sexp parsed = parse("AA1:AC2");
        CellContents dbl = parsed.accept(sexpParser);

        assertEquals("AA1 AA2 AB1 AB2 AC1 AC2", dbl.stringParams());
    }
}