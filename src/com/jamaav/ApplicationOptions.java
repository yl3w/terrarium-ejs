package com.jamaav;

import java.io.File;

import org.kohsuke.args4j.Option;

public class ApplicationOptions {
  @Option(name = "-file", usage = "file with initial definition of terrarium", required = true)
  public File file;

  @Option(name = "-factory", usage = "factory class for terrarium elements", required = false)
  public String factoryType;

  public File getFile() {
    return file;
  }

  public String getFactoryType() {
    return factoryType;
  }
}
