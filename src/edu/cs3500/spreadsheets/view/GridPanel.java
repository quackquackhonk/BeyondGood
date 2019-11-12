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
    public GridPanel(int numRow, int numCol, int cw, int ch) {
        super();
        /*
        this.rowMin = rowMin;
        this.rowMax = rowMax;
        this.colMax = colMax;
        this.colMin = colMin;
        this.grid = grid;
         */
        this.numRow = numRow;
        this.numCol = numCol;
        this.cw = cw;
        this.ch = ch;
    }

    /**
     * Draws cells.
     *
     * @param g Graphics component
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 1.5));
        int yOffset = (int) (font.getSize() * 1.1);
        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setColor(Color.pink);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
        System.out.println(this.getWidth());

        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                g2d.setColor(Color.black);
                g2d.fillRect(i * cw, j * ch, cw, ch);
                g2d.setColor(Color.pink);
                g2d.drawLine(i * cw, j * ch, i * cw + cw, j * ch);
                g2d.drawLine(i * cw, j * ch + ch, i * cw + cw, j * ch + ch);
                g2d.drawLine(i * cw, j * ch, i * cw, j * ch + ch);
                g2d.drawLine(i * cw + cw, j * ch, i * cw + cw, j * ch + ch);
                g2d.drawString("death.", i * cw + 1, j * ch + yOffset);
            }
        }
        this.revalidate();
    }
}
