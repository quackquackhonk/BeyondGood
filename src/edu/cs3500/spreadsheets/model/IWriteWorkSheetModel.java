package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * To represent a model for a worksheet. Maintains state and enforces class invariants. Includes
 * methods to set, move, and evaluate cells.  All cells are represented as CellContents. Cycles and
 * invalid cells are recorded.
 */
public interface IWriteWorkSheetModel<CellContents> extends IReadWorkSheetModel<CellContents> {

  /**
   * Creates and sets cell at given Coord, regardless of the errors it causes/has in the
   * IWriteWorkSheetModel. Useful for Controllers that need to create cells from user input that may
   * cause errors.  Updates adjacency lists for all affected cells.
   *
   * @throws IllegalArgumentException if Coord is null
   */
  HashSet<Coord> setCellAllowErrors(Coord coord, String cell) throws IllegalArgumentException;

  /**
   * Prints (System.out.println) result of evaluated cell at given coordinate.
   *
   * @param coord is string coordinate
   */
  void evaluateIndCell(String coord);

  /**
   * Checks the validity of the model. A valid model has no cycles and all cells can evaluate.
   */
  void setupModel();

  /**
   * Build a cell at a given location provided with col and row numbers. Used mostly by builder and
   * DOESN'T CREATE/UPDATE EXISTING ADJACENCY LISTS.
   *
   * @param col      represents the col number
   * @param row      represents the row number
   * @param contents the content of the cell
   * @throws IllegalArgumentException if the location of the cell is invalid
   */
  void buildCell(int col, int row, CellContents contents) throws IllegalArgumentException;

  /**
   * Sets a cell given raw String containing a coordinate and raw cell contents. Updates adjacency
   * lists of affected cells, and throws IllegalArgumentException if new cell causes an
   * cycles/errors.
   *
   * @throws IllegalArgumentException if new cell causes cycles, cannot evaluate, or causes other
   *                                  cells not to evaluate.
   */
  void setCell(int col, int row, String s) throws IllegalArgumentException;
}
