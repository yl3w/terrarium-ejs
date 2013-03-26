package com.jamaav.model;

abstract class AbstractElement implements Element {
  private final KeyToElement keyToElement;

  public AbstractElement(KeyToElement keyToElement) {
    this.keyToElement = keyToElement;
  }

  @Override
  public char asKey() {
    return keyToElement.asKey();
  }
}
