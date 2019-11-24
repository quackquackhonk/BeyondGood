package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.MouseEventListener;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI view for IReadWorkSheetModels.
 */
public class SpreadsheetGUIViewEditable extends JFrame implements IView {

  private GridPanel gridPanel;
  private JButton formConfirm;
  private JButton formCancel;
  private JTextField formText;
  private HashMap<Coord, String> stringCells;

  //private JScrollPane scrollPane;
  private SpreadsheetScrollingPanel scrollPane;
  private int cellWidth;
  private int cellHeight;
  private int maxCol;
  private int maxRow;
  private boolean ready;
  private String prevText;


  /**
   * Constructs a GUI view for IReadWorkSheetModel.
   */
  public SpreadsheetGUIViewEditable() {
    super();
    this.setTitle("Beyond gOOD Editor");
    //this.rowMinGrid = 0;
    //this.colMinGrid = 0;
    this.cellWidth = 80;
    this.cellHeight = (int) (cellWidth / 2.5);

    HashMap<Coord, String> stringCells = new HashMap<>();
    this.stringCells = stringCells;
    this.setLayout(new BorderLayout());
    this.setSize(this.getPreferredSize());

    // Determine number of rows and columns GridPanel needs to display given Frame dimensions
    int numRow = this.getPreferredSize().width / cellWidth + 3;
    int numCol = this.getPreferredSize().height / cellHeight + 3;
    //System.out.println(numRow);

    int colEnd = this.getWidth() / cellWidth + 3;
    int rowEnd = this.getHeight() / cellWidth + 3;

    gridPanel = new GridPanel(numRow, numCol, cellWidth, cellHeight,
        stringCells, 0, colEnd, 0, rowEnd);


    // Add FormulaPanel, currently not editable.
    JPanel formulaBarPanel = new JPanel();
    formConfirm = new JButton("✔");
    formConfirm.setPreferredSize(new Dimension(45, cellHeight));
    formConfirm.setActionCommand("confirm input");

    formCancel = new JButton("X");
    formCancel.setPreferredSize(new Dimension(45, cellHeight));
    formCancel.setActionCommand("clear input");

    formulaBarPanel.setLayout(new FlowLayout());
    formText = new JTextField("Default formula", 20);
    formText.setEditable(true);

    formulaBarPanel.add(formConfirm);
    formulaBarPanel.add(formCancel);
    formulaBarPanel.add(formText);
    this.add(formulaBarPanel, BorderLayout.NORTH);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.addWindowStateListener(we -> createScrollPanel(this.stringCells));
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
    int currPanelWidth = Math.max(currentSize.width, maxCol * cellWidth);
    int currPanelHeight = Math.max(currentSize.height, maxRow * cellHeight);

    int colStart = gridPanel.getColStart();
    int colEnd = gridPanel.getColEnd();

    int rowStart = gridPanel.getRowStart();
    int rowEnd = gridPanel.getRowEnd();

    gridPanel = new GridPanel(numRow, numCol, cellWidth,
        cellHeight, stringCells, colStart, colEnd, rowStart, rowEnd);
    scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
    this.scrollPane.setPreferredSize(new Dimension(currPanelWidth + 3 * cellWidth,
        currPanelHeight + 3 * cellHeight));
    // Three cell buffer
    gridPanel.setPreferredSize(
        new Dimension(currPanelWidth + 3 * cellWidth,
            currPanelHeight + 3 * cellHeight));
    this.add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * Renders state of model.
   */
  @Override
  public void render() throws IOException {
    if(this.ready) {
      this.repaint();
    } else {
      System.out.println("View has not been setup");
    }
  }

  /**
   * Default size of the frame.
   *
   * @return dimensions of the frame.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1000, 600);
  }

  /**
   * Make the view visible. This is usually called after the view is constructed
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setCommandCallback(Consumer<String> callback) {
    // TODO: figure out what this is supposed to do.
  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error message.
   */
  @Override
  public void showErrorMessage(String error) {
    // Implemented in the future.
  }

  /**
   * this is to force the view to have a method to set up actions for buttons. All the buttons must
   * be given this action listener
   * <p>
   * Thus our Swing-based implementation of this interface will already have such a method.
   *
   * @param listener the listener to add.
   */
  @Override
  public void addActionListener(ActionListener listener) {
    this.formConfirm.addActionListener(listener);
    this.formCancel.addActionListener(listener);
  }

  /**
   * Forces view to have a method to set up listeners for mouse events. For Swing views, this
   * method
   * will already be implemented through Java Swing. For non-swing views, this will need to be
   * written.
   * @param listener the MouseListener to add.
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    this.gridPanel.addMouseListener(listener);
  }

  /**
   * Sets default user input.
   */
  @Override
  public void setInputText(String s) {
    this.formText.setText(s);
  }


  /**
   * Gets the text the user has inputted in the input field.
   */
  @Override
  public String getInputText() {
    return this.formText.getText();
  }


  /**
   * Determine corresponding Coord from x position and y position on the worksheet.
   *
   * @param x x position
   * @param y y position
   * @return Coord that corresponds to inputs
   */
  @Override
  public Coord coordFromLoc(int x, int y) {
    this.formText.requestFocus();
    this.prevText = this.formText.getText();
    Coord cell = this.scrollPane.coordFromLoc(x, y);
    this.repaint();
    return cell;
  }

  /**
   * The Coord of the cell currently selected by the user.
   *
   * @return Coord of highlighted cell
   */
  @Override
  public Coord getSelectedCell() {
    return this.gridPanel.getSelectedCell();
  }

  /**
   * Initializes view by passing in the cells to display and the range of cells to display.
   *
   * @param stringCells All cells in the sheet.
   * @param maxCol render cells up to this column
   * @param maxRow render cells up to this row
   */
  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {
    this.setTitle("Beyond gOOD Editor");
    this.maxCol = maxCol;
    this.maxRow = maxRow;
    this.stringCells = stringCells;

    this.scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
    this.add(scrollPane, BorderLayout.CENTER);
    // Size the grid panel to be as wide/tall as the furthest out cells + some buffer.
    // If this size is smaller than the Frame size, use the frame size instead.
    //System.out.println(model.getMaxRow());
    int initPanelWidth = Math.max(getPreferredSize().width, this.maxCol * cellWidth);
    //System.out.println(initPanelWidth);
    int initPanelHeight = Math.max(getPreferredSize().height, this.maxRow * cellHeight);

    // Three cell buffer
    gridPanel.setPreferredSize(
        new Dimension(initPanelWidth + 3 * cellWidth, initPanelHeight + 3 * cellHeight));

    gridPanel.setCells(stringCells);
    formConfirm.setPreferredSize(new Dimension(45, cellHeight));
    formCancel.setPreferredSize(new Dimension(45, cellHeight));
    this.pack();
    this.revalidate();
    ready = true;
  }

  /**
   * Update the view with new cells.
   *
   * @param coord location of cell.
   * @param cell contents of new cell in String form
   */
  @Override
  public void updateView(Coord coord, String cell) {
    this.stringCells.put(coord, cell);
    this.gridPanel.addCell(coord, cell);
    this.repaint();
  }

  /**
   * Reverts input state prior to user modification.
   */
  @Override
  public void resetInput() {
    this.formText.setText(this.prevText);
  }
}
