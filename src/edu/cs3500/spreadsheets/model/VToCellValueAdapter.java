package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.provider.model.CellValue;

/**
 * Class to adapt Values to CellValues.  Implements provider interface and delegates their methods
 * to an instance of our own Value implementation.
 */
public class VToCellValueAdapter implements CellValue {
  Value value;

  /**
   * Constructs the adapter object. Translates CellValue methods to Value methods.
   * @param v the value to adapt.
   */
  public VToCellValueAdapter(Value v) {
    this.value = v;
  }

  @Override
  public String toString() {
    return value.toString();
  }

}
