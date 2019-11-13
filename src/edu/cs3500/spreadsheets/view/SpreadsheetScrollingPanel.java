package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.model.Coord;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class SpreadsheetScrollingPanel extends JPanel {

    private final int cellWidth;
    private final int cellHeight;
    // this is a hardcoded value, and I don't know if it is dependent on the screen size or screen resolution
    // (it probably is ).
    private final int SCROLL_BAR_THICKNESS = 30;

    private JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
    private JScrollBar horizontalScroll = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);

    private ScrollingViewPort gridViewport = new ScrollingViewPort();
    private JComponent grid;
    private JPanel colHead;
    private JPanel rowHead;

    public SpreadsheetScrollingPanel(JPanel view, int cellWidth, int cellHeight) {

        super();

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

//        this.setLayout(new BorderLayout());
//        this.grid = view;
//        this.gridViewport.add(this.grid);
//
//        this.verticalScroll.setUnitIncrement(cellHeight);
//        this.horizontalScroll.setUnitIncrement(cellWidth);
//
//        this.colHead = new SpreadsheetScrollingColumnHeader();
//        this.rowHead = new SpreadsheetScrollingRowHeader();
//        this.add(this.verticalScroll, BorderLayout.EAST);
//        this.add(this.horizontalScroll, BorderLayout.SOUTH);
//        this.add(this.colHead, BorderLayout.NORTH);
//        this.add(this.rowHead, BorderLayout.WEST);
//        this.add(this.gridViewport, BorderLayout.CENTER);

        this.setLayout(new GridBagLayout());
        this.grid = view;
        this.gridViewport.add(this.grid);
        this.verticalScroll.setUnitIncrement(cellHeight);
        this.horizontalScroll.setUnitIncrement(cellWidth);
        this.colHead = new SpreadsheetScrollingColumnHeader();
        this.rowHead = new SpreadsheetScrollingRowHeader();
        // corners
        JPanel topLeftCorner = new JPanel();
        topLeftCorner.setPreferredSize(new Dimension(cellWidth, cellHeight));
        topLeftCorner.setSize(topLeftCorner.getPreferredSize());
        JPanel bottomRightCorner = new JPanel();
        bottomRightCorner.setPreferredSize(new Dimension(SCROLL_BAR_THICKNESS, SCROLL_BAR_THICKNESS));
        bottomRightCorner.setSize(bottomRightCorner.getPreferredSize());

        GridBagConstraints tlcc = new GridBagConstraints();
        tlcc.gridx = 0;
        tlcc.gridy = 0;
        this.add(topLeftCorner, tlcc);

        GridBagConstraints brcc = new GridBagConstraints();
        brcc.gridx = 2;
        brcc.gridy = 2;
        this.add(bottomRightCorner, brcc);

        GridBagConstraints vsbc = new GridBagConstraints();
        vsbc.gridx = 2;
        vsbc.gridy = 1;
        this.add(this.verticalScroll, vsbc);

        GridBagConstraints hsbc = new GridBagConstraints();
        hsbc.gridx = 1;
        hsbc.gridy = 2;
        this.add(this.horizontalScroll, hsbc);

        GridBagConstraints chc = new GridBagConstraints();
        chc.gridx = 1;
        chc.gridy = 0;
        this.add(this.colHead, chc);

        GridBagConstraints rhc = new GridBagConstraints();
        rhc.gridx = 0;
        rhc.gridy = 1;
        this.add(this.rowHead, rhc);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 1;
        this.add(this.gridViewport, gc);


        AdjustmentListener scrollListener = e -> {
            gridViewport.doLayout();
            colHead.doLayout();
            rowHead.doLayout();
        };
        this.verticalScroll.addAdjustmentListener(scrollListener);
        this.horizontalScroll.addAdjustmentListener(scrollListener);
    }

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
                size.width - 2 * SCROLL_BAR_THICKNESS, horizontalBarSize.height);

        int maxWidth = Math.max(gridSize.width, 0);
        this.horizontalScroll.setMaximum(gridSize.width - size.width + horizontalScroll.getVisibleAmount());
        this.horizontalScroll.setBlockIncrement(maxWidth / 5);
        this.horizontalScroll.setEnabled(maxWidth > 0);

        int maxHeight = Math.max(gridSize.height, 0);
        this.verticalScroll.setMaximum(gridSize.height - size.height + verticalScroll.getVisibleAmount());
        this.verticalScroll.setBlockIncrement(maxHeight / 5);
        this.verticalScroll.setEnabled(maxHeight > 0);

        this.horizontalScroll.setVisibleAmount(this.horizontalScroll.getBlockIncrement());
        this.verticalScroll.setVisibleAmount(this.verticalScroll.getBlockIncrement());

    }


    protected class ScrollingViewPort extends JPanel {

        public ScrollingViewPort() {
            setLayout(null);
        }


        public void doLayout() {
            Dimension innerComponentSize = grid.getPreferredSize();
            int x = horizontalScroll.getValue();
            int y = verticalScroll.getValue();
            grid.setBounds(-x, -y, innerComponentSize.width,
                    innerComponentSize.height);
        }
    }

    public class SpreadsheetScrollingColumnHeader extends JPanel {

        public SpreadsheetScrollingColumnHeader() {
            super();
            this.setPreferredSize(new Dimension(grid.getPreferredSize().width, cellHeight));
            setLayout(null);
            this.setSize(this.getPreferredSize());
            this.repaint();
        }

        public void doLayout() {
            int x = horizontalScroll.getValue();
            this.setBounds(-x + cellWidth, this.getY(), this.getWidth(), this.getHeight());
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setSize(new Dimension(grid.getWidth(), cellHeight));
            Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(cellHeight / 2.0));
            int yOffset = (int) (font.getSize() * 1.1);
            Graphics2D g2d = (Graphics2D) g;
            int numCols = grid.getWidth() / cellWidth;
            g2d.setFont(font);
            g2d.setColor(Color.pink);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.black);
            for (int col = 0; col < numCols + 1; col++) {
                g2d.drawLine(col * cellWidth, 0, col * cellWidth, cellHeight);
                String colName = Coord.colIndexToName(col + 1);
                g2d.drawString(colName, col * cellWidth + 1, yOffset);
            }
            this.revalidate();
        }
    }

    public class SpreadsheetScrollingRowHeader extends JPanel {

        public SpreadsheetScrollingRowHeader() {
            super();
            this.setPreferredSize(new Dimension(cellWidth, grid.getPreferredSize().height));
            setLayout(null);
            this.setSize(this.getPreferredSize());
            this.repaint();
        }

        public void doLayout() {
            int y = verticalScroll.getValue();
            this.setBounds(this.getX(), -y + cellHeight, this.getWidth(), this.getHeight());
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setSize(new Dimension(cellWidth, grid.getHeight()));
            Font font = new Font("Arial", Font.PLAIN, (int) Math.floor(cellHeight / 2.0));
            int yOffset = (int) (font.getSize() * 1.1);
            Graphics2D g2d = (Graphics2D) g;
            int numRows = grid.getHeight() / cellHeight;
            g2d.setFont(font);
            g2d.setColor(Color.pink);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.black);
            for (int row = 0; row < numRows + 1; row++) {
                g2d.drawLine(0, row * cellHeight, cellWidth, row * cellHeight);
                g2d.drawString(Integer.toString(row + 1), 1, row * cellHeight + yOffset);
            }
            this.revalidate();
        }

    }
}
