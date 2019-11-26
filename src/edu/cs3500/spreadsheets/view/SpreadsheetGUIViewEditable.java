package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.ButtonListener;
import edu.cs3500.spreadsheets.controller.ControllerFeatures;
import edu.cs3500.spreadsheets.controller.KeyboardListener;
import edu.cs3500.spreadsheets.controller.MouseEventListener;
import edu.cs3500.spreadsheets.controller.MouseRunnable;
import edu.cs3500.spreadsheets.model.Coord;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * GUI view for IReadWorkSheetModels.
 */
public class SpreadsheetGUIViewEditable extends JFrame implements IView {

  private MouseListener mouseEvent = this.defaultMouseListener();
  private GridPanel gridPanel;
  private JButton formConfirm;
  private JButton formCancel;
  private JTextField formText;
  private JLabel addColLabel;
  private JLabel addRowLabel;
  private JTextField addColField;
  private JTextField addRowField;
  private JButton addColButton;
  private JButton addRowButton;
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
    formulaBarPanel.setLayout(new FlowLayout());
    formConfirm = new JButton("✔");
    formConfirm.setPreferredSize(new Dimension(45, cellHeight));
    formConfirm.setActionCommand("confirm input");

    formCancel = new JButton("X");
    formCancel.setPreferredSize(new Dimension(45, cellHeight));
    formCancel.setActionCommand("clear input");

    formText = new JTextField("Default formula", 20);
    formText.setEditable(true);
    formText.setActionCommand("");

    // UI option to add rows and columns
    addColLabel = new JLabel("Add Column At:");
    addColField = new JTextField("", 5);
    addColButton = new JButton("✔");
    addColButton.setPreferredSize(new Dimension(45, cellHeight));
    addColButton.setActionCommand("add column");

    addRowLabel = new JLabel("Add Row At:");
    addRowField = new JTextField("", 5);
    addRowButton = new JButton("✔");
    addRowButton.setPreferredSize(new Dimension(45, cellHeight));
    addRowButton.setActionCommand("add row");

    formulaBarPanel.add(formConfirm);
    formulaBarPanel.add(formCancel);
    formulaBarPanel.add(formText);
    formulaBarPanel.add(addRowLabel);
    formulaBarPanel.add(addRowField);
    formulaBarPanel.add(addRowButton);
    formulaBarPanel.add(addColLabel);
    formulaBarPanel.add(addColField);
    formulaBarPanel.add(addColButton);

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

    System.out.println(getPreferredSize() + " gui size");
    int initPanelWidth = Math.max(getPreferredSize().width, this.maxCol * cellWidth);
    int initPanelHeight = Math.max(getPreferredSize().height, this.maxRow * cellHeight);

    // Three cell buffer
    gridPanel.setPreferredSize(
            new Dimension(initPanelWidth + 3 * cellWidth, initPanelHeight + 3 * cellHeight));

    scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
    this.scrollPane.setPreferredSize(new Dimension(currPanelWidth + 3 * cellWidth,
            currPanelHeight + 3 * cellHeight));

    // Three cell buffer
    gridPanel.setPreferredSize(
            new Dimension(currPanelWidth + 3 * cellWidth,
                    currPanelHeight + 3 * cellHeight));
    //this.gridPanel.addMouseListener(mouseEvent);
    this.add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void render(){
    if (this.ready) {
      this.repaint();
    } else {
      System.out.println("View has not been setup");
    }
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
    JOptionPane.showMessageDialog(this, error);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.formConfirm.addActionListener(listener);
    this.formCancel.addActionListener(listener);
    this.addRowButton.addActionListener(listener);
    this.addColButton.addActionListener(listener);
  }

  @Override
  public void addMouseListener(MouseListener listener) {
    this.mouseEvent = listener;
    this.gridPanel.addMouseListener(listener);
  }

  @Override
  public void setInputText(String s) {
    this.formText.setText(s);
  }

  @Override
  public String getInputText() {
    return this.formText.getText();
  }

  @Override
  public Coord coordFromLoc(int x, int y) {
    //this.formText.requestFocus();
    this.prevText = this.formText.getText();
    Coord cell = this.scrollPane.coordFromLoc(x, y);
    //this.repaint();
    return cell;
  }

  @Override
  public Coord getSelectedCell() {
    return this.gridPanel.getSelectedCell();
  }

  @Override
  public void setupView(HashMap<Coord, String> stringCells, int maxCol, int maxRow) {
    this.setTitle("Beyond gOOD Editor");
    this.maxCol = maxCol;
    this.maxRow = maxRow;
    this.stringCells = stringCells;

    // Size the grid panel to be as wide/tall as the furthest out cells + some buffer.
    // If this size is smaller than the Frame size, use the frame size instead.
    //System.out.println(model.getMaxRow());
    int initPanelWidth = Math.max(getPreferredSize().width, this.maxCol * cellWidth);
    //System.out.println(initPanelWidth);
    int initPanelHeight = Math.max(getPreferredSize().height, this.maxRow * cellHeight);

    // Three cell buffer
    gridPanel.setPreferredSize(
            new Dimension(initPanelWidth + 3 * cellWidth, initPanelHeight + 3 * cellHeight));

    this.scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);

    this.add(scrollPane, BorderLayout.CENTER);

