package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * GUI view for IReadWorkSheetModels.
 */
public class SpreadsheetGUIView extends JFrame implements IView {
    private IReadWorkSheetModel model;
    private JPanel gridPanel;
    private JPanel formulaBarPanel;
    private JTextField formText;
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
                stringCells.put(c, cellResult);
            } catch (IllegalArgumentException e) {
                String msg = e.getMessage();
                //System.out.println(msg + " "+ c.toString());
                if(msg.contains("cycle")) {
                    stringCells.put(c, "#REF!");
                } else if(msg.contains("Formula")) {
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
        gridPanel = new GridPanel(numRow, numCol, cellWidth, cellHeight, stringCells);
        this.scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
        this.scrollPane.setPreferredSize(new Dimension(initPanelWidth + 3 * cellWidth,
                initPanelHeight + 3 * cellHeight));

        // Three cell buffer
        gridPanel.setPreferredSize(
                new Dimension(initPanelWidth + 3 * cellWidth, initPanelHeight + 3 * cellHeight));
        this.add(scrollPane, BorderLayout.CENTER);

        // Add FormulaPanel, currently not editable.
        this.formulaBarPanel = new JPanel();
        formulaBarPanel.setLayout(new FlowLayout());
        formText = new JTextField("Default formula", 20);
        formText.setEditable(false);
        formulaBarPanel.add(formText);
        this.add(formulaBarPanel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowStateListener(we -> createScrollPanel(stringCells));
        this.pack();
    }

    /**
     * Handles the events for when the frame is resized. Removes the scrollPane
     * and recreates it based on the current size of the window.
     * @param stringCells the hashmap of strings for the gridpanel to render.
     */
    private void createScrollPanel(HashMap<Coord, String> stringCells) {
            this.remove(this.scrollPane);
            Dimension currentSize = this.getSize();
            int numRow = this.getPreferredSize().width / cellWidth + 3;
            int numCol = this.getPreferredSize().height / cellHeight + 3;
            int currPanelWidth = Math.max(currentSize.width, model.getMaxCol() * cellWidth);
            int currPanelHeight = Math.max(currentSize.height, model.getMaxRow() * cellHeight);

            gridPanel = new GridPanel(numRow, numCol, cellWidth, cellHeight, stringCells);
            scrollPane = new SpreadsheetScrollingPanel(gridPanel, cellWidth, cellHeight);
            this.scrollPane.setPreferredSize(new Dimension(currPanelWidth + 3 * cellWidth,
                    currPanelHeight + 3 * cellHeight));
            // Three cell buffer
            gridPanel.setPreferredSize(
                    new Dimension(currPanelWidth + 3 * cellWidth, currPanelHeight + 3 * cellHeight));
            this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Renders state of model.
     */
    @Override
    public void render() throws IOException {
        this.repaint();
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
     * Make the view visible. This is usually called
     * after the view is constructed
     */
    @Override
    public void makeVisible() {
        this.setVisible(true);
    }

    /**
     * Provide the view with a callback option to
     * process a command.
     *
     * @param callback object
     */
    @Override
    public void setCommandCallback(Consumer<String> callback) {

    }

    /**
     * Transmit an error message to the view, in case
     * the command could not be processed correctly
     *
     * @param error message.
     */
    @Override
    public void showErrorMessage(String error) {

    }
}
