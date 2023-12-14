package by.kovalski.port.entity;

import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class Temp implements Runnable {

  @Override
  public void run(){
    System.out.println(Port.getInstance()); ;
  }
}
