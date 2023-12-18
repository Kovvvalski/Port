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
  private static final Random random = new Random();
  private final long id = IdGenerator.getId();
  private int maxCapacity;
  private boolean action; // true - load to port, false - unload from port
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
    logger.info(Thread.currentThread().getName() + " is trying to get pier");
    Pier pier = port.getPier();
    if (pier != null)
      logger.info(Thread.currentThread().getName() + " got pier " + pier.getId());
    else {
      logger.error("In thread " + getName() + " error during getting pier");
      return;
    }
    try {
      port.doAction(maxCapacity, action, currentContent, pier);
      TimeUnit.SECONDS.sleep(random.nextInt(2) + 1);
    } catch (PortException e) {
      logger.error("Error during doing action in thread " + getName(), e);
      return;
    } catch (InterruptedException e) {
      logger.error("Thread " + getName() + " is interrupted", e);
    }
    logger.info(Thread.currentThread().getName() + " finished work with pier " + pier.getId());
    port.freePier(pier);
  }
}
