package by.kovalski.port.creator.impl;

import by.kovalski.port.creator.ThreadCreator;
import by.kovalski.port.entity.Ship;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ThreadCreatorImpl implements ThreadCreator {
  private static final Logger logger = LogManager.getLogger();
  private static final int NAME_INDEX = 0;
  private static final int CAPACITY_INDEX = 1;
  private static final int ACTION_INDEX = 2;
  private static final int CONTENT_INDEX = 3;
  private static final int DEFAULT_CAPACITY_VALUE = 1000;
  private static final int DEFAULT_CONTENT_VALUE = 500;
  private static final String DELIMITER = ",";

  @Override
  public List<Thread> createThreads(List<String> data){
    List<Thread> out = new ArrayList<>();
    for(String row : data){
      int capacity = 0;
      boolean action = true;
      int content = 0;
      String[] parsed = row.split(DELIMITER);
      try {
        capacity = Integer.parseInt(parsed[CAPACITY_INDEX]);
      }catch (NumberFormatException e){
        logger.error("Not valid capacity value, setting default",e);
        capacity = DEFAULT_CAPACITY_VALUE;
      }
      try {
        content = Integer.parseInt(parsed[CONTENT_INDEX]);
      }catch (NumberFormatException e){
        logger.error("Not valid content value, setting default",e);
        content = DEFAULT_CONTENT_VALUE;
      }
      try {
        action = Boolean.parseBoolean(parsed[ACTION_INDEX]);
      }catch (NumberFormatException e){
        logger.error("Not valid action value, setting default",e);
      }
      out.add(new Ship(parsed[NAME_INDEX],capacity,action,content));
    }
    return out;
  }
}
