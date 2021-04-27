package com.hit.gamecalendar.main.java.common.socket.interfaces;

import java.io.IOException;
import java.net.InetAddress;

public interface ISocketDriver {
    void listen() throws IOException, InterruptedException;
    InetAddress getSocketAddress();
    int getPort();
}
