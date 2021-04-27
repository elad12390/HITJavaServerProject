package com.hit.gamecalendar.main.java.common.socket.responses;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.common.socket.SocketExchange;
import com.hit.gamecalendar.main.java.dao.GameModel;

public class SocketResponseFactory {
    public static <T> SocketOKResponse createOkResponse(T data) {
        var gson = new Gson();
        return new SocketOKResponse(gson.toJson(data));
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
