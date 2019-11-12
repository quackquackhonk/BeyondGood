package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.SpreadsheetGUIView;
import edu.cs3500.spreadsheets.view.SpreadsheetTextualView;

/**
 * Instantiate new IViews.
 */
public class ViewCreator {

    /**
     * Returns a PyramidSolitaireModel class based on the given GameType enum.
     *
     * @param viewType enum representing desired game model
     * @return corresponding PyramidSolitaireModel
     */
    public static IView create(ViewType viewType, IReadWorkSheetModel model) {
        IView view = null;
        switch (viewType) {
            case GUI:
                view = new SpreadsheetGUIView(model);
                break;
            default:
                throw new IllegalArgumentException("This isn't even possible lol");
        }
        return view;
    }

    /**
     * Returns a PyramidSolitaireModel class based on the given GameType enum.
     *
     * @param viewType enum representing desired game model
     * @return corresponding PyramidSolitaireModel
     */
    public static IView create(ViewType viewType, IReadWorkSheetModel model, Appendable out) {
        IView view = null;
        if (viewType == ViewType.TEXT) {
            view = new SpreadsheetTextualView(model, out);
        } else {
            throw new IllegalArgumentException("This isn't even possible lol");
        }
        return view;
    }

    /**
     * Enum to represent the three possible game modes.
     */
    public enum ViewType {
        TEXT(), GUI();
    }

}
