package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.Features;
import edu.cs3500.spreadsheets.provider.model.Cell;
import edu.cs3500.spreadsheets.provider.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ViewOnlyModel;
import edu.cs3500.spreadsheets.provider.view.listener.ButtonListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * An editable graphic view for spreadsheet. Contains a tool bar for user to edit the content of
 * cell.
 */
public class SpreadsheetEditableGraphicView extends JFrame implements SpreadsheetView {

  private ScrollableGridPanel sPanel;

  private JToolBar toolBar;
  private JButton confirmButton;
  private JButton rejectButton;
  private JTextField textField;

  private Coord selectedCoord;

  private ViewOnlyModel model;

  /**
   * constructor for SpreadsheetEditableGraphicView.
   *
   * @param model a viewonly model.
   */
  public SpreadsheetEditableGraphicView(ViewOnlyModel model) {
    super();
    this.setTitle("Editable Spreadsheet");
    this.setSize(1500, 750);
    this.setMinimumSize(new Dimension(1000, 500));
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    this.model = model;

    sPanel = new ScrollableGridPanel(model);
    this.setLayout(new BorderLayout());
    this.add(sPanel, BorderLayout.CENTER);

    toolBar = new JToolBar();
    this.add(toolBar, BorderLayout.NORTH);

    confirmButton = new JButton("✓");

    confirmButton.setFont(new Font("Font", Font.BOLD, 16));
    confirmButton.setActionCommand("Confirm Button");
    toolBar.add(confirmButton);

    rejectButton = new JButton("×");
    rejectButton.setFont(new Font("Font", Font.BOLD, 16));
    rejectButton.setActionCommand("Reject Button");
    toolBar.add(rejectButton);


    textField = new JTextField(100);
    toolBar.add(textField);

    //default selected Coord
    setSelectedCoord(new Coord(1, 1));


    this.pack();

    resetFocus();

  }

  public SpreadsheetEditableGraphicView(String title, ViewOnlyModel model) {
    this(model);
    this.setTitle(title);
  }


  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    System.out.println("Adding feat");
    Map<String, Runnable> buttonClickedMap = new HashMap<String, Runnable>();
    ButtonListener buttonListener = new ButtonListener();
    buttonClickedMap.put("Confirm Button", features::confirm);
    buttonClickedMap.put("Reject Button", features::reject);
    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.addActionListener(buttonListener);

    sPanel.addMouseListener(features);

  }

  @Override
  public void addActionListener(ActionListener actionListener) {
    confirmButton.addActionListener(actionListener);
    rejectButton.addActionListener(actionListener);
  }

  @Override
  public String getInputString() {
    return textField.getText();
  }

  @Override
  public void setInputString(String s) {
    textField.setText(s);
  }

  @Override
  public Coord getSelectCoord() {
    return selectedCoord;
  }

  @Override
  public void setSelectedCoord(Coord c) {
    selectedCoord = new Coord(c.col, c.row);

    Cell cell = model.getCellAt(selectedCoord.col, selectedCoord.row);
    if (cell != null) {
      String s = cell.getRowContent();
      //s = s.substring(s.indexOf(' ') + 1);
      setInputString(s);
    } else {
      setInputString("");
    }


    highlightSelectedCoord();
  }

  private void highlightSelectedCoord() {
    sPanel.setHighlightCoord(selectedCoord);
    repaint();
  }


  @Override
  public void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
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
