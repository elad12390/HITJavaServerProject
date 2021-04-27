package com.hit.gamecalendar.main.java.common.socket.responses;

import com.hit.gamecalendar.main.java.common.socket.enums.SocketResponseStatus;

public class SocketExceptionResponse<T> extends SocketResponse {
    public SocketExceptionResponse(String message, T data) {
        super(message, SocketResponseStatus.EXCEPTION, data.toString());
    }

    public SocketExceptionResponse(String message) {
        super(SocketResponseStatus.EXCEPTION, message);
    }
}
