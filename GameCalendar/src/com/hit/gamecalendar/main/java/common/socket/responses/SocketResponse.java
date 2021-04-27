package com.hit.gamecalendar.main.java.common.socket.responses;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.common.socket.enums.SocketResponseStatus;
import com.hit.gamecalendar.main.java.common.socket.interfaces.ISocketResponse;

public class SocketResponse implements ISocketResponse<String> {
    public SocketResponseStatus statusCode;
    public String data;

    public SocketResponse(String message, SocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public SocketResponse(SocketResponseStatus statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    @Override
    public SocketResponseStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getDataJson() {
        return data;
    }

    public <T> T getData(Class<T> c) {
        var gson = new Gson();
        return gson.fromJson(this.getDataJson(), c);
    }

    public Boolean isSuccessful() {
        return this.statusCode == SocketResponseStatus.OK;
    }
}
