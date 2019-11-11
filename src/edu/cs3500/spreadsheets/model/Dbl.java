package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents a cell with a Double as a value.
 */
public class Dbl implements Value<Double> {
    private final double val;

    /**
     * Constructs a Dbl cell that stores a Double.
     *
     * @param val - double to store
     */
    public Dbl(double val) {
        this.val = val;
    }

    /**
     * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
     * boolean.
     */
    @Override
    public Double evaluate() {
        return this.val;
    }

    @Override
    public String toString() {
        return String.format("%f", this.val);
    }

    @Override
    public String getRaw() {
        return this.toString();
    }

    /**
     * Returns unevaluated String version of CellContents, for dependency checking.
     */
    @Override
    public String stringParams() {
        return this.toString();
    }

    /**
     * Evaluates current cell to its base Value.
     *
     * @param v the visitor used to evaluate cell
     * @return base Value of cell.
     */
    @Override
    public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
        return v.visitDbl(this);
    }

    /**
     * Returns this CellContents in ArrayList form.
     *
     * @param v represent a visitor for evaluation
     * @return
     */
    @Override
    public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
        return v.getContentDbl(this);
    }

    /**
     * returns Dbl if its a Dbl.
     *
     * @return
     */
    @Override
    public Dbl getDbl() {
        return this;
    }
}
