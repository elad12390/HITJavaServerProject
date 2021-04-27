package com.hit.gamecalendar.main.java.common.socket.interfaces;

import com.hit.gamecalendar.main.java.common.socket.enums.SocketResponseStatus;

public interface ISocketResponse<T> {
    SocketResponseStatus getStatusCode();
    T getDataJson();
}
