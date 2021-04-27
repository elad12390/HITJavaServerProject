package com.hit.gamecalendar.main.java.common.socket.interfaces;

import com.hit.gamecalendar.main.java.common.socket.enums.ESocketResponseStatus;

public interface ISocketResponse<T> {
    ESocketResponseStatus getStatusCode();
    T getDataJson();
}
