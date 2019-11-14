package edu.cs3500.spreadsheets.model;

/**
 * Interface to organize Value cells. Contains no methods.
 */
public interface Value<K> extends CellContents<K> {

  /**
   * returns Dbl if its a Dbl.
   *
   * @return
   */
  Dbl getDbl();


}
