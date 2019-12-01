package edu.cs3500.spreadsheets.provider.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;

/**
 * To represent the visible spreadsheet.
 */
public class SpreadsheetGraphicView extends JFrame implements SpreadsheetView {

  private ScrollableGridPanel sPanel;



  /**
   * To construct SpreadsheetGraphicView
   * set the size and name of the visible canvas.
   * @param model SpreadsheetModel
   */
  public SpreadsheetGraphicView(ViewOnlyModel model) {
    super();
    this.setTitle("Spreadsheet");
    this.setSize(1500, 750);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    sPanel = new ScrollableGridPanel(model);

    this.add(sPanel);


    this.pack();


  }

  public SpreadsheetGraphicView(String title, ViewOnlyModel model) {
    this(model);
    this.setTitle(title);
  }




  @Override
  public void render() {
    this.setVisible(true);
  }


  @Override
  public void addFeatures(Features features) {
    // this view will not include any features.
  }

  @Override
  public void addActionListener(ActionListener listener) {
    // this view will not include buttons.
  }

  @Override
  public String getInputString() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setInputString(String s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Coord getSelectCoord() {
    throw new UnsupportedOperationException();
  }


  @Override
  public void setSelectedCoord(Coord c) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void resetFocus() {
    // no need resetfocus becase focus didn't change.
  }

  @Override
  public void showMessage(String m) {
    JOptionPane.showMessageDialog(null, m,
            "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(null, error,
            "Error", JOptionPane.ERROR_MESSAGE);
  }
}
