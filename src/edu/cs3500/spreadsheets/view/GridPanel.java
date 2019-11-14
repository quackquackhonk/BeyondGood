package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
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

  // Column start/end + Row start/end.
  // Only draw within these bounds.
  private int cs;
  private int ce;
  private int rs;
  private int re;

  /**
   * Constructs GUI JPanel dimensions.
   */
  public GridPanel(int numRow, int numCol, int cw, int ch, HashMap<Coord, String> cells,
      int colStart, int colEnd, int rowStart, int rowEnd) {
    super();
    this.grid = cells;
    this.numRow = numRow;
    this.numCol = numCol;
    this.cw = cw;
    this.ch = ch;
    this.ce = colEnd;
    this.cs = colStart;
    this.rs = rowStart;
    this.re = rowEnd;
  }

  // Display cells starting at this column.
  int getColStart() {
    return this.cs;
  }

  // Display cells until this column.
  int getColEnd() {
    return this.ce;
  }

  // Display cells starting at this row.
  int getRowStart() {
    return this.rs;
  }

  // Display cells ending at this row.
  int getRowEnd() {
    return this.re;
  }

  // Set column boundaries.
  void setCols(int start, int end) {
    this.cs = start;
    this.ce = end;
  }

  // Set row boundaries.
  void setRows(int start, int end) {
    this.rs = start;
    this.re = end;
  }

  /**
   * Sets the HashMap of Coord -> String that this Panel uses to render cell text to cells.
   *
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

    numRow = this.getPreferredSize().height / ch + 3;
    numCol = this.getPreferredSize().width / cw + 3;

    Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 2));
    int yOffset = (int) (font.getSize() * 1.4);
    Dimension prefSize = this.getPreferredSize();
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(font);
    g2d.setColor(Color.pink);

    for (int row = rs; row < re; row++) {
      for (int col = cs; col < ce; col++) {
        String cellText = grid.getOrDefault(new Coord(col + 1, row + 1), "");
        g2d.setColor(Color.black);
        g2d.fillRect(col * cw, row * ch, cw, ch);
        g2d.setColor(Color.pink);
        g2d.drawLine(col * cw, row * ch, col * cw + cw, row * ch);
        g2d.drawLine(col * cw, row * ch + ch, col * cw + cw, row * ch + ch);
        g2d.drawLine(col * cw, row * ch, col * cw, row * ch + ch);
        g2d.drawLine(col * cw + cw, row * ch, col * cw + cw, row * ch + ch);
        g2d.drawString(cellText, col * cw + 3, row * ch + yOffset);
      }
    }
    this.revalidate();
  }
}
