package edu.cs3500.spreadsheets.provider.view;


import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.Coord;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Stroke;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.provider.view.listener.ButtonListener;

/**
 * To represent a Grid Panel of a spread sheet model without slides.
 */
public final class GridPanel extends javax.swing.JPanel {


  private final int cellWidth = 75;
  private final int cellHeight = 25;

  private int x = 0;
  private int y = 0;

  SpreadsheetModel model;

  Coord hightlightCoord;

  /**
   * To construct the GridPanel and set the size of the spread sheet. * @param model
   * SpreadsheetModel
   */
  public GridPanel(ViewOnlyModel model) {
    this.model = model;

    this.setPreferredSize(new Dimension(1500, 750));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;


    int gridWidth = this.getWidth() / cellWidth;
    int gridHeight = this.getHeight() / cellHeight;


    int hightlightx = 0;
    int highlighty = 0;
    for (int i = 0; i < gridHeight; i++) {
      for (int j = 0; j < gridWidth; j++) {


        if (i == 0 && j > 0) {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
          g2d.setColor(Color.BLACK);

          if (Coord.colIndexToName(j).length() > 8) {
            drawCenteredString(g, Coord.colIndexToName(j + y).substring(0, 8),
                    new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                    new Font("bold", Font.BOLD, 16));
          } else {
            drawCenteredString(g, Coord.colIndexToName(j + y),
                    new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                    new Font("bold", Font.BOLD, 16));
          }
        } else if (j == 0 && i > 0) {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
          g2d.setColor(Color.BLACK);

          if (String.valueOf(i + x).length() > 8) {
            drawCenteredString(g, String.valueOf(i + x).substring(0, 8),
                    new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                    new Font("bold", Font.BOLD, 16));
          } else {
            drawCenteredString(g, String.valueOf(i + x),
                    new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                    new Font("bold", Font.BOLD, 16));
          }
        } else if (i != 0 && j != 0) {
          if (model.getCellAt(j + y, i + x) != null) {
            if (model.getCellAt(j + y, i + x).toString().length() > 8) {
              drawCenteredString(g, model.getCellAt(j + y, i + x).toString().substring(0, 8),
                      new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                      new Font("default", Font.PLAIN, 14));
            } else {
              drawCenteredString(g, model.getCellAt(j + y, i + x).toString(),
                      new Rectangle(j * cellWidth, i * cellHeight, cellWidth, cellHeight),
                      new Font("default", Font.PLAIN, 14));
            }
          }
        } else {
          g2d.setColor(new Color(194, 194, 163));
          g2d.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
        }

        if (hightlightCoord != null) {
          if (hightlightCoord.col == j + y && hightlightCoord.row == i + x) {
            hightlightx = j;
            highlighty = i;
          }
        }

        g2d.setColor(Color.BLACK);

        g2d.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);

      }
    }

    if (hightlightx > 0 && highlighty > 0) {
      g2d.setColor(Color.red);
      float thickness = 2;
      Stroke oldStroke = g2d.getStroke();
      g2d.setStroke(new BasicStroke(thickness));

      g2d.drawRect(hightlightx * cellWidth, highlighty * cellHeight, cellWidth, cellHeight);

      g2d.setStroke(oldStroke);

    }
  }

  /**
   * the change of distance each time the horizontal slide makes.
   *
   * @param i distance of sliding
   */
  protected void setH(int i) {
    y = i;

  }

  /**
   * the change of distance each time the vertical slide makes.
   *
   * @param i distance of sliding
   */
  protected void setV(int i) {
    x = i;

  }

  protected void setHighlightCoord(Coord c) {
    hightlightCoord = c;
  }

  /**
   * To ensure text appear in the center of each grid.
   *
   * @param g    graphic
   * @param text the text inside each grid.
   * @param rect the grid.
   * @param font the front of the text.
   */
  private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
    FontMetrics metrics = g.getFontMetrics(font);
    int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
    g.setFont(font);
    g.drawString(text, x, y);

  }


  protected void addMouseListener(Features f) {

    MouseListener l = new MouseListener() {
      @Override
      public void mouseClicked(MouseEvent e) {
        //nothing
      }

      @Override
      public void mousePressed(MouseEvent e) {
        int x = e.getX() / cellWidth;
        int y = e.getY() / cellHeight;
        if (x > 0 && y > 0) {
          f.selectCell(new Coord(x + GridPanel.this.y, y + GridPanel.this.x));
        }
      }

      @Override
      public void mouseReleased(MouseEvent e) {

      }

      @Override
      public void mouseEntered(MouseEvent e) {
        //nothing
      }

      @Override
      public void mouseExited(MouseEvent e) {
        //nothing
      }
    };
    this.addMouseListener(l);


    //MouseMotion Listener
    MouseMotionListener ml = new MouseMotionListener() {

      @Override
      public void mouseDragged(MouseEvent e) {
        int x = e.getX() / cellWidth;
        int y = e.getY() / cellHeight;
        if (x > 0 && y > 0) {
          f.selectCell(new Coord(x + GridPanel.this.y, y + GridPanel.this.x));
        }
      }

      @Override
      public void mouseMoved(MouseEvent e) {
        //nothing
      }
    };

    this.addMouseMotionListener(ml);
  }



}
