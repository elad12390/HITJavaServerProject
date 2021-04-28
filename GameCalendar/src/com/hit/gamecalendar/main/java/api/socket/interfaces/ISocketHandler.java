package com.hit.gamecalendar.main.java.api.socket.interfaces;

import com.hit.gamecalendar.main.java.api.socket.SocketExchange;

import java.io.IOException;

public interface ISocketHandler {
    void handle(SocketExchange exchange) throws IOException;
}
