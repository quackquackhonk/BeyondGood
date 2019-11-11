package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Ops that sums the arguments.
 */
public class SUM extends Ops {


    /**
     * Constructs a SUM cell that stores CellContents.
     *
     * @param params - CC to store.
     */
    public SUM(ArrayList<CellContents> params) {
        super(params);
    }

    ;

    /**
     * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
     * boolean.
     */
    @Override
    public Object evaluate() {
        return null;
    }

    /**
     * Evaluates current cell to its base Value.
     *
     * @param v the visitor used to evaluate cell
     * @return base Value of cell.
     */
    @Override
    public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
        return v.visitSUM(this);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("=(SUM ");

        for (CellContents c : params) {
            out.append(c.getRaw());
            out.append(" ");
        }
        return out.toString().trim() + ")";
    }

    /**
     * Returns this CellContents in ArrayList form.
     *
     * @param v represent a visitor for evaluation
     * @return
     */
    @Override
    public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
        return v.getContentSum(this);
    }

    @Override
    public String getRaw() {
        return this.toString();
    }

}
