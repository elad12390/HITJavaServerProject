package com.hit.gamecalendar.main.java.api.socket.interfaces;

import java.io.IOException;

public interface ISocketExchange {

    <T> T get(Class<T> c);
    ISocketRequest getRequest() throws IOException;
    <T> void send(T data);
    void close() throws IOException;
}
