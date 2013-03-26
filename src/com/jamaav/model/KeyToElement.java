package com.jamaav.model;

public enum KeyToElement {
  BRICK {
    @Override
    public AbstractElement getElement() {
      return new Brick(this);
    }

    @Override
    public char asKey() {
      return '#';
    }

  },
  BOUNCINGBUG {
    @Override
    public BouncingBug getElement() {
      return new BouncingBug(this);
    }

    @Override
    public char asKey() {
      return '%';
    }

  },
  LICHEN {
    @Override
    public Lichen getElement() {
      return new Lichen(this);
    }

    @Override
    public char asKey() {
      return '*';
    }

  },
  LICHENEATER {
    @Override
    public LichenEater getElement() {
      return new LichenEater(this);
    }

    @Override
    public char asKey() {
      return 'c';
    }

  },
  SPACE {

    @Override
    public Element getElement() {
      return new Space(this);
    }

    @Override
    public char asKey() {
      return ' ';
    }
  };
  public abstract Element getElement();

  public abstract char asKey();

}