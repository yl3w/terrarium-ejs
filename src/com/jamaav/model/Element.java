package com.jamaav.model;

public interface Element {
  public Surrounding act(Terrarium initial, Surrounding surrounding);

  public char asKey();
}
