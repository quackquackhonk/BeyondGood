package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

import java.util.*;

/**
 * Model class for working with spreadsheets.
 */
public class WorkSheetModel implements IWriteWorkSheetModel<CellContents> {

  HashMap<Coord, CellContents> sheet;
  HashMap<Coord, ArrayList<HashSet<Coord>>> adjList;
  boolean isValid;
  int numRow;
  int numCol;
  int maxRow;
  int minRow;
  int maxCol;
  int minCol;

  /**
   * Creates a WorkSheetModel for evaluating and editing spreadsheets.
   */
  public WorkSheetModel() {
    this.sheet = new HashMap<>();
    this.adjList = new HashMap<>();
    maxRow = 0;
    minRow = 0;
    maxCol = 0;
    minCol = 0;
  }

  protected HashMap<Coord, ArrayList<HashSet<Coord>>> getAdjList() {
    return this.adjList;
  }

  // Returns whether or not model has problematic cells in model.
  @Override
  public Boolean hasErrors() {
    return !this.isValid;
  }

  /**
   * Returns the max width of sheet.
   *
   * @return the width of the row
   */
  @Override
  public int getMaxRow() {
    return maxRow;
  }

  /**
   * Returns the min width of sheet.
   *
   * @return the width of the row
   */
  @Override
  public int getMinRowWidth() {
    return minRow;
  }

  /**
   * Returns the min height of sheet.
   *
   * @return the height of the col
   */
  @Override
  public int getMinColHeight() {
    return minCol;
  }

  /**
   * Returns the max height of the sheet.
   *
   * @return the height of the col
   */
  @Override
  public int getMaxCol() {
    return maxCol;
  }

  @Override
  public void shiftCells(CellContents c, int x, int y) {
    // we will use for future assignments

  }


  @Override
  public void dragChange(Coord start, Coord finish) {
    // we will use for future assignments

  }


  @Override
  public void updateCell(Coord location, String value) {
    // we will use for future assignments

  }

  /**
   * Prints result of evaluated cell at given coordinate. coord
   */
  @Override
  public String evaluateCell(String coord) {
    if (!this.isValid) {
      throw new IllegalArgumentException("Model has incorrect cells");
    }
    try {
      Coord target = getCoordFromString(coord);
      CellContents cell = getCell(target);
      Value finalVal = cell.acceptEvalVisitor(new EvalVisitor());
      //System.out.println(finalVal.toString());
      return finalVal.toString();
    } catch (NullPointerException n) {
      throw new IllegalArgumentException("Can't evaluate cell or cell doesn't exist" + coord);
    }

  }

  /**
   * Evaluates the give cell and returns result in String form.
   *
   * @param col col in sheet
   * @param row row in sheet
   * @return String result
   */
  @Override
  public String evaluateCell(int col, int row) {
    Coord c = new Coord(col, row);
    return this.evaluateCell(c.toString());
  }

  /**
   * Returns the raw text of the cell at given Coordinate. coord
   */
  @Override
  public String getCellText(Coord coord) {
    CellContents cell = getCell(coord);
    if (cell.forOps(new EvalVisitor()).size() == 0) {
      return null;
    } else {
      return cell.getRaw();
    }
  }

  @Override
  public String getCellText(int col, int row) {
    Coord c = new Coord(col, row);
    return this.getCellText(c);
  }

  @Override
  public void evaluateIndCell(String coord) {
    Coord target = getCoordFromString(coord);
    CellContents cell = getCell(target);
    Value finalVal = cell == null ? new Blank() : cell.acceptEvalVisitor(new EvalVisitor());
  }

  /**
   * Return cell at a provided location. loc is the coordinates of the cell.
   *
   * @return the CC at the coordinate.
   */
  @Override
  public CellContents getCell(Coord loc) throws IllegalArgumentException {
    try {
      CellContents cell = this.sheet.get(loc);
      return cell.stringParams().equals("") ? null : cell;
    } catch (NullPointerException e) {
      return null;
    }
  }

  /**
   * returns the Coords in this model.
   */
  @Override
  public HashSet<Coord> activeCells() {
    return new HashSet<Coord>(this.sheet.keySet());
  }

