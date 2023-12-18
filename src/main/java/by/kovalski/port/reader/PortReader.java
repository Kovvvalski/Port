package by.kovalski.port.reader;

import by.kovalski.port.exception.PortException;

@FunctionalInterface
public interface PortReader {
  String readPort(String filePath) throws PortException;
}