    gridPanel.setCells(stringCells);
    gridPanel.setMaxCol(maxCol);
    gridPanel.setMaxRow(maxRow);
    formConfirm.setPreferredSize(new Dimension(45, cellHeight));
    formCancel.setPreferredSize(new Dimension(45, cellHeight));
    this.pack();
    this.revalidate();
    this.resetFocus();
    ready = true;
  }

  @Override
  public void updateView(Coord coord, String cell) {
    this.stringCells.put(coord, cell);
    this.gridPanel.addCell(coord, cell);
    //this.repaint();
  }

  @Override
  public void resetInput() {
    this.formText.setText(this.prevText);
  }

  @Override
  public String getColToAdd() {
    return this.addColField.getText();
  }

  @Override
  public void setColToAdd(String s) {
    this.addColField.setText(s);
  }

  @Override
  public String getRowToAdd() {
    return this.addRowField.getText();
  }

  @Override
  public void setRowToAdd(String s) {
    this.addRowField.setText(s);
  }

  @Override
  public void addFeatures(ControllerFeatures f) {
    this.addActionListener(this.configureButtonListener(f));
    this.addKeyListener(this.configureKeyboardListener(f));
    this.addMouseListener(this.configureMouseListener(f));
  }

  @Override
  public void cellSelectWithKey(Coord coord) {
    this.gridPanel.setSelectedCell(coord);
  }

  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void resizeView(int maxCol, int maxRow) {

    this.maxCol = Math.max(maxCol, this.maxCol);
    this.maxRow = Math.max(maxRow, this.maxRow);

    this.gridPanel.setMaxCol(maxCol);
    this.gridPanel.setMaxRow(maxRow);
  }

  /**
   * Creates an empty MouseListener that does nothing given any mouse event. Used as the default
   * stored mouse event in the view.
   * @return an empty MouseListener.
   */
  private MouseListener defaultMouseListener() {
    return new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {

      }

      @Override
      public void mousePressed(MouseEvent e) {

      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {

      }

      @Override
      public void mouseExited(MouseEvent e) {

      }
    };
  }

  /**
   * Creates a ButtonListener with all the functionality of the given feature passed in.
   * Basically "wires" the buttons in this view to the functionality provided by the controller.
   * @param f the features from the controller.
   * @return a ButtonListener connection the controller to the view
   */
  private ButtonListener configureButtonListener(ControllerFeatures f) {
    ButtonListener btn = new ButtonListener();
    Map<String, Runnable> buttonClickedActions = new HashMap<>();

    // Create new cell from user text input.
    buttonClickedActions.put("confirm input", f::confirmInput);

    // Clear user text input.
    buttonClickedActions.put("clear input", f::clearInput);

    // adds a column
    buttonClickedActions.put("add column", f::addColumn);

    // adds a row
    buttonClickedActions.put("add row", f::addRow);
    btn.setButtonClickedActionMap(buttonClickedActions);
    return btn;
  }

  /**
   * Creates a new MouseEventListener with all the specified functionality that this controller
   * needs. This MouseListener can then be passed into the view so that the view can start listening
   * for those specific events.
   * @param f the features from the Controller
   * @return the configured MouseEventListener.
   */
  private MouseEventListener configureMouseListener(ControllerFeatures f) {
    MouseEventListener mel = new MouseEventListener();
    Map<Integer, MouseRunnable> mouseClickMap = new HashMap<>();

    mouseClickMap.put(MouseEvent.BUTTON1, f::clickOnCellAt);
    //mouseClickMap.put(MouseEvent.BUTTON1, loc -> System.out.println(view.getInputText()));
    //mouseClickMap.put(MouseEvent.BUTTON1, loc -> System.out.println(loc));

    mel.setMouseClickedMap(mouseClickMap);

    return mel;
  }

  /**
   * Creates a new KeyboardListener with all the specified keyboard functionality that this
   * controller needs. This KeyboardListener can then be passed into the view so that the view can
   * start listening for those specific events.
   * @param f the features from the controller.
   * @return the configured KeyboardListener.
   */
  private KeyboardListener configureKeyboardListener(ControllerFeatures f) {
    KeyboardListener kbd = new KeyboardListener();

    Map<Integer, Runnable> keyPressedMap = new HashMap<>();
    Map<Integer, Runnable> keyReleasedMap = new HashMap<>();

    keyPressedMap.put(KeyEvent.VK_UP, () -> f.cellSelectWithKey(0, -1));

    keyPressedMap.put(KeyEvent.VK_DOWN, () -> f.cellSelectWithKey(0, 1));

    keyPressedMap.put(KeyEvent.VK_LEFT, () -> f.cellSelectWithKey(-1, 0));

    keyPressedMap.put(KeyEvent.VK_RIGHT, () -> f.cellSelectWithKey(1, 0));

    keyReleasedMap.put(KeyEvent.VK_UP, () -> f.cellSelectWithKey(0, 0));

    keyReleasedMap.put(KeyEvent.VK_DOWN, () -> f.cellSelectWithKey(0, 0));

    keyReleasedMap.put(KeyEvent.VK_LEFT, () -> f.cellSelectWithKey(0, 0));

    keyReleasedMap.put(KeyEvent.VK_RIGHT, () -> f.cellSelectWithKey(0, 0));

    keyPressedMap.put(KeyEvent.VK_DELETE, f::deleteCellContents);

    kbd.setKeyPressedMap(keyPressedMap);
    kbd.setKeyPressedMap(keyPressedMap);
    kbd.setKeyReleasedMap(keyReleasedMap);
    return kbd;
  }

}
