package com.jamaav.model;

public class BouncingBug extends AbstractElement {

  public BouncingBug(KeyToElement keyToElement) {
    super(keyToElement);
  }

  @Override
  public Terrarium act(Terrarium initial) {
    return initial;
  }
}
