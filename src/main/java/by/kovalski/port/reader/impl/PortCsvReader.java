package by.kovalski.port.reader.impl;

import by.kovalski.port.exception.PortException;
import by.kovalski.port.reader.PortReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PortCsvReader implements PortReader {
  private static final Logger logger = LogManager.getLogger();

  @Override
  public String readPort(String filePath) throws PortException {
    String port = null;
    try {
      port = Files.readAllLines(Paths.get(filePath)).get(1);
    } catch (IOException e) {
      logger.error("Error during reading data from file " + filePath, e);
      throw new PortException("Error during reading data from file " + filePath, e);
    }
    return port;
  }

  @Override
  public List<String> readPiers(String filePath) throws PortException {
    List<String> out = null;
    try{
      out = Files.readAllLines(Paths.get(filePath));
    }catch (IOException e){
      logger.error("Error during reading pierces",e);
      throw new PortException("Error during reading pierces",e);
    }
    out.remove(0);
    return out;
  }
}
