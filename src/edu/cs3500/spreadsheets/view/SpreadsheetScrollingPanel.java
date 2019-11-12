package edu.cs3500.spreadsheets.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class SpreadsheetScrollingPanel extends JPanel {

    private final int cellWidth;
    private final int cellHeight;

    private JScrollBar verticalScroll = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
    private JScrollBar horizontalScroll = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);

    private ScrollingViewPort gridViewport = new ScrollingViewPort();
    private JComponent grid;

    public SpreadsheetScrollingPanel(JPanel view, int cellWidth, int cellHeight) {

        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;

        setLayout(null);

        add(this.gridViewport);

        this.grid = view;
        this.gridViewport.add(this.grid);

        this.verticalScroll.setUnitIncrement(cellHeight);
        this.add(this.verticalScroll);
        this.horizontalScroll.setUnitIncrement(cellWidth);
        this.add(this.horizontalScroll);

        AdjustmentListener scrollListener = new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                gridViewport.doLayout();
            }
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
        gridViewport.setBounds(0, 0, width, height);
        this.verticalScroll.setBounds(width + 1, 0, verticalBarSize.width, height);
        this.horizontalScroll.setBounds(0, height + 1, width, horizontalBarSize.height);

        int maxWidth = Math.max(gridSize.width - width, 0);
        this.horizontalScroll.setMaximum(maxWidth);
        this.horizontalScroll.setBlockIncrement(maxWidth / 5);
        this.horizontalScroll.setEnabled(maxWidth > 0);

        int maxHeight = Math.max(gridSize.height - height, 0);
        this.verticalScroll.setMaximum(maxHeight);
        this.verticalScroll.setBlockIncrement(maxHeight / 5);
        this.verticalScroll.setEnabled(maxHeight > 0);

        this.horizontalScroll.setVisibleAmount(this.horizontalScroll.getBlockIncrement());
        this.verticalScroll.setVisibleAmount(this.verticalScroll.getBlockIncrement());

    }

    @Override
    public void paintComponent(Graphics g) {

    }

    public class ScrollingViewPort extends JPanel {

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
}
