package by.kovalski.port.creator.impl;

import by.kovalski.port.creator.PortCreator;
import by.kovalski.port.entity.Pier;
import by.kovalski.port.entity.Port;
import by.kovalski.port.reader.PortReader;
import by.kovalski.port.reader.impl.PortCsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

public class PortCreatorImpl implements PortCreator {
  private static final Logger logger = LogManager.getLogger();
  private static final String DELIMITER = ",";
  private static final int NAME_INDEX = 0;
  private static final int CAPACITY_INDEX = 1;
  private static final int CONTENT_INDEX = 2;
  private static final int PIERS_INDEX = 3;
  private static final int DEFAULT_CAPACITY_VALUE = 1000;
  private static final int DEFAULT_CONTENT_VALUE = 500;
  private static final int DEFAULT_PIERS_NUMBER = 3;
  private static final String[] defaultPierces = new String[]{"pier1", "pier2", "pier3"};

  @Override
  public Port createPortFromString(String port) {
    PortReader reader = new PortCsvReader();
    String[] parsed = port.split(DELIMITER);
    ArrayDeque<Pier> piers = new ArrayDeque<>();
    int capacity = 0;
    int content = 0;
    int numberOfPiers = 0;
    try {
      capacity = Integer.parseInt(parsed[CAPACITY_INDEX]);
    } catch (NumberFormatException e) {
      logger.error("Not valid capacity value, setting default");
      capacity = DEFAULT_CAPACITY_VALUE;
    }
    try {
      content = Integer.parseInt(parsed[CONTENT_INDEX]);
    } catch (NumberFormatException e) {
      logger.error("Not valid content value, setting default");
      content = DEFAULT_CONTENT_VALUE;
    }
    try {
      numberOfPiers = Integer.parseInt(parsed[PIERS_INDEX]);
    } catch (NumberFormatException e) {
      logger.error("Not valid piers number value, setting default");
      numberOfPiers = DEFAULT_PIERS_NUMBER;
    }
    for (int i = 0;i< numberOfPiers;i++){
      piers.offer(new Pier());
    }
    return Port.getInstance(parsed[NAME_INDEX], capacity, content, piers);
  }
}
