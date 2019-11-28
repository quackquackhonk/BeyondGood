package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.AdjustmentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

/**
 * Custom ScrollPane for spreadsheets. Contains the GridPanel, scrollbars, and col/row headers.
 */
public class SpreadsheetScrollingPanel extends JPanel {

  private final int cellWidth;
  private final int cellHeight;
  private final int SCROLL_BAR_THICKNESS = 30;

  private JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
  private JScrollBar horizontalScroll = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);

  private ScrollingViewPort gridViewport;
  private GridPanel grid;
  private JPanel colHead;
  private JPanel rowHead;
  private Coord selectedCell;

  /**
   * Constructs custom scroll panel.
   *
   * @param view       grid view to move around based on scroll and window events.
   * @param cellWidth  width of a rendered cell
   * @param cellHeight height of a rendered cell
   */
  public SpreadsheetScrollingPanel(GridPanel view, int cellWidth, int cellHeight) {
    super();

    this.cellWidth = cellWidth;
    this.cellHeight = cellHeight;
    this.selectedCell = null;

    this.setLayout(new GridBagLayout());
    this.grid = view;
    this.gridViewport = new ScrollingViewPort();
    this.gridViewport.add(this.grid);
    this.verticalScroll.setUnitIncrement(cellHeight);
    this.horizontalScroll.setUnitIncrement(cellWidth);
    this.colHead = new SpreadsheetScrollingColumnHeader();
    this.rowHead = new SpreadsheetScrollingRowHeader();
    JPanel topLeftCorner = new JPanel();
    topLeftCorner.setBackground(Color.pink);
    topLeftCorner.setPreferredSize(new Dimension(cellWidth, cellHeight));
    topLeftCorner.setSize(topLeftCorner.getPreferredSize());
    JPanel bottomRightCorner = new JPanel();
    bottomRightCorner.setPreferredSize(
            new Dimension(SCROLL_BAR_THICKNESS, SCROLL_BAR_THICKNESS));
    bottomRightCorner.setSize(bottomRightCorner.getPreferredSize());

    GridBagConstraints tlcc = new GridBagConstraints();
    tlcc.gridx = 0;
    tlcc.gridy = 0;
    tlcc.weighty = 1.0;
    tlcc.weightx = 1.0;
    this.add(topLeftCorner, tlcc);

    GridBagConstraints brcc = new GridBagConstraints();
    brcc.gridx = 2;
    brcc.gridy = 2;
    brcc.weighty = 0.0;
    brcc.weightx = 0.0;
    this.add(bottomRightCorner, brcc);

    GridBagConstraints vsbc = new GridBagConstraints();
    vsbc.gridx = 2;
    vsbc.gridy = 1;
    vsbc.weighty = 0.0;
    vsbc.weightx = 0.0;
    this.add(this.verticalScroll, vsbc);

    GridBagConstraints hsbc = new GridBagConstraints();
    hsbc.gridx = 1;
    hsbc.gridy = 2;
    hsbc.weighty = 0.0;
    hsbc.weightx = 0.0;
    this.add(this.horizontalScroll, hsbc);

    GridBagConstraints chc = new GridBagConstraints();
    chc.gridx = 1;
    chc.gridy = 0;
    chc.weightx = .5;
    chc.fill = GridBagConstraints.HORIZONTAL;
    this.add(this.colHead, chc);

    GridBagConstraints rhc = new GridBagConstraints();
    rhc.gridx = 0;
    rhc.weighty = .5;
    rhc.fill = GridBagConstraints.VERTICAL;
    rhc.gridy = 1;
    this.add(this.rowHead, rhc);

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 1;
    gc.weightx = 1.0;
    gc.weighty = 1.0;
    gc.gridy = 1;
    gc.fill = GridBagConstraints.BOTH;
    this.add(this.gridViewport, gc);

    AdjustmentListener scrollListener = e -> {
      gridViewport.doLayout();
      colHead.doLayout();
      rowHead.doLayout();
    };
    this.verticalScroll.addAdjustmentListener(scrollListener);
    this.horizontalScroll.addAdjustmentListener(scrollListener);
  }

  /**
   * Sets the bounds and layout for the grid, viewport, and two scrollbars.
   */
  public void doLayout() {
    Dimension size = getSize();
    Dimension gridSize = grid.getPreferredSize();
    Dimension verticalBarSize = this.verticalScroll.getPreferredSize();
    Dimension horizontalBarSize = this.horizontalScroll.getPreferredSize();
    int width = Math.max(size.width - verticalBarSize.width - 1, 0);
    int height = Math.max(size.height - horizontalBarSize.height - 1, 0);
    gridViewport.setBounds(cellWidth, cellHeight,
            width - SCROLL_BAR_THICKNESS, height - SCROLL_BAR_THICKNESS);
    this.verticalScroll.setBounds(width, cellHeight,
            verticalBarSize.width, height - SCROLL_BAR_THICKNESS);
    this.horizontalScroll.setBounds(cellWidth, height + 2,
            size.width - (3 * SCROLL_BAR_THICKNESS) - 5, horizontalBarSize.height);

    int maxWidth = Math.max(gridSize.width, 0);
    this.horizontalScroll.setMaximum(
            gridSize.width - size.width + horizontalScroll.getVisibleAmount());
    this.horizontalScroll.setBlockIncrement(maxWidth / 5);
    this.horizontalScroll.setEnabled(maxWidth > 0);

    int maxHeight = Math.max(gridSize.height, 0);
    this.verticalScroll.setMaximum(
            gridSize.height - size.height + verticalScroll.getVisibleAmount());
    this.verticalScroll.setBlockIncrement(maxHeight / 5);
    this.verticalScroll.setEnabled(maxHeight > 0);

    this.horizontalScroll.setVisibleAmount(this.horizontalScroll.getBlockIncrement());
    this.verticalScroll.setVisibleAmount(this.verticalScroll.getBlockIncrement());
  }

  // Returns the Coord based on mouse position
  Coord coordFromLoc(int x, int y) {
    return this.grid.coordFromLoc(x, y);
  }

  /**
   * Viewport for the client. Moves grid based on scroll and window resize events.
   */
  protected class ScrollingViewPort extends JPanel {

    /**
     * Constructs ScrollingViewPort.
     */
    public ScrollingViewPort() {
      setLayout(null);
    }

    /**
     * Recalculates layout based on scroll event. Moves grid accordingly.
     */
    public void doLayout() {
      int x = horizontalScroll.getValue();
      int y = verticalScroll.getValue();
      Dimension gridSize = new Dimension(grid.getPreferredSize());

      // Resize the grid
      if (gridSize.width - cellWidth * 3 < SpreadsheetScrollingPanel.this.getWidth()) {
        double newWidth = SpreadsheetScrollingPanel.this.getWidth() + cellWidth * 3;
        gridSize.setSize(newWidth, gridSize.height);
        System.out.println("changed size in scroll panel");
      }

      if (gridSize.height - cellHeight * 3 < SpreadsheetScrollingPanel.this.getHeight()) {
        double newHeight = SpreadsheetScrollingPanel.this.getHeight() + cellHeight * 3;
        gridSize.setSize(gridSize.width, newHeight);
        System.out.println("changed size in scroll panel");
      }


      int colEnd = (-grid.getBounds().x + this.getWidth()) / cellWidth + 3;
      int colStart = -grid.getBounds().x / cellWidth;

      int rowEnd = (-grid.getBounds().y + this.getHeight()) / cellHeight + 3;
      int rowStart = -grid.getBounds().y / cellHeight;

      //System.out.println(grid.getColEnd() > colEndCur);
      grid.setCols(colStart, colEnd);
      grid.setRows(rowStart, rowEnd);

      grid.setPreferredSize(gridSize);

      grid.setBounds(-x, -y, gridSize.width,
              gridSize.height);
    }
  }

  /**
   * Sticky column header.
   */
  public class SpreadsheetScrollingColumnHeader extends JPanel {

    /**
     * Constructs sticky column header.
     */
    public SpreadsheetScrollingColumnHeader() {
      super();
      this.setPreferredSize(new Dimension(grid.getPreferredSize().width, cellHeight));
      setLayout(null);
      this.setSize(this.getPreferredSize());
      //this.repaint();
    }

    /**
     * Calculates bounds based on scroll event.
     */
    public void doLayout() {
      int x = horizontalScroll.getValue();
      this.setBounds(-x + cellWidth, this.getY(), this.getWidth(), this.getHeight());
    }

    /**
     * Draw this component.
     *
     * @param g Graphics to draw
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.setSize(new Dimension(grid.getWidth(), cellHeight));
      Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(cellHeight / 2.4));
      int yOffset = (int) (font.getSize() * 1.65);
      Graphics2D g2d = (Graphics2D) g;
      int numCols = grid.getWidth() / cellWidth;
      g2d.setFont(font);
      g2d.setColor(Color.pink);
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2d.setColor(Color.black);
      for (int col = 0; col < numCols + 1; col++) {
        g2d.drawLine(col * cellWidth, 0, col * cellWidth, cellHeight);
        g2d.drawLine(col * cellWidth, cellHeight, col * cellWidth, cellHeight);
        String colName = Coord.colIndexToName(col + 1);
        g2d.drawString(colName, col * cellWidth + 3, yOffset);
      }
      this.revalidate();
    }
  }

  /**
   * Sticky row side header. Scrolls with grid vertically but not horizontally.
   */
  public class SpreadsheetScrollingRowHeader extends JPanel {

    /**
     * Constructs sticky rowheader.
     */
    public SpreadsheetScrollingRowHeader() {
      super();
      this.setPreferredSize(new Dimension(cellWidth, grid.getPreferredSize().height));
      setLayout(null);
      this.setSize(this.getPreferredSize());
      //this.repaint();
    }

    /**
     * Recalculates bounds based on scroll event.
     */
    public void doLayout() {
      int y = verticalScroll.getValue();
      this.setBounds(this.getX(), -y + cellHeight, this.getWidth(), this.getHeight());
    }

    /**
     * Renders this component.
     *
     * @param g Graphics used to render component
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.setSize(new Dimension(cellWidth, grid.getHeight()));
      Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(cellHeight / 2.4));
      int yOffset = (int) (font.getSize() * 1.65);
      Graphics2D g2d = (Graphics2D) g;
      int numRows = grid.getHeight() / cellHeight;
      g2d.setFont(font);
      g2d.setColor(Color.pink);
      g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
      g2d.setColor(Color.black);
      for (int row = 0; row < numRows + 1; row++) {
        // g2d.drawLine(0, row * cellHeight+cellHeight, cellWidth, row * cellHeight+cellHeight);
        g2d.drawLine(0, row * cellHeight, cellWidth, row * cellHeight);
        g2d.drawString(Integer.toString(row + 1), 3, row * cellHeight + yOffset);
      }
      this.revalidate();
    }
  }
}
