package com.hit.gamecalendar.main.java.common.socket.interfaces;

import com.hit.gamecalendar.main.java.common.socket.SocketExchange;

import java.io.IOException;

public interface ISocketHandler {
    void handle(SocketExchange exchange) throws IOException;
}
