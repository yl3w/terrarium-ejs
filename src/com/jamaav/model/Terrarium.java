package com.jamaav.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Terrarium {
  private static final int DELAY_BETWEEN_STEPS = 2000;
  private volatile boolean execute = true;
  private Element[][] grid;

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

  private Surrounding getSurrounding(int row, int col) {
    return new Surrounding(
        new Element[][] {
            new Element[] { grid[row - 1][col - 1], grid[row - 1][col],
                grid[row - 1][col + 1] },
            new Element[] { grid[row][col - 1], grid[row][col],
                grid[row][col + 1] },
            new Element[] { grid[row + 1][col - 1], grid[row + 1][col],
                grid[row + 1][col + 1] }, });
  }

  private void updateSurrounding(int row, int col, Surrounding surrounding) {
    grid[row - 1][col - 1] = surrounding.grid[0][0];
    grid[row - 1][col] = surrounding.grid[0][1];
    grid[row - 1][col + 1] = surrounding.grid[0][2];

    grid[row][col - 1] = surrounding.grid[1][0];
    grid[row][col] = surrounding.grid[1][1];
    grid[row][col + 1] = surrounding.grid[1][2];

    grid[row + 1][col - 1] = surrounding.grid[2][0];
    grid[row + 1][col] = surrounding.grid[2][1];
    grid[row + 1][col + 1] = surrounding.grid[2][2];
  }

  public void start(ExecutorService threadPool) {
    while (execute) {
      List<Future<?>> futures = new ArrayList<>();
      for (int i = 1; i < grid.length - 1; i++) {
        for (int j = 1; j < grid[0].length - 1; j++) {
          final int row = i;
          final int col = j;
          final Element element = grid[row][col];
          final Surrounding surrounding = getSurrounding(row, col);
          futures.add(threadPool.submit(new Runnable() {
            @Override
            public void run() {
              synchronized (Terrarium.this) {
                Surrounding result = element.act(Terrarium.this, surrounding);
                updateSurrounding(row, col, result);
              }
            }
          }));
        }
      }
      waitForCompletion(futures);
      print();
    }
  }

  private void waitForCompletion(List<Future<?>> futures) {
    // wait for futures to complete
    for (Future<?> f : futures) {
      try {
        f.get();
      } catch (InterruptedException e) {
        // ignore an interrupted exception
      } catch (ExecutionException e) {
        // this happens when the element failed to complete its task
      }
    }

    try {
      Thread.sleep(DELAY_BETWEEN_STEPS);
    } catch (InterruptedException e) {
      // safe to ignore
    }
  }

  private void print() {
    for (final char[] line : printable()) {
      System.out.println(new String(line));
    }
  }

  public void stop() {
    execute = false;
  }
}
