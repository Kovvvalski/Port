package by.kovalski.port.util;

public class PierIdGenerator {
  private static int id;
  public static int getId(){
    return ++id;
  }
}
