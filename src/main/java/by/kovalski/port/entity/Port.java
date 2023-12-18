package by.kovalski.port.entity;

import by.kovalski.port.exception.PortException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;

import by.kovalski.port.observer.PortContentObserver;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;


public class Port {
  private static final Logger logger = LogManager.getLogger();
  private static final double MIN_BOARDER_COEFFICIENT = 0.1;
  private static final double MAX_BOARDER_COEFFICIENT = 0.9;
  private static final double PORTION_COEFFICIENT = 0.1;
  private static final double DEFAULT_CONTENT_COEFFICIENT = 0.5;
  private static volatile boolean isCreated;
  private static Port instance;
  private String name;
  private Semaphore semaphore;
  private int maxCapacity;
  private AtomicInteger currentContent;
  private ArrayDeque<Pier> availablePiers;
  private PortContentObserver observer;

  private Port() {

  }

  private Port(String name, int maxCapacity, int currentContent, ArrayDeque<Pier> piers) {
    this.name = name;
    this.semaphore = new Semaphore(piers.size());
    this.maxCapacity = maxCapacity;
    if (currentContent > maxCapacity || currentContent < 0) {
      logger.error("Invalid current content value, setting default value");
      this.currentContent = new AtomicInteger((int) (DEFAULT_CONTENT_COEFFICIENT * maxCapacity));
    } else
      this.currentContent = new AtomicInteger(currentContent);
    this.availablePiers = piers;
    addObserver();
    notifyObserver();
  }

  public static Port getInstance(String name, int maxCapacity, int currentContent, ArrayDeque<Pier> piers) {
    if (instance == null) {
      synchronized (Port.class) {
        if (!isCreated) {
          instance = new Port(name, maxCapacity, currentContent, piers);
          isCreated = true;
        }
      }
    }
    return instance;
  }

  public static Port getInstance() {
    if (instance == null) {
      synchronized (Port.class) {
        if (!isCreated) {
          instance = new Port();
          isCreated = true;
        }
      }
    }
    return instance;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCurrentContent() {
    return currentContent.get();
  }

  public int getMaxCapacity() {
    return maxCapacity;
  }

  public void setMaxCapacity(int maxCapacity) {
    this.maxCapacity = maxCapacity;
  }

  public Pier getPier() {
    Pier pier = null;
    try {
      semaphore.acquire();
      synchronized (Port.class) {
        pier = availablePiers.poll();
      }
    } catch (InterruptedException e) {
      logger.error("Can not acquire semaphore in thread " + Thread.currentThread().getName(), e);
      Thread.currentThread().interrupt();
    }
    return pier;
  }

  public void doAction(int maxCapacity, boolean action, int content, Pier pier) throws PortException {
    if (pier == null) {
      logger.error("Null pointer of pier");
      throw new PortException("Null pointer of pier");
    }
    if (action) {
      currentContent.set(currentContent.get() + content);
    } else {
      currentContent.set(currentContent.get() + content - maxCapacity);
    }
    notifyObserver();
  }
  synchronized public void freePier(Pier pier) {
    availablePiers.offer(pier);
    semaphore.release();
  }

  public void removeObserver() {
    observer = null;
  }

  public void addObserver() {
    observer = () -> {
      if (currentContent.get() > MAX_BOARDER_COEFFICIENT * maxCapacity) {
        logger.warn("The port is near to overflowing, fixing this");
        currentContent.set(currentContent.get() - (int) (PORTION_COEFFICIENT * maxCapacity));
      }
      if (currentContent.get() < MIN_BOARDER_COEFFICIENT * maxCapacity) {
        logger.warn("The port is near to devastation, fixing this");
        currentContent.set(currentContent.get() + (int) (PORTION_COEFFICIENT * maxCapacity));
      }
    };
  }

  public void notifyObserver() {
    if (observer != null)
      observer.changePortContent();
  }
}