  /**
   * Checks a cell for errors and evaluates it.
   *
   * @param coord location of cell.
   */
  @Override
  public String evaluateCellCheck(String coord) {
    Coord tar = this.getCoordFromString(coord);
    if (!this.getProbCells(tar).isEmpty()) {
      throw new IllegalArgumentException("Cell in cycle");
    } else {
      try {
        Coord target = getCoordFromString(coord);
        CellContents cell = getCell(target);
        Value finalVal = cell.acceptEvalVisitor(new EvalVisitor());
        //System.out.println(finalVal.toString());
        return finalVal.toString();
      } catch (NullPointerException n) {
        throw new IllegalArgumentException("Can't evaluate cell or cell doesn't exist" + coord);
      }
    }
  }

  /**
   * Checks the validity of all cells in the worksheet by parsing their s-expressions. toSkip -
   * Cycle cells caught in checkAdjList();
   *
   * @return rest of the bad cells
   */

  protected HashSet<Coord> checkGrid(HashSet<Coord> toSkip) {
    HashSet<Coord> badCells = new HashSet<>();
    Set<Coord> toCheck = new HashSet<>(this.sheet.keySet());
    // Remove cells that had cycles.
    for (Coord c : toSkip) {
      toCheck.remove(c);
    }

    // Evaluate every cell, accumulate the bad ones.
    // Returns the bad ones.
    for (Coord c : toCheck) {
      try {
        if (getCell(c) != null) {
          evaluateCell(c.toString());
        } else {
          if (this.sheet.get(c) == null) {
            String strCoord = c.toString();
            SexpVisitParser coordParser = new SexpVisitParser();
            String[] coordComps = coordParser.checkCoord(strCoord);

            buildCell(Coord.colNameToIndex(coordComps[0]),
                    Integer.parseInt(coordComps[1]), new Blank());
          }
        }
      } catch (IllegalArgumentException e) {
        badCells.add(c);
      }
    }
    return badCells;
  }


  /**
   * Checks evaluable cells and cyclic references.
   */
  @Override
  public void setupModel() {
    isValid = true;

    this.buildAdjList();
    HashSet<Coord> cycleCells = this.checkAdjList();
    HashSet<Coord> badEvalCells = this.checkGrid(cycleCells);

    // Do problem cells exist?
    isValid = cycleCells.size() == 0 && badEvalCells.size() == 0;
    for (Coord c : cycleCells) {
      System.out.println("Error in cell: " + c.toString() + ": Was in cycle");
    }

    for (Coord c : badEvalCells) {
      System.out.println("Error in cell: " + c.toString() + ": Could not be evaluated");
    }
  }

  // Checks adjacency list for cycles
  protected HashSet<Coord> checkAdjList() {
    Set<Coord> needCheck = this.adjList.keySet();
    HashSet<Coord> allProbCells = new HashSet<>();

    // Gets all cells in a cycle starting with cur cell.
    // Adds them to allProbCells, removes them from needCheck.
    for (Coord cur : needCheck) {
      // Only check if haven't seen before, as part of DFS.
      if (!allProbCells.contains(cur) && this.sheet.get(cur) != null) {
        HashSet<Coord> curProbCells = getProbCells(cur);
        allProbCells.addAll(curProbCells);
      }
    }
    //System.out.println(allProbCells);
    return allProbCells;
  }

  // Returns all cells that are in a cycle with origin. Can be empty Set.
  protected HashSet<Coord> getProbCells(Coord origin) {
    Stack<Coord> visited = new Stack<>();
    HashSet<Coord> output = new HashSet<>();
    Stack<Coord> todo = new Stack<>();

    if (this.sheet.get(origin).stringParams().equals("")) {
      return output;
    }

    // Add origin to visited, push origin's dependencies to todo.
    visited.push(origin);
    HashSet<Coord> origDep = this.adjList.get(origin).get(1);
    boolean isCycle = false;
    for (Coord c : origDep) {
      todo.push(c);
    }

    //System.out.println(todo + visited.toString());
    // Loops until a cycle is found or all Coords checked.
    while (!todo.empty() && !isCycle) {
      Coord toCheck = todo.pop();
      HashSet<Coord> newDep = this.adjList.get(toCheck).get(1);
      for (Coord c : newDep) {
        todo.push(c);
      }

      // Backtracks by popping from visited.
      if (visited.search(toCheck) != -1) {
        isCycle = true;
        output.add(origin);
        break;
      }
      visited.push(toCheck);
    }
    // Returns list of cells affected by cycle. Can be empty.
    return output;
  }

