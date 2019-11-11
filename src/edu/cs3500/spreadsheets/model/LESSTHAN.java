package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;


/**
 * Represents a cell with a Less Than function as a value.
 */
public class LESSTHAN extends Ops {

    /**
     * Constructs a LESSTHAN cell that stores a ArrayList<CellContents>.
     *
     * @param params - ArrayList<CellContents> to store
     */
    public LESSTHAN(ArrayList<CellContents> params) {
        super(params);
        if (params.size() != 2) {
            throw new IllegalArgumentException();
        }
    }

    ;

    /**
     * Returns value of CellContents. Value refers to the data type of the class, ex: Bool returns a
     * boolean.
     */
   // @Override
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
        return v.visitLESSTHAN(this);
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("=(< ");

        for (CellContents c : params) {
            out.append(c.toString());
            out.append(" ");
        }
        return out.toString().trim() + ")";
    }

    @Override
    public String getRaw() {
        return this.toString();
    }

    /**
     * Returns this CellContents in ArrayList form.
     *
     * @param v represent a visitor for evaluation
     * @return
     */
    @Override
    public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
        return v.getContentLess(this);
    }
}
