package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Returns Class based on type of SExpression.
 * This class is used by builder and handles parsing assuming "=" has been seen
 */
public class SexpVisitParser implements SexpVisitor<CellContents> {


    /**
     * Returns an SexpVisitParser to create CellContents.
     */
    public SexpVisitParser() {
        // we still need a constructor as we need to create an instance of
        // this visitor parser in our model.
    }

    /**
     * Process a boolean value.
     *
     * @param b the value
     * @return the desired result
     */
    @Override
    public Bool visitBoolean(boolean b) {
        return new Bool(b);
    }

    /**
     * Process a numeric value.
     *
     * @param d the value
     * @return the desired result
     */
    @Override
    public Dbl visitNumber(double d) {
        return new Dbl(d);
    }

    /**
     * Process a symbol.
     *
     * @param s the value
     * @return the desired result
     */
    @Override
    public ReferenceCell visitSymbol(String s) {
        String[] col = s.split(":");

        if (!(col.length == 2 && s.contains(":") || col.length == 1 && !s.contains(":"))) {
            throw new IllegalArgumentException("not proper coordinate, failed in visitSymbol()");
        } else {
            // Builds column / row components of each String Coord
            ArrayList<String[]> coordComponents = new ArrayList<>();
            for (String coord : col) {
                coordComponents.add(checkCoord(coord));
            }

            return new ReferenceCell(getCoordFromComps(coordComponents));
        }
    }


    /**
     * Returns the components of a Coord in String form. ex; ["A", "1"].
     *
     * @param s the string format of the coord
     * @return an array of column name and row number
     */
    // Returns the components of a Coord in String form. ex; ["A", "1"];
    protected String[] checkCoord(String s) throws IllegalArgumentException {
        String[] chars = s.split("");

        if (!chars[0].matches("^[a-zA-Z]*$")) {
            throw new IllegalArgumentException("Not valid coordinate");
        }

        for (int i = 1; i < chars.length; i++) {
            String toCheck = chars[i];

            if (!chars[i].matches("^[a-zA-Z]*$")) {
                try {
                    Integer num = Integer.parseInt(toCheck);
                    // Row number
                    Integer remaining = Integer.parseInt(s.substring(i));
                    String[] comps = new String[]{s.substring(0, i), s.substring(i)};
                    return comps;

                } catch (NumberFormatException n) {
                    if (!chars[0].matches("^[a-zA-Z]*$")) {
                        throw new IllegalArgumentException("bad coord");
                    }
                }
            }
        }
        throw new IllegalArgumentException(Arrays.toString(chars));
    }

    // Returns list of Coords from one or two base coords in String form.
    protected ArrayList<Coord> getCoordFromComps(ArrayList<String[]> coords) {
        int col1 = Coord.colNameToIndex(coords.get(0)[0]);
        int row1 = Integer.parseInt(coords.get(0)[1]);
        ArrayList<Coord> list = new ArrayList<>();

        if (coords.size() == 1) {
            Coord start = new Coord(col1, row1);
            list.add(start);

        } else if (coords.size() == 2) {
            int col2 = Coord.colNameToIndex(coords.get(1)[0]);
            int row2 = Integer.parseInt(coords.get(1)[1]);

            int minCol = Math.min(col1, col2);
            int maxCol = Math.max(col1, col2);
            int minRow = Math.min(row1, row2);
            int maxRow = Math.max(row1, row2);
            // Loop through and build all required Coords within the range
            for (int i = minCol; i < maxCol + 1; i++) {
                for (int j = minRow; j < maxRow + 1; j++) {
                    Coord toAdd = new Coord(i, j);
                    list.add(toAdd);
                }
            }
        } else {
            throw new IllegalArgumentException("Illegal Coords");
        }
        return list;
    }

    /**
     * Process a string value.
     *
     * @param s the value
     * @return the desired result
     */
    @Override
    public Str visitString(String s) {
        return new Str(s);
    }

    /**
     * Process a list value.
     *
     * @param l the contents of the list (not yet visited)
     * @return the desired result
     */
    @Override
    public CellContents visitSList(List<Sexp> l) {
        // Single length lists should be collapsed to one CellContents
        if (l.size() == 1) {
            Sexp s = l.get(0);
            return s.accept(new SexpVisitParserListOne());
        } else {
            String opName = l.get(0).toString().toUpperCase();
            Ops newOps;
            ArrayList<CellContents> parsedList = createCCParams(l.subList(1, l.size()));
            // Builds appropriate Ops
            switch (opName) {
                case ("SUM"):
                    newOps = new SUM(parsedList);
                    return newOps;
                case ("PRODUCT"):
                    newOps = new PRODUCT(parsedList);
                    return newOps;
                case ("<"):
                    newOps = new LESSTHAN(parsedList);
                    return newOps;
                case (">"):
                    newOps = new GREATERTHAN(parsedList);
                    return newOps;
                default:
                    throw new IllegalArgumentException("bad formula");
            }
        }
    }

    // Parses list of Sexp as CellContents
    private ArrayList<CellContents> createCCParams(List<Sexp> l) {
        ArrayList<CellContents> parsedCont = new ArrayList<>();
        for (Sexp s : l) {
            CellContents cont = s.accept(new SexpVisitParser());
            parsedCont.add(cont);
        }
        return parsedCont;
    }
}
