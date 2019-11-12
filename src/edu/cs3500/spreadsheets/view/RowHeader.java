package edu.cs3500.spreadsheets.view;

import javax.swing.*;
import java.awt.*;

/**
 * Row header for GUI view.
 */
public class RowHeader extends JPanel {
    private int cw;
    private int ch;
    private int nr;

    /**
     * Constructs RowHeader.
     */
    public RowHeader(int cw, int ch, int numRow) {
        super();
        this.cw = cw;
        this.ch = ch;
        this.nr = numRow;
    }

    /**
     * Draws row header.
     *
     * @param g Graphics to draw on.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 1.5));
        int yOffset = (int) (font.getSize() * 1.1);
        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);


        for (int i = 0; i < nr; i++) {
            g2d.setColor(Color.black);
            g2d.fillRect(i * cw, 10 * ch, cw, ch);

            g2d.drawLine(i * cw, 10 * ch, i * cw + cw, 10 * ch);
            g2d.drawLine(i * cw, 10 * ch + ch, i * cw + cw, 10 * ch + ch);
            g2d.drawLine(i * cw, 10 * ch, i * cw, 10 * ch + ch);
            g2d.drawLine(i * cw + cw, 10 * ch, i * cw + cw, 10 * ch + ch);

            System.out.println(i);
        }
        this.revalidate();
    }
}
