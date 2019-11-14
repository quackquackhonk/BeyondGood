package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Interface for views of IWorkSheetModels.
 */
public interface IView {
    /**
     * Renders state of model.
     */
    void render() throws IOException;

    /**
     * Make the view visible. This is usually called
     * after the view is constructed
     */
    void makeVisible();

    /**
     * Provide the view with a callback option to
     * process a command.
     *
     * @param callback object
     */
    void setCommandCallback(Consumer<String> callback);

    /**
     * Transmit an error message to the view, in case
     * the command could not be processed correctly.
     *
     * @param error message.
     */
    void showErrorMessage(String error);
}
