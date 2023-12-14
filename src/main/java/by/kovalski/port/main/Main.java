package by.kovalski.port.main;

import by.kovalski.port.creator.PortCreator;
import by.kovalski.port.creator.ThreadCreator;
import by.kovalski.port.creator.impl.PortCreatorImpl;
import by.kovalski.port.creator.impl.ThreadCreatorImpl;
import by.kovalski.port.entity.Port;
import by.kovalski.port.entity.Ship;
import by.kovalski.port.exception.PortException;
import by.kovalski.port.reader.PortReader;
import by.kovalski.port.reader.ThreadReader;
import by.kovalski.port.reader.impl.PortCsvReader;
import by.kovalski.port.reader.impl.ThreadCsvReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
  private static final Logger logger = LogManager.getLogger();
  private static final String PATH_TO_PORT = "src/main/resources/port.csv";
  private static final String PATH_TO_THREADS = "src/main/resources/threads.csv";
  private static final double MAX_CONTENT_COEFFICIENT = 0.1;

  public static void main(String[] args)throws Exception {
    PortCreator creator = new PortCreatorImpl();
    PortReader reader = new PortCsvReader();
    ThreadCreator creator1 = new ThreadCreatorImpl();
    ThreadReader reader1 = new ThreadCsvReader();
    String data = null;
    try {
      data = reader.readPort(PATH_TO_PORT);
    } catch (PortException e) {
      logger.fatal("Can not read data from file " + PATH_TO_PORT);
      return;
    }
    List<String> threadData = null;

    try {
      threadData = reader1.readThreadsFromFile(PATH_TO_THREADS);
    } catch (PortException e) {
      logger.fatal("Can not create data from file " + PATH_TO_THREADS);
      return;
    }
    Port port = creator.createPortFromString(data);
    List<Thread> threads = creator1.createThreads(threadData);
    for(int i = 0;i<threads.size();i++){
      if(((Ship)threads.get(i)).getMaxCapacity() >= (int) (MAX_CONTENT_COEFFICIENT * port.getMaxCapacity())){
        logger.error("The thread " + threads.get(i).getName() + " has not correct capacity, removing this thread from list");
        threads.remove(threads.get(i));
        i--;
      }
    }

    for (Thread t : threads) {
      t.start();
    }
    sleep(20000);
    System.out.println(port.getCurrentContent());
  }
}
