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
        Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 3));
        int yOffset = (int) (font.getSize() * 1.1);
        Dimension prefSize = this.getPreferredSize();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);
        g2d.setColor(Color.black);
        g2d.fillRect(0,0, cw, this.getHeight());

        for (int i = 0; i < nr+10; i++) {
            g2d.setColor(Color.black);
            g2d.fillRect(cw, 10 * ch, cw, ch);

            g2d.setColor(Color.white);
            g2d.drawString(Integer.toString(i), 1, i * ch + yOffset);
            g2d.drawLine(0, i * ch, cw, i * ch);
            g2d.drawLine(0, i * ch + ch, cw, i * ch + ch);
            g2d.drawLine(0,i*ch,0,i*ch+ch);
            g2d.drawLine(cw, i * ch, cw, i * ch + ch);

            System.out.println(i);
        }
        this.revalidate();
    }
}
