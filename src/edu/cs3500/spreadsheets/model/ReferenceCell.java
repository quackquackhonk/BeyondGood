package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * A cell that REFERS or POINTS to another cell.
 * Stores a coordinate?
 */
public class ReferenceCell implements CellContents<ArrayList<Coord>> {
    private final ArrayList<Coord> cells;

    /**
     * Constructs a ReferenceCell cell that stores CC.
     *
     * @param cells - params
     */
    public ReferenceCell(ArrayList<Coord> cells) {
        this.cells = cells;
    }

    /**
     * Returns evaluate() of reference CellContents.
     */
    // @Override
    public ArrayList<Coord> evaluate() {
        return this.cells;
    }

    @Override
    public String getRaw() {
        System.out.println(this.cells.size());
        if (this.cells.size() == 1) {
            return this.cells.get(0).toString();
        } else {
            return this.cells.get(0).toString() +
                    ":" +
                    this.cells.get(this.cells.size() - 1).toString();
        }
    }

    @Override
    public String stringParams() {
        StringBuilder outputBuilder = new StringBuilder();
        for (Coord c : this.cells) {
            outputBuilder.append(c.toString());
            outputBuilder.append(" ");
        }
        return outputBuilder.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder outputBuilder = new StringBuilder();
        for (Coord c : this.cells) {
            outputBuilder.append(c.toString());
            outputBuilder.append(" ");
        }
        return outputBuilder.toString().trim();
    }

    /**
     * Evaluates current cell to its base Value.
     *
     * @param v the visitor used to evaluate cell
     * @return base Value of cell.
     */
    @Override
    public Value acceptEvalVisitor(WorkSheetModel.EvalVisitor v) {
        return v.visitRefCell(this);
    }

    /**
     * Returns this CellContents in ArrayList form.
     *
     * @param v represent a visitor for evaluation
     * @return
     */
    @Override
    public ArrayList<CellContents> forOps(WorkSheetModel.EvalVisitor v) {
        return v.getContentRefCell(this);
    }

}
