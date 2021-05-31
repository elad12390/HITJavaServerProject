package com.hit.clock.main.java;

public interface IClock extends Runnable {
    void start();
    String getTime();
}
