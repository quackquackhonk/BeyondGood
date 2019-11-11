package edu.cs3500.spreadsheets.model;

/**
 * Class to catch range references in single length lists.
 */
public class SexpVisitParserListOne extends SexpVisitParser {
    public SexpVisitParserListOne() {
        // we still need a constructor as we need to create an instance of
        // this visitor parser in our model.
    }

    ;

    // Catches references in single length lists.
    @Override
    public ReferenceCell visitSymbol(String s) {
        String[] col = s.split(":");
        if (col.length == 2) {
            throw new IllegalArgumentException("No range references in single lists");
        } else {
            return super.visitSymbol(s);
        }
    }
}
