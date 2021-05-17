package com.hit.gamecalendar.main.java.api.socket.responses;

import com.hit.gamecalendar.main.java.api.Startup;

public class SocketResponseFactory {
    public static <T> SocketOKResponse createOkResponse(T data) {
        return new SocketOKResponse(Startup.gson.toJson(data));
    }

    public static <T extends Exception> SocketNotFoundExceptionResponse<String> createNotFoundResponse(String message, T data) {
        return new SocketNotFoundExceptionResponse<>(message, data.toString());
    }

    public static <T extends Exception> SocketExceptionResponse<String> createExceptionResponse(String message, T data) {
        return new SocketExceptionResponse<>(message, data.toString());
    }

    public static <T extends Exception> SocketExceptionResponse<String> createExceptionResponse(String message) {
        return new SocketExceptionResponse<>(message);
    }
}
