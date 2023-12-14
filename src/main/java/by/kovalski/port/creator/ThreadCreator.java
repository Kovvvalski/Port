package by.kovalski.port.creator;

import java.util.List;

@FunctionalInterface
public interface ThreadCreator {
  List<Thread> createThreads(List<String> data);
}
