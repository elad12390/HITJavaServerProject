package com.hit.gamecalendar.main.java.api.socket.interfaces;

import com.hit.gamecalendar.main.java.api.socket.enums.ESocketResponseStatus;

public interface ISocketResponse<T> {
    ESocketResponseStatus getStatusCode();
    T getDataJson();
}
