package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ControllerFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI view for IReadWorkSheetModels.
 */
public class SpreadsheetGUIView extends JFrame implements IView {

  private IReadWorkSheetModel model;
  private GridPanel gridPanel;

  //private JScrollPane scrollPane;
  private SpreadsheetScrollingPanel scrollPane;
  private int cellWidth;
  private int cellHeight;

  /**
   * Constructs a GUI view for IReadWorkSheetModel.
   */
  public SpreadsheetGUIView(IReadWorkSheetModel model) {
    super();
    this.model = model;
    this.setTitle("Beyond gOOD Editor");
    //this.rowMinGrid = 0;
    //this.colMinGrid = 0;
    this.cellWidth = 80;
    this.cellHeight = (int) (cellWidth / 2.5);

    // Get active model cells, draw them
    HashSet<Coord> modelCells = model.activeCells();
    HashMap<Coord, String> stringCells = new HashMap<>();

    // Display cycle/formula errors
    for (Coord c : modelCells) {
      try {
        String cellResult = model.evaluateCellCheck(c.toString());
        // only add the cell if it is not empty
        if (!cellResult.equals("")) {
          stringCells.put(c, cellResult);
        }
      } catch (IllegalArgumentException e) {
        String msg = e.getMessage();
        //System.out.println(msg + " "+ c.toString());
        if (msg.contains("cycle")) {
          stringCells.put(c, "#REF!");
        } else if (msg.contains("Formula")) {
          stringCells.put(c, "#VALUE!");
        }
      }
    }

    this.setLayout(new BorderLayout());
    this.setSize(this.getPreferredSize());

    // Size the grid panel to be as wide/tall as the furthest out cells + some buffer.
    // If this size is smaller than the Frame size, use the frame size instead.
    //System.out.println(model.getMaxRow());
    int initPanelWidth = Math.max(getPreferredSize().width, model.getMaxCol() * cellWidth);
    //System.out.println(initPanelWidth);
    int initPanelHeight = Math.max(getPreferredSize().height, model.getMaxRow() * cellHeight);

    // Determine number of rows and columns GridPanel needs to display given Frame dimensions
    int numRow = this.getPreferredSize().width / cellWidth + 3;
    int numCol = this.getPreferredSize().height / cellHeight + 3;
    //System.out.println(numRow);

    int colEnd = this.getWidth() / cellWidth + 3;
    int rowEnd = this.getHeight() / cellWidth + 3;

    gridPanel = new GridPanel(numRow, numCol, cellWidth, cellHeight,
        stringCells, 0, colEnd, 0, rowEnd);
    this.scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
    this.scrollPane.setPreferredSize(new Dimension(initPanelWidth + 3 * cellWidth,
        initPanelHeight + 3 * cellHeight));

    // Three cell buffer
    gridPanel.setPreferredSize(
        new Dimension(initPanelWidth + 3 * cellWidth, initPanelHeight + 3 * cellHeight));
    this.add(scrollPane, BorderLayout.CENTER);

    // Add FormulaPanel, currently not editable.
    JPanel formulaBarPanel = new JPanel();
    formulaBarPanel.setLayout(new FlowLayout());
    JTextField formText = new JTextField("Default formula", 20);
    formText.setEditable(true);

    formulaBarPanel.add(formText);
    this.add(formulaBarPanel, BorderLayout.NORTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowStateListener(we -> createScrollPanel(stringCells));
    this.pack();
  }

  /**
   * Handles the events for when the frame is resized. Removes the scrollPane and recreates it based
   * on the current size of the window.
   *
   * @param stringCells the hashmap of strings for the gridpanel to render.
   */
  private void createScrollPanel(HashMap<Coord, String> stringCells) {
    this.remove(this.scrollPane);
    Dimension currentSize = this.getSize();
    int numRow = this.getPreferredSize().width / cellWidth + 3;
    int numCol = this.getPreferredSize().height / cellHeight + 3;
    int currPanelWidth = Math.max(currentSize.width, model.getMaxCol() * cellWidth);
    int currPanelHeight = Math.max(currentSize.height, model.getMaxRow() * cellHeight);

    int colStart = gridPanel.getColStart();
    int colEnd = gridPanel.getColEnd();

    int rowStart = gridPanel.getRowStart();
    int rowEnd = gridPanel.getRowEnd();

    gridPanel = new GridPanel(numRow, numCol, cellWidth,
        cellHeight, stringCells, colStart, colEnd, rowStart, rowEnd);
    scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
    this.setPreferredSize(new Dimension(currPanelWidth + 3 * cellWidth,
        currPanelHeight + 3 * cellHeight));
    // Three cell buffer
    gridPanel.setPreferredSize(
        new Dimension(currPanelWidth + 3 * cellWidth,
            currPanelHeight + 3 * cellHeight));
    this.add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void render() throws IOException {
    this.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 600);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showErrorMessage(String error) {
    throw new UnsupportedOperationException("Read-only views has no reason to throw errors");
  }

  @Override
  public void addActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("Read-only view has no listeners");
  }

  @Override
  public void setInputText(String s) {
    throw new UnsupportedOperationException("Read-only view has no input mechanism");
  }

  @Override
  public String getInputText() {
    throw new UnsupportedOperationException("Read-only view has no input mechanism");
  }

  @Override
  public Coord coordFromLoc(int x, int y) {
    return this.scrollPane.coordFromLoc(x, y);
  }

  @Override
  public Coord getSelectedCell() {
    throw new UnsupportedOperationException("Read-only views cannot select cells");
  }

  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {
    throw new UnsupportedOperationException("Read-only view has no setup, since it contains a " +
            "read-only model");
  }

  @Override
  public void updateView(Coord coord, String cell) {
    throw new UnsupportedOperationException("Read-only views cannot be updated");
  }

  @Override
  public void resetInput() {
    throw new UnsupportedOperationException("Non-editable views have no input to reset");
  }

  @Override
  public String getColToAdd() {
    throw new UnsupportedOperationException("Non-editable views cannot add columns");
  }

  @Override
  public void setColToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add columns");
  }

  @Override
  public String getRowToAdd() {
    throw new UnsupportedOperationException("Non-editable views cannot add rows");
  }

  @Override
  public void setRowToAdd(String s) {
    throw new UnsupportedOperationException("Read-only views cannot add rows");
  }

  @Override
  public void addFeatures(ControllerFeatures f) {
    throw new UnsupportedOperationException("Read-only view does not add features");
  }

  @Override
  public void cellSelectWithKey(Coord coord) {
    throw new UnsupportedOperationException("Read-only view does not select cells");
  }

  @Override
  public void resetFocus() {
    throw new UnsupportedOperationException("Read-only view does not have focus");
  }

  @Override
  public void resizeView(int maxCol, int maxRow) {
    throw new UnsupportedOperationException("Read-only view cannot be resized");
  }
}
