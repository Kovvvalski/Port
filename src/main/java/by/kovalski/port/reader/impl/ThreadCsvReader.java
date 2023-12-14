package by.kovalski.port.reader.impl;

import by.kovalski.port.exception.PortException;
import by.kovalski.port.reader.ThreadReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ThreadCsvReader implements ThreadReader {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public List<String> readThreadsFromFile(String path) throws PortException {
    List<String> out = null;
    try {
      out = Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      logger.error("Error during reading threads", e);
      throw new PortException("Error during reading threads", e);
    }
    out.remove(0);
    return out;
  }
}
