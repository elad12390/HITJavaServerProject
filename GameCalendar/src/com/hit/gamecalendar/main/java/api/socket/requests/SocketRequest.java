package com.hit.gamecalendar.main.java.api.socket.requests;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.api.socket.interfaces.ISocketRequest;

public class SocketRequest implements ISocketRequest {
    private final String method;
    private final String path;
    private final ParamRequestMap queryData;
    private String data;

    public SocketRequest(String method, String path, ParamRequestMap queryData, String jsonData) {
        this.method = method;
        this.path = path;
        this.queryData = queryData;
        this.data = jsonData;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public ParamRequestMap getQueryData() {
        return queryData;
    }

    @Override
    public String getJsonData() {
        return data;
    }

    public <T> T getData(Class<T> c) {
        return Startup.gson.fromJson(this.getJsonData(), c);
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SocketRequest{" +
                "method='" + method + '\'' +
                ", path='" + path + '\'' +
                ", queryData=" + queryData +
                ", data=" + data +
                '}';
    }
}
