package com.jamaav.model;

public class Lichen extends AbstractElement {

  public Lichen(KeyToElement keyToElement) {
    super(keyToElement);
  }

  @Override
  public Terrarium act(Terrarium initial) {
    return initial;
  }

}
