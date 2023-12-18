package by.kovalski.port.util;

public class IdGenerator {
  private static long current;

  public static long getId() {
    return ++current;
  }
}
