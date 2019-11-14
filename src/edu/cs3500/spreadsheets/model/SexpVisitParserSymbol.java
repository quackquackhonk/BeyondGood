package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * Subclass of {@code SexpVisitParser} to parse SExps not preceded by "=".
 */
public class SexpVisitParserSymbol extends SexpVisitParser {
  /**
   * Process a symbol.
   *
   * @param s the value
   * @return the desired result
   */
  @Override
  public ReferenceCell visitSymbol(String s) {
    throw new IllegalArgumentException("No equals before SSymbol");
  }

  /**
   * Process a list value.
   *
   * @param l the contents of the list (not yet visited)
   * @return the desired result
   */
  @Override
  public Ops visitSList(List l) {
    throw new IllegalArgumentException("No equals before SList");
  }


}
