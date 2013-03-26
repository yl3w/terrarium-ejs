package com.jamaav.model;

public class Space extends AbstractElement {

  public Space(KeyToElement keyToElement) {
    super(keyToElement);
  }

  @Override
  public Terrarium act(Terrarium initial) {
    return initial;
  }
}
