package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;

/**
 * Represents an abstract cell of Operation (function).
 */
public abstract class Ops implements CellContents {
    ArrayList<CellContents> params;

    /**
     * Returns Class that multiplies all its arguments.
     */
    public Ops(ArrayList<CellContents> params) {
        this.params = params;
    }


    public ArrayList<CellContents> getInnerCells() {
        return this.params;
    }

    @Override
    public String stringParams() {
        String ans = "";
        for (CellContents c : this.params) {
            ans += c.stringParams() + " ";
        }
        //System.out.println(ans.trim());
        return ans.trim();
    }
}
