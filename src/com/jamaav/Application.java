package com.jamaav;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.jamaav.model.DefaultModelFactory;
import com.jamaav.model.ModelFactory;
import com.jamaav.model.Terrarium;

public class Application {

  private static final int RUN_DURATION = 2 * 60 * 1000;
  private static final int THREAD_COUNT = 5;

  public static void main(String[] args) {
    final Application application = new Application();
    application.doMain(args);
  }

  private void doMain(String[] args) {
    final ExecutorService threadPool = Executors
        .newFixedThreadPool(THREAD_COUNT);

    final ApplicationOptions options = new ApplicationOptions();
    final CmdLineParser parser = new CmdLineParser(options);

    try {
      parser.parseArgument(args);
    } catch (final CmdLineException ex) {
      System.err.println(ex.getMessage());
      parser.printUsage(System.err);
      return;
    }

    final String factoryType = options.getFactoryType();
    final ModelFactory factory = instantiateFactory(factoryType);
    final char[][] characters = readInitial(options.file);
    final Terrarium terrarium = factory.createTerrarium(characters);
    terrarium.start(threadPool);
    
    try {
      Thread.sleep(RUN_DURATION);
    } catch (Exception ex) {
      // safe to ignore
    }
    terrarium.stop();
    threadPool.shutdown();

    // kick of the lifecycle of the terrarium
    final char[][] asViewed = terrarium.printable();
    for (final char[] line : asViewed) {
      System.out.println(new String(line));
    }

  }

  private char[][] readInitial(File file) {
    if (!file.exists()) {
      System.err
          .println("Unable to load initial definition of terrarium. File "
              + file.getName() + " does not exist.");
      throw new RuntimeException(
          "Unable to load initial definition of terrarium. File "
              + file.getName() + " does not exist.");
    }

    final List<String> lines = new ArrayList<>();
    try {
      final BufferedReader reader = new BufferedReader(new FileReader(file));
      try {
        String line = reader.readLine();
        while (line != null && line.length() != 0) {
          lines.add(line);
          line = reader.readLine();
        }
      } catch (final IOException e) {
        System.err
            .println("Unable to load initial definition of terrarium. Error occurred when processing the file "
                + file.getName());
        throw new RuntimeException(e);
      } finally {
        try {
          reader.close();
        } catch (final Exception ex) {
          // safe to ignore this exception
        }
      }
    } catch (final FileNotFoundException e) {
      System.err
          .println("Unable to load initial definition of terrarium. File "
              + file.getName() + " does not exist.");
      throw new RuntimeException(
          "Unable to load initial definition on terrarium. File "
              + file.getName() + " does not exist.", e);
    }

    return convertToCharacters(lines);
  }

  private char[][] convertToCharacters(List<String> lines) {
    validSpecification(lines);
    final char[][] characters = new char[lines.size()][lines.get(0).length()];
    final Iterator<String> it = lines.iterator();
    for (int i = 0; it.hasNext(); i++) {
      final String line = it.next();
      final char[] charactersAtLine = line.toCharArray();
      System.arraycopy(charactersAtLine, 0, characters[i], 0,
          charactersAtLine.length);
    }
    return characters;
  }

  private void validSpecification(List<String> lines) {
    if (lines.size() < 1) {
      System.err.println("Expect a grid for terrarium specification.");
      throw new RuntimeException("Expect a grid for terrarium specification.");
    }

    final int length = lines.get(0).length();
    for (final String line : lines) {
      if (length != line.length()) {
        System.err
            .println("Incorrect grid specification for terrarium, expect line of length "
                + length + ", obtained line of length " + line.length());
        throw new RuntimeException(
            "Incorrect grid specification for terrarium, expect line of length "
                + length + ", obtained line of length " + line.length());
      }
    }
  }

  private ModelFactory instantiateFactory(String factoryType) {

    if (factoryType == null || factoryType.length() == 0) {
      return DefaultModelFactory.INSTANCE;
    }

    try {
      @SuppressWarnings("unchecked")
      final Class<? extends ModelFactory> factoryClass = (Class<? extends ModelFactory>) Class
          .forName(factoryType);
      return factoryClass.newInstance();
    } catch (final ClassNotFoundException e) {
      System.err.println("Invalid factory class specified");
      throw new RuntimeException(e);
    } catch (final InstantiationException e) {
      System.err
          .println("Unable to instanstiate factory of type "
              + factoryType
              + ", possible access specification error. Factory type and its constructor must be public, a default constructor is expected");
      throw new RuntimeException(e);
    } catch (final IllegalAccessException e) {
      System.err
          .println("Unable to instanstiate factory of type "
              + factoryType
              + ", possible access specification error. Factory type and its constructor must be public, a default constructor is expected");
      throw new RuntimeException(e);
    }
  }
}
