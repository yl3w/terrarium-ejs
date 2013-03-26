package com.jamaav.model;

public class Terrarium {
  public Element[][] grid;

  public Terrarium(Element[][] grid) {
    this.grid = grid;
  }

  public char[][] printable() {
    final char[][] printable = new char[grid.length][grid[0].length];
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[0].length; j++) {
        printable[i][j] = grid[i][j].asKey();
      }
    }
    return printable;
  }
}
