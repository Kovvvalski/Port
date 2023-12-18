package by.kovalski.port.entity;

import by.kovalski.port.util.PierIdGenerator;

public class Pier {
  private final int id = PierIdGenerator.getId();

  public Pier() {

  }

  public int getId() {
    return id;
  }
}
