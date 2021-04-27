package com.hit.gamecalendar.main.java.common.socket;

import com.hit.gamecalendar.main.java.common.socket.interfaces.ISocketHandler;

import java.util.HashMap;
import java.util.Map;

public class SocketPathMap extends HashMap<String, ISocketHandler> {
    public SocketPathMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public SocketPathMap(int initialCapacity) {
        super(initialCapacity);
    }

    public SocketPathMap() {
    }

    public SocketPathMap(Map<? extends String, ? extends ISocketHandler> m) {
        super(m);
    }
}
