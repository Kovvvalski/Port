package by.kovalski.port.reader;

import by.kovalski.port.exception.PortException;

import java.util.List;

@FunctionalInterface
public interface ThreadReader {
  List<String> readThreadsFromFile(String path) throws PortException;
}
