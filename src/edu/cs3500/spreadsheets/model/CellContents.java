package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents possible contents of the cell.
 */
public interface CellContents<K> {

    /**
     * Returns value of CellContents. Value refers to the data type of the class.
     * - Values return their corresponding type (boolean, String, double.
     * - Blanks throw an error
     * - Ops (Functions) can't evaluate by themselves, throw an error.
     * - ReferenceCells return their list of Coords
     * <p>
     * boolean.
     */
    K evaluate();

    /**
     * Returns the string implementation of cell contents.
     */
    String getRaw();

    /**
     * Returns unevaluated String version of CellContents, for dependency checking.
     */
    String stringParams();

    /**
     * Evaluates current cell to its base Value.
     *
     * @param v the visitor used to evaluate cell
     * @return base Value of cell.
     */
    Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v);

    /**
     * Returns the contents of the given cell in an ArrayList.
     *
     * @param v represent a visitor for evaluation
     * @return
     */
    ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v);
}
