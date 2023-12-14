package by.kovalski.port.entity;

import by.kovalski.port.exception.PortException;
import by.kovalski.port.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ship extends Thread {
  private static final Logger logger = LogManager.getLogger();
  private static final double DEFAULT_CONTENT_COEFFICIENT = 0.5;
  private final long id = IdGenerator.getId();
  private int maxCapacity;
  private boolean action; // 1 - load to port, 0 - unload from port
  private int currentContent;

  public Ship(String name, int maxCapacity, boolean action, int currentContent) {
    super.setName(name);
    this.maxCapacity = maxCapacity;
    this.action = action;
    if (currentContent > maxCapacity || currentContent < 0) {
      logger.error("Not correct value of content");
      currentContent = (int) (DEFAULT_CONTENT_COEFFICIENT * maxCapacity);
    } else
      this.currentContent = currentContent;
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(int maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public boolean getAction() {
    return action;
  }

  public void setAction(boolean action) {
    this.action = action;
  }

  public int getCurrentContent() {
    return currentContent;
  }

  public void setCurrentContent(int currentContent) {
    this.currentContent = currentContent;
  }

  @Override
  public void run() {
    Port port = Port.getInstance();
    String pier = null;
    try {
      logger.info(Thread.currentThread().getName() + " is trying to get pier");
      pier = port.getPier();
      logger.info(Thread.currentThread().getName() + " got pier " + pier);
    } catch (PortException e) {
      e.printStackTrace();
    }
    try {
      port.doAction(maxCapacity,action, currentContent, pier);
      Random random = new Random();
      TimeUnit.SECONDS.sleep(random.nextInt(2)+1);
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info(Thread.currentThread().getName() + " finished work with pier " + pier);
    port.freePier(pier);
  }
}
