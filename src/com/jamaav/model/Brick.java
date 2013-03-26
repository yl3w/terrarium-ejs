package com.jamaav.model;

public class Brick extends AbstractElement {

  public Brick(KeyToElement keyToElement) {
    super(keyToElement);
  }

  @Override
  public Terrarium act(Terrarium initial) {
    return initial;
  }

}
