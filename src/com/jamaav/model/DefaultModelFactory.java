package com.jamaav.model;

import java.util.HashMap;
import java.util.Map;

public class DefaultModelFactory implements ModelFactory {

  public static final ModelFactory INSTANCE = new DefaultModelFactory();

  private final Map<Character, KeyToElement> charToElements = new HashMap<>();

  private DefaultModelFactory() {
    for (final KeyToElement kte : KeyToElement.values()) {
      charToElements.put(Character.valueOf(kte.asKey()), kte);
    }
  }

  public Element getElementForKey(char key) {
    final KeyToElement kte = charToElements.get(Character.valueOf(key));
    if (kte == null) {
      throw new IllegalModelKeyElement("Undefined mode for key '" + kte + "'");
    }
    return kte.getElement();
  }

  @Override
  public Terrarium createTerrarium(char[][] lines) {
    final Element[][] grid = new Element[lines.length][];
    for (int i = 0; i < lines.length; i++) {
      final char[] line = lines[i];
      final Element[] elementsForLine = new Element[line.length];
      for (int j = 0; j < line.length; j++) {
        elementsForLine[j] = getElementForKey(line[j]);
      }
      grid[i] = elementsForLine;
    }
    return new Terrarium(grid);
  }
}
