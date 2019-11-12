package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * GUI view for IReadWorkSheetModels.
 */
public class SpreadsheetGUIView extends JFrame implements IView {
    private IReadWorkSheetModel model;
    private JPanel gridPanel;
    private JPanel formulaBarPanel;
    private JTextField formText;
    private JPanel colHeadPanel;
    private JPanel rowHeadPanel;
    private int rowMin;
    private int rowMax;
    private int colMax;
    private int colMin;

    /**
     * Constructs a GUI view for IReadWorkSheetModel.
     */
    public SpreadsheetGUIView(IReadWorkSheetModel model) {
        super();
        this.model = model;
        this.setTitle("Beyond gOOD Editor");
        this.rowMin = 0;
        this.colMin = 0;
        this.rowMax = this.model.getRowWidth();
        this.colMax = this.model.getColHeight();

        this.setLayout(new BorderLayout());
        this.setSize(this.getPreferredSize());
        gridPanel = new GridPanel();
        gridPanel.setLayout(new GridLayout(10,10));
        gridPanel.setPreferredSize(new Dimension(500, 500));
        this.add(gridPanel, BorderLayout.CENTER);

        this.formulaBarPanel = new JPanel();
        formulaBarPanel.setLayout(new FlowLayout());
        formText = new JTextField( "Default formula",20);
        formText.setEditable(false);
        formulaBarPanel.add(formText);
        this.add(formulaBarPanel, BorderLayout.NORTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
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
     * @return dimension
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000,600);
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
