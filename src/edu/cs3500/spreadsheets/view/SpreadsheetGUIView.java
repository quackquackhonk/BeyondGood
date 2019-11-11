package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.IWorkSheetModel;

import javax.swing.*;
import java.io.IOException;
import java.util.function.Consumer;

public class SpreadsheetGUIView extends JFrame implements IView {
    private IWorkSheetModel model;
    private JPanel grid;
    private JPanel formulaBar;







    /**
     * Renders state of model.
     */
    @Override
    public void render() throws IOException {

    }

    /**
     * Make the view visible. This is usually called
     * after the view is constructed
     */
    @Override
    public void makeVisible() {

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