  /**
   * Build a cell at a given location provided with col and row numbers. col  represents the col
   * number row  represents the row number cont the content of the cell
   *
   * @throws IllegalStateException    if the model was not checked
   * @throws IllegalArgumentException if the location of the cell is invalid
   */
  @Override
  public void buildCell(int col, int row, CellContents cont) {
    minCol = Math.min(col, minCol);
    maxCol = Math.max(col, maxCol);
    minRow = Math.min(row, minRow);
    maxRow = Math.max(row, maxRow);

    Coord coord = new Coord(col, row);
    this.sheet.put(coord, cont);
    ArrayList<HashSet<Coord>> childDeps = new ArrayList<>();
    childDeps.add(new HashSet<>());
    childDeps.add(new HashSet<>());
    this.adjList.put(coord, childDeps);
  }

  // Returns the adjacency list of the given coord. Returns null if sheet doesn't contain coord.
  protected ArrayList<HashSet<Coord>> getCellAdjList(Coord coord) {
    try {
      return this.adjList.get(coord);
    } catch (NullPointerException p) {
      return null;
    }
  }

  /*
   * Creates and sets a cell at coordinate in input string if valid.
   * -Create cell, store old one.
   * - get dependencies of new cell
   * - Get children of setCell's coord if has any
   * - Check for cycles
   * - If no cycles, evaluate itself
   *    - if works, evaluate children to make sure it works
   *
   * - replace cell
   */
  @Override
  public void setCell(int col, int row, String cellString) {
    Coord coord = new Coord(col, row);
    if (cellString == null) {
      buildCell(col, row, new Blank());
    } else {
      //System.out.println("Setting cell " + coord.toString());
      CellContents newCell = SheetBuilder.cellFromString(cellString);

      boolean coordExists = this.getCellAdjList(coord) != null;
      // Current Dependencies or none.
      HashSet<Coord> curDeps = coordExists ? this.getCellAdjList(coord).get(1) : new HashSet<>();
      HashSet<Coord> curChild = coordExists ? this.getCellAdjList(coord).get(0) : new HashSet<>();
      CellContents oldCell = getCell(coord);
      HashSet<Coord> newDeps = this.getDepCoords(newCell.stringParams());

      // Set new dependencies to check for cycles.
      this.adjList.get(coord).set(1, newDeps);
      this.sheet.put(coord, newCell);

      HashSet<Coord> cycleCells = this.getProbCells(coord);
      if (cycleCells.size() == 0) {
        try {
          // Evaluate current cell
          newCell.acceptEvalVisitor(new EvalVisitor());
          HashSet<Coord> needCheck = new HashSet<>(newDeps);
          needCheck.addAll(curChild);

          // Only check children and dependencies of Coord skip rest.
          HashSet<Coord> dontCheck = new HashSet<>(this.sheet.keySet());
          dontCheck.removeAll(needCheck);

          HashSet<Coord> probEvalCells = this.checkGrid(dontCheck);
          // If cells can't evaluate, put old cell/dependencies back.
          if (probEvalCells.size() != 0) {
            this.sheet.put(coord, oldCell);
            this.adjList.get(coord).set(1, curDeps);
            throw new IllegalArgumentException("New cell messes up old cells");
          }
          //System.out.println("Successfully added " + coord.toString());
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("New cell can't evaluate");
        }
      } else {
        throw new IllegalArgumentException("New cell has a cycle");
      }
    }
  }

  // Builds adjacency list for all cells in sheet.
  protected void buildAdjList() {
    Set<Coord> toCheck = this.sheet.keySet();
    // Gets Coord dependencies of currentCoord
    for (Coord currentCoord : toCheck) {
      // Returns null for blanks
      CellContents cellContent = this.getCell(currentCoord);
      if (cellContent != null) {
        String cellRaw = cellContent.stringParams();
        ArrayList<HashSet<Coord>> childDeps = this.adjList.get(currentCoord);

        // Set dependencies of currentCoord
        HashSet<Coord> dep = this.getDepCoords(cellRaw);
        childDeps.set(1, dep);

        // Adds currentCoord as child of its dependencies
        for (Coord coordDep : dep) {
          if (this.getCellAdjList(coordDep) != null) {
            ArrayList<HashSet<Coord>> neighbors = this.adjList.get(coordDep);
            HashSet<Coord> children = neighbors.get(0);
            children.add(currentCoord);
          } else {
            // If a dependency of currentCoord hasn't been created yet, set it as null
            // and add currentCoord as a child.
            String strCoord = coordDep.toString();
            SexpVisitParser coordParser = new SexpVisitParser();
            String[] coordComps = coordParser.checkCoord(strCoord);
            buildCell(Coord.colNameToIndex(coordComps[0]),
                    Integer.parseInt(coordComps[1]),
                    new Blank());
            this.adjList.get(coordDep).get(0).add(currentCoord);
          }
        }
      }
    }
  }

