package com.jamaav.model;

import org.junit.Assert;
import org.junit.Test;

public class TerrariumTest {

  @Test
  public void testPrint() {
    final Element[][] grid = new Element[][] {
        new Element[] { KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement(), KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement() },
        new Element[] { KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement(), KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement() },
        new Element[] { KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement(), KeyToElement.BRICK.getElement(),
            KeyToElement.BRICK.getElement() } };
    final Terrarium t = new Terrarium(grid);
    final char[][] printable = t.printable();
    final char[][] expected = new char[][] { new char[] { '#', '#', '#', '#' },
        new char[] { '#', '#', '#', '#' }, new char[] { '#', '#', '#', '#' }, };
    Assert.assertEquals(expected.length, printable.length);
    for (int i = 0; i < expected.length; i++) {
      Assert.assertArrayEquals(expected[i], printable[i]);
    }
  }
}
