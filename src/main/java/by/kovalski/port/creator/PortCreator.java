package by.kovalski.port.creator;

import by.kovalski.port.entity.Port;
import by.kovalski.port.exception.PortException;

@FunctionalInterface
public interface PortCreator {
  Port createPortFromString(String port);
}
