package com.hit.gamecalendar.main.java.common.socket.responses;

public class SocketNotFoundExceptionResponse<T> extends SocketExceptionResponse<T> {
    public SocketNotFoundExceptionResponse(String message, T data) {
        super(message, data);
    }
}
