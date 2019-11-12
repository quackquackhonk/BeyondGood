package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * GUI JPanel for rendering cells in a spreadsheet.
 */
public class GridPanel extends JPanel {
    private int rowMin;
    private int rowMax;
    private int colMax;
    private int colMin;
    private HashMap<Coord, String> grid;

    /**
     * Constructs GUI JPanel
     *mensionsensions
     */
    public GridPanel() {
        super();
        rowMin = rowMin;
        rowMax = rowMax;
        colMax = colMax;
        colMin = colMin;
        grid = grid;
    }

    /**
     * Draws cells.
     * @param g Graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.pink);
        this.revalidate();
        g2d.fillRect(0, 0, prefSize.width,prefSize.height);
    }
}
