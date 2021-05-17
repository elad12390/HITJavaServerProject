package com.hit.gamecalendar.main.java.api.socket.interfaces;

public interface ISocketExchange {

    <T> T get(Class<T> c);
    ISocketRequest getRequest();
    <T> void send(T data);
    void close();
}