  // Builds Coords from input String and returns them in list.
  // Can be empty.
  private HashSet<Coord> getDepCoords(String cellRaw) {
    String[] cellRowArray = cellRaw.split(" ");
    HashSet<Coord> coords = new HashSet<>();
    SexpVisitParser coordParser = new SexpVisitParser();

    for (String c : cellRowArray) {
      if (!c.contains("\"")) {
        try {
          String[] coordComps = coordParser.checkCoord(c);
          int col = Coord.colNameToIndex(coordComps[0]);
          int row = Integer.parseInt(coordComps[1]);
          Coord toAdd = new Coord(col, row);
          coords.add(toAdd);
        } catch (IllegalArgumentException e) {
          // TODO: figure out what to put here
        }
      }
    }
    return coords;
  }

  // Builds Coords from input String and returns it.
  // Can be empty.
  private Coord getCoordFromString(String cellRaw) {
    String[] cellRowArray = cellRaw.split(" ");
    HashSet<Coord> coords = new HashSet<>();
    SexpVisitParser coordParser = new SexpVisitParser();

    for (String c : cellRowArray) {
      if (!c.contains("\"")) {
        try {
          String[] coordComps = coordParser.checkCoord(c);
          int col = Coord.colNameToIndex(coordComps[0]);
          int row = Integer.parseInt(coordComps[1]);
          Coord toAdd = new Coord(col, row);
          return toAdd;
        } catch (IllegalArgumentException e) {
          // TODO: figure out what to put here
        }
      }
    }
    throw new IllegalArgumentException("invalid coord");
  }

  /**
   * A factory class to build model.
   */
  public static final class SheetBuilder implements
          WorksheetReader.WorksheetBuilder<IWriteWorkSheetModel> {

    IWriteWorkSheetModel model;

    /**
     * A factory class to build model. model represent a worksheet model
     */
    public SheetBuilder(IWriteWorkSheetModel model) {
      this.model = model;
    }

    /**
     * A factory class to build model.
     */
    public SheetBuilder() {
      this.model = new WorkSheetModel();
    }

    // Creates cell from raw string input with equals.
    // Throws IAE if string can't be made into a cell.
    protected static CellContents cellFromString(String contents) throws IllegalArgumentException {
      try {
        String toParse;
        CellContents cell;
        String first = contents.substring(0, 1);
        // Parses CellContents if equal sign seen
        if (first.equals("=")) {
          toParse = contents.substring(1);
          Sexp parsed = Parser.parse(toParse);
          SexpVisitParser visitParser = new SexpVisitParser();
          cell = parsed.accept(visitParser);
          // No SSymbols allowed if not preceded by "="
        } else {
          Sexp parsed = Parser.parse((contents));
          SexpVisitParserSymbol visitParser = new SexpVisitParserSymbol();
          cell = parsed.accept(visitParser);
        }
        return cell;
      } catch (IllegalArgumentException e) {
        //System.out.println(contents);
        throw new IllegalArgumentException(e);
      }
    }

    /**
     * Creates a new cell at the given coordinates and fills in its raw contents. col      the
     * column of the new cell (1-indexed) row      the row of the new cell (1-indexed) contents the
     * raw contents of the new cell: may be {@code null}, or any string. Strings beginning with an
     * {@code =} character should be treated as formulas; all other strings should be treated as
     * number or boolean values if possible, and string values otherwise.
     *
     * @return this {@link WorksheetBuilder}
     */
    @Override
    public SheetBuilder createCell(int col, int row, String contents) {
      try {
        CellContents cell = cellFromString(contents);
        this.model.buildCell(col, row, cell);
      } catch (IllegalArgumentException e) {
        // Catch IAE thrown by parser
        System.out.println("Error in cell " +
                Coord.colIndexToName(col) + row + ": Unparsable Sexpression");
      }
      return new SheetBuilder(this.model);
    }


    @Override
    public IWriteWorkSheetModel createWorksheet() {
      this.model.setupModel();
      return this.model;
    }
  }

