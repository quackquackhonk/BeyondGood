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
 * Class to draw the cells and grid of a spreadsheet. Displays cells within a given row/col range
 * and stores currently highlighted cell.
 */
public class GridPanel extends JPanel {

  private HashMap<Coord, String> grid;
  private int numRow;
  private int numCol;
  private int cw;
  private int ch;

  // Column start/end + Row start/end.
  // Only draw within these bounds.

  // Col Start and Col End
  private int cs;
  private int ce;

  // Row Start and Row End
  private int rs;
  private int re;

  // Cell to highlight
  private Coord selectedCell;

  /**
   * Constructs Grid JPanel dimensions.
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
    this.selectedCell = new Coord(1, 1);
  }

  // Change the currently highlighted cell to input Coord.
  void setSelectedCell(Coord c) {
    this.selectedCell = c;
  }

  // Returns Coord of last cell highlighted by the user.
  Coord getSelectedCell() {
    if (this.selectedCell != null) {
      return new Coord(this.selectedCell.col, this.selectedCell.row);
    } else {
      return null;
    }
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

  // Set row boundaries to display
  void setRows(int start, int end) {
    this.rs = start;
    this.re = end;
  }

  // Set MAXIMUM row, the row number of highest cell in the sheet.
  void setMaxRow(int row) {
    this.numRow = row;
  }

  // Get MAXIMUM row, the row number of highest cell in the sheet.
  int getMaxRow() {
    return this.numRow;
  }

  // set MAXIMUM col, the column number of furthest right cell in the sheet.
  void setMaxCol(int col) {
    this.numCol = col;
  }

  // get MAXIMUM col, the column number of furthest right cell in the sheet.
  int getMaxCol() {
    return this.numCol;
  }

  // Get coordinate form mouse location / highlighted cell
  Coord coordFromLoc(int x, int y) {
    int col = x / cw;
    int row = y / ch;

    Coord location = new Coord(col + 1, row + 1);
    this.selectedCell = location;
    return location;
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

    Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(ch / 2));

    // Resize for potential new rows
    Dimension curDim = new Dimension(this.getSize());
    int newX = Math.max(curDim.width, (3 + getMaxCol()) * cw);
    int newY = Math.max(curDim.height, ((3 + getMaxRow()) * ch));
    this.setPreferredSize(new Dimension(newX, newY));

    int yOffset = (int) (font.getSize() * 1.4);
    Dimension prefSize = this.getPreferredSize();
    Graphics2D g2d = (Graphics2D) g;
    g2d.setFont(font);
    g2d.setColor(Color.pink);

    for (int row = Math.max(rs - 10, 0); row < re + 10; row++) {
      for (int col = Math.max(cs - 10, 0); col < ce + 10; col++) {
        String cellText = grid.getOrDefault(new Coord(col + 1, row + 1), "");
        g2d.setColor(Color.black);
        g2d.fillRect(col * cw, row * ch, cw, ch);
        g2d.setColor(Color.pink);
        Color lineColor = Color.pink;

        boolean on = selectedCell != null
                && row == this.selectedCell.row - 1
                && col == this.selectedCell.col - 1;

        boolean right = selectedCell != null
                && row == this.selectedCell.row - 1
                && col == this.selectedCell.col;

        boolean below = selectedCell != null
                && row == this.selectedCell.row
                && col == this.selectedCell.col - 1;

        // Draws top border
        if (on) {
          g2d.setColor(Color.darkGray);
          g2d.fillRect(col * cw, row * ch, cw, ch);
        }

        lineColor = on || below ? Color.white : Color.pink;
        g2d.setColor(lineColor);
        g2d.drawLine(col * cw, row * ch, col * cw + cw, row * ch);

        // Draws left border
        lineColor = on || right ? Color.white : Color.pink;
        g2d.setColor(lineColor);
        g2d.drawLine(col * cw, row * ch, col * cw, row * ch + ch);

        lineColor = on ? Color.white : Color.pink;
        g2d.setColor(lineColor);
        g2d.drawString(cellText, col * cw + 3, row * ch + yOffset);
        g2d.drawLine(col * cw, row * ch + ch, col * cw + cw, row * ch + ch);
        g2d.drawLine(col * cw + cw, row * ch, col * cw + cw, row * ch + ch);
      }
    }
    this.revalidate();
  }

  void addCell(Coord coord, String cell) {
    this.grid.put(coord, cell);
  }
}
