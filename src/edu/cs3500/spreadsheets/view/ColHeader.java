package edu.cs3500.spreadsheets.view;

import javax.swing.*;
import java.awt.*;

public class ColHeader extends JPanel {
    private int cw;
    private int ch;
    private int nc;
    /**
     * Constructs RowHeader.
     */
    public ColHeader(int cw, int ch, int numCol) {
        super();
        this.cw = cw;
        this.ch = ch;
        this.nc = numCol;
    }

    /**
     * Draws row header.
     *
     * @param g Graphics to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
    }
}
