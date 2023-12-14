package by.kovalski.port.util;

public class IdGenerator {
  private static long current = 0L;

  public static long getId() {
    return ++current;
  }
}
