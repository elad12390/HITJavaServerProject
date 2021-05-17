package com.hit.gamecalendar.main.java.api.socket.interfaces;

import java.net.InetAddress;

public interface ISocketDriver {
    void listen();
    InetAddress getSocketAddress();
    int getPort();
}
