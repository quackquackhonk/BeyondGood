package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IReadWorkSheetModel;

import java.io.IOException;
import java.util.HashSet;
import java.util.function.Consumer;

/**
 * Textual view for ISpreadSheetModels.
 */
public class SpreadsheetTextualView implements IView {
    IReadWorkSheetModel model;
    Appendable out;

    /**
     * Constructs a view class.
     *
     * @param model model to render
     * @param out   render to this.
     */
    public SpreadsheetTextualView(IReadWorkSheetModel model, Appendable out) {
        this.model = model;
        this.out = out;
    }

    /**
     * Renders state of model.
     */
    @Override
    public void render() throws IOException {
        HashSet<Coord> allCells = model.activeCells();
        for (Coord c : allCells) {
            String cellText = model.getCellText(c);
            System.out.println(cellText);
            if (!cellText.equals("")) {
                String toAdd = c.toString() + " " + cellText;
                out.append(toAdd);
                out.append("\n");
            }
        }
    }

    /**
     * Make the view visible. This is usually called
     * after the view is constructed
     */
    @Override
    public void makeVisible() {
        // Not necessary in textual view
    }

    /**
     * Provide the view with a callback option to
     * process a command.
     *
     * @param callback object
     */
    @Override
    public void setCommandCallback(Consumer<String> callback) {
        // Stub without controller
    }

    /**
     * Transmit an error message to the view, in case
     * the command could not be processed correctly
     *
     * @param error message.
     */
    @Override
    public void showErrorMessage(String error) {
        // Stub without controller
    }
}
