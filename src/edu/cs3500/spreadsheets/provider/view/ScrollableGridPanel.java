package edu.cs3500.spreadsheets.provider.view;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JScrollBar;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.model.Coord;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;

/**
 * To represent a GridPanel with both horizontal and vertical sliders to make the spreadsheet
 * scrollable.
 */
public class ScrollableGridPanel extends javax.swing.JPanel {


  private SpreadsheetModel model;

  private JScrollBar vBar;
  private JScrollBar hBar;

  private GridPanel gPanel;

  /**
   * To construct the ScrollableGridPanel. To initialize and set the value to the panel and
   * sliders.
   *
   * @param model the spreadsheet model.
   */
  public ScrollableGridPanel(ViewOnlyModel model) {
    this.model = model;

    this.gPanel = new GridPanel(model);

    this.setLayout(new BorderLayout());
    this.add(gPanel, BorderLayout.CENTER);


    vBar = new JScrollBar(JScrollBar.VERTICAL, 0, 50, 0, 500);

    hBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 50, 0, 500);


    AdjustmentListener hListener = e -> {
      gPanel.setH(hBar.getValue());

      if (hBar.getValue() + hBar.getVisibleAmount() == hBar.getMaximum()) {

        hBar.setMaximum(hBar.getMaximum() + 1);
      }

      this.repaint();
    };

    hBar.addAdjustmentListener(hListener);


    AdjustmentListener vListener = e -> {
      gPanel.setV(vBar.getValue());

      if (vBar.getValue() + vBar.getVisibleAmount() == vBar.getMaximum()) {

        vBar.setMaximum(vBar.getMaximum() + 1);
      }

      this.repaint();
    };

    vBar.addAdjustmentListener(vListener);


    MouseWheelListener l = e -> {
      if (e.isShiftDown()) {
        if (e.getWheelRotation() < 0) {
          hBar.setValue(hBar.getValue() - 1);
        } else {
          hBar.setValue(hBar.getValue() + 1);
        }
      } else {
        if (e.getWheelRotation() < 0) {
          vBar.setValue(vBar.getValue() - 1);
        } else {
          vBar.setValue(vBar.getValue() + 1);
        }
      }


    };

    this.addMouseWheelListener(l);


    this.add(vBar, BorderLayout.EAST);
    this.add(hBar, BorderLayout.SOUTH);


  }


  protected void setHighlightCoord(Coord c) {
    gPanel.setHighlightCoord(c);
  }



  protected void addMouseListener(Features f) {
    gPanel.addMouseListener(f);
  }

}
