package by.kovalski.port.reader;

import by.kovalski.port.exception.PortException;

import java.util.List;
public interface PortReader {
  String readPort(String filePath) throws PortException;
  List<String> readPiers(String filePath) throws PortException;
}