  /*
  Visitor for evaluating CellContents.
   */
  public final class EvalVisitor implements IEvalVisitor<Value> {

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentBool(Bool b) {
      ArrayList<CellContents> arr = new ArrayList<>();
      arr.add(b);
      return arr;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentStr(Str b) {
      ArrayList<CellContents> arr = new ArrayList<>();
      arr.add(b);
      return arr;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentDbl(Dbl b) {
      ArrayList<CellContents> arr = new ArrayList<>();
      arr.add(b);
      return arr;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentRefCell(ReferenceCell b) {
      ArrayList<Coord> coords = b.evaluate();
      ArrayList<CellContents> cells = new ArrayList<>();
      for (Coord c : coords) {
        CellContents cellComp = WorkSheetModel.this.getCell(c);
        if (cellComp != null) {
          cells.add(cellComp);
        }
      }
      return cells;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentSum(SUM b) {
      ArrayList<CellContents> evaled = new ArrayList<>();
      evaled.add(b.acceptEvalVisitor(this));
      return evaled;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentProduct(PRODUCT b) {
      ArrayList<CellContents> evaled = new ArrayList<>();
      evaled.add(b.acceptEvalVisitor(this));
      return evaled;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentLess(LESSTHAN b) {
      ArrayList<CellContents> evaled = new ArrayList<>();
      evaled.add(b.acceptEvalVisitor(this));
      return evaled;
    }

    /**
     * Returns this CellContents as an ArrayList of CellContents.
     *
     * @param b CC
     * @return ArrayList of CC
     */
    public ArrayList<CellContents> getContentGreater(GREATERTHAN b) {
      ArrayList<CellContents> evaled = new ArrayList<>();
      evaled.add(b.acceptEvalVisitor(this));
      return evaled;
    }

    /**
     * Returns boolean of Bool b
     */
    @Override
    public Value visitBool(Bool b) {
      return b;
    }

    /**
     * Returns String of Str s
     */
    @Override
    public Value visitStr(Str s) {
      return s;
    }

    /**
     * Returns double of Dbl
     */
    @Override
    public Value visitDbl(Dbl d) {
      return d;
    }

    /**
     * Returns evaluated cell if single reference, throws an error for regions of cells. r reference
     * cell to evaluate.
     *
     * @return returns ReferenceCell
     */
    @Override
    public Value visitRefCell(ReferenceCell r) {
      ArrayList<Coord> contCoord = r.evaluate();
      if (contCoord.size() > 1) {
        throw new IllegalArgumentException("Can't evaluate multiple cells without a formula");
      } else {
        CellContents cellCont = WorkSheetModel.this.sheet.get(contCoord.get(0));
        if (cellCont.equals(r)) {
          throw new IllegalArgumentException("Cell can't refer to itself");
        }
        return cellCont.acceptEvalVisitor(this);
      }
    }

    /**
     * Returns sum of contents.
     */
    @Override
    public Dbl visitSUM(SUM s) {
      double total = 0;
      ArrayList<CellContents> cont = s.getInnerCells();
      if (cont.isEmpty()) {
        throw new IllegalArgumentException("Can't sum 0 arguments");
      }
      if (cont.size() == 1) {
        CellContents single = cont.get(0);
        ArrayList<CellContents> singleCont = single.forOps(this);
        if (singleCont.size() == 0) {
          return new Dbl(0);
        }
        if (singleCont.size() == 1) {
          try {
            return singleCont.get(0).acceptEvalVisitor(this).getDbl();
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formula - Non-numeric argument");
          }

        } else {
          SUM rangeSum = new SUM(singleCont);
          return rangeSum.acceptEvalVisitor(this).getDbl();
        }
      } else {
        for (CellContents c : cont) {
          SUM toAdd = new SUM(c.forOps(this));
          Dbl dblAdd = toAdd.acceptEvalVisitor(this).getDbl();
          total += dblAdd.evaluate();
        }
      }
      return new Dbl(total);
    }

    /**
     * Evaluates this blank.
     *
     * @param b blank
     * @return Value
     */
    @Override
    public Value visitBlank(Blank b) {
      return b;
    }

    /**
     * Returns product of contents.
     */
    @Override
    public Value visitPRODUCT(PRODUCT s) {
      double total = 1;
      ArrayList<CellContents> cont = s.getInnerCells();

      if (cont.isEmpty()) {
        throw new IllegalArgumentException("Can't multiply 0 arguments");
      }
      if (cont.size() == 1) {
        // References to nonexistant cells / nulls are null
        CellContents single = cont.get(0);
        ArrayList<CellContents> singleCont = single.forOps(this);

        if (singleCont.size() == 0) {
          return new Dbl(1);
        }
        if (singleCont.size() == 1) {
          try {
            return singleCont.get(0).acceptEvalVisitor(this).getDbl();
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formula - Non-numeric argument");
          }
        } else {
          PRODUCT rangeProd = new PRODUCT(singleCont);
          return rangeProd.acceptEvalVisitor(this).getDbl();
        }
      } else {
        for (CellContents c : cont) {
          PRODUCT toMult = new PRODUCT(c.forOps(this));
          Dbl dblMult = toMult.acceptEvalVisitor(this).getDbl();
          total *= dblMult.evaluate();
        }
      }
      return new Dbl(total);
    }

    /**
     * Returns if first argument is greater than second argument.
     */
    @Override
    public Value visitGREATERTHAN(GREATERTHAN s) {
      ArrayList<CellContents> cont = s.getInnerCells();
      // base case if two values to compare.
      if (cont.size() == 2) {
        try {
          CellContents first = cont.get(0);
          ArrayList<CellContents> firstCont = first.forOps(this);
          CellContents second = cont.get(1);
          ArrayList<CellContents> secCont = second.forOps(this);

          // compare the two numeric arguments
          if (firstCont.size() == 1 && secCont.size() == 1) {
            try {
              Dbl num1 = firstCont.get(0).acceptEvalVisitor(this).getDbl();
              Dbl num2 = secCont.get(0).acceptEvalVisitor(this).getDbl();
              boolean result = num1.evaluate() > num2.evaluate();
              return new Bool(result);
            } catch (IllegalArgumentException e) {
              throw new IllegalArgumentException("Formula - Non-numeric argument");
            }
          } else {
            Value val1 = first.acceptEvalVisitor(this);
            Value val2 = first.acceptEvalVisitor(this);
            ArrayList<CellContents> lessParams = new ArrayList<>();
            lessParams.add(val1);
            lessParams.add(val2);
            LESSTHAN newLess = new LESSTHAN(lessParams);
            return newLess.acceptEvalVisitor(this);
          }
        } catch (IllegalArgumentException e) {
          throw new IllegalArgumentException("Parameters aren't both numeric");
        }
      } else {
        throw new IllegalArgumentException("Incorrect number of parameters");
      }
    }

    /**
     * Returns if first argument is less than second argument. (numeric inputs)
     */
    @Override
    public Value visitLESSTHAN(LESSTHAN s) {
      ArrayList<CellContents> cont = s.getInnerCells();
      // base case if two values to compare.
      if (cont.size() == 2) {
        CellContents first = cont.get(0);
        ArrayList<CellContents> firstCont = first.forOps(this);
        CellContents second = cont.get(1);
        ArrayList<CellContents> secCont = second.forOps(this);

        if (firstCont.size() == 1 && secCont.size() == 1) {
          try {
            Dbl num1 = firstCont.get(0).acceptEvalVisitor(this).getDbl();
            Dbl num2 = secCont.get(0).acceptEvalVisitor(this).getDbl();
            boolean result = num1.evaluate() < num2.evaluate();
            return new Bool(result);
          } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Formula - Non-numeric argument");
          }
        } else {
          Value val1 = first.acceptEvalVisitor(this);
          Value val2 = first.acceptEvalVisitor(this);
          ArrayList<CellContents> lessParams = new ArrayList<>();
          lessParams.add(val1);
          lessParams.add(val2);
          LESSTHAN newLess = new LESSTHAN(lessParams);
          return newLess.acceptEvalVisitor(this);
        }
      } else {
        throw new IllegalArgumentException("Incorrect number of parameters");
      }
    }

    // Returns single size ArrayList of the base case or
    // returns ArrayList of CellContents otherwise.
    protected ArrayList<CellContents> evalOps(CellContents cell) {
      ArrayList<CellContents> cont = cell.forOps(this);
      if (cont.size() == 1) {
        CellContents single = cont.get(0);
        if (single.forOps(this).get(0).equals(single)) {
          ArrayList<CellContents> singleList = new ArrayList<>();
          singleList.add(single);
          return singleList;
        } else {
          CellContents toEval = (CellContents) single.forOps(this).get(0);
          return evalOps(toEval);
        }
      } else {
        return cont;
      }
    }
  }
}

