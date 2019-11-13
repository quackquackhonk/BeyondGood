package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * GUI JPanel for rendering cells in a spreadsheet.
 */
public class GridPanel extends JPanel {
    /*
    private int rowMin;
    private int rowMax;
    private int colMax;
    private int colMin;

     */
    private HashMap<Coord, String> grid;
    private int numRow;
    private int numCol;
    private int cw;
    private int ch;

    /**
     * Constructs GUI JPanel
     * mensionsensions
     */
    public GridPanel(int numRow, int numCol, int cw, int ch, HashMap<Coord, String> cells) {
        super();
        /*
        this.rowMin = rowMin;
        this.rowMax = rowMax;
        this.colMax = colMax;
        this.colMin = colMin;
        this.grid = grid;
         */
        this.grid = cells;
        this.numRow = numRow;
        this.numCol = numCol;
        this.cw = cw;
        this.ch = ch;
    }

    /**
     * Sets the HashMap of Coord -> String that this Panel uses to render cell text to cells.
     * @param cells the new HashMap
     */
    public void setCells(HashMap<Coord, String> cells) {
        this.grid = cells;
    }

    /**
     * Draws cells.
     *
     * @param g Graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        numRow = this.getPreferredSize().width / cw + 3;
        numCol = this.getPreferredSize().height / ch + 3;

        Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 2));
        int yOffset = (int) (font.getSize() * 1.4);
        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setColor(Color.pink);
        g2d.fillRect(0, 0, this.getPreferredSize().width, this.getPreferredSize().height);
        System.out.println(this.getPreferredSize() + " grids pref size");

        for (int row = 0; row < numRow; row++) {
            for (int col = 0; col < numCol; col++) {

                String cellText = grid.getOrDefault(new Coord(row + 1, col + 1), "");
                g2d.setColor(Color.black);
                g2d.fillRect(row * cw, col * ch, cw, ch);
                g2d.setColor(Color.pink);
                g2d.drawLine(row * cw, col * ch, row * cw + cw, col * ch);
                g2d.drawLine(row * cw, col * ch + ch, row * cw + cw, col * ch + ch);
                g2d.drawLine(row * cw, col * ch, row * cw, col * ch + ch);
                g2d.drawLine(row * cw + cw, col * ch, row * cw + cw, col * ch + ch);
                g2d.drawString(cellText, row * cw + 3, col * ch + yOffset);
            }
        }
        this.revalidate();
    }
}
