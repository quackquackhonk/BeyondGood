package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * Represents a Read-only Worksheet Model. Mainly for use in the view.
 */
public interface IReadWorkSheetModel<CellContents> {

    /**
     * Returns the set of Coords initiated or depended on by other Coords.
     *
     * @return active Coords
     */
    HashSet<Coord> activeCells();

    /**
     * Prints result of evaluated cell at given coordinate.
     *
     * @param coord represents a String coordinate
     */
    String evaluateCell(String coord);

    /**
     * Returns a String of the evaluated Cell at the given column and row.
     *
     */
    String evaluateCell(int col, int row);

    /**
     * Returns the raw text of the cell at given Coordinate.
     *
     * @param coord is a coordinate
     */
    String getCellText(Coord coord);

    /**
     * Returns the raw text
     */
    String getCellText(int col, int row);

    /**
     * Return cell at a provided location.
     *
     * @param location is the coordinates of the cell
     * @return the {@Cell} at the provided location
     * @throws IllegalStateException    if the model was not checked
     * @throws IllegalArgumentException if the location of the cell is invalid
     */
    CellContents getCell(Coord location) throws IllegalStateException;

    /**
     * Does the model have any cells in error.
     */
    Boolean hasErrors();

    /**
     * Returns the max width of sheet.
     *
     * @return the width of the row
     */
    int getMaxRowWidth();

    /**
     * Returns the min width of sheet.
     *
     * @return the width of the row
     */
    int getMinRowWidth();

    /**
     * Returns the min height of sheet.
     *
     * @return the height of the col
     */
    int getMinColHeight();

    /**
     * Returns the max height of the sheet.
     *
     * @return the height of the col
     */
    int getMaxColHeight();

}