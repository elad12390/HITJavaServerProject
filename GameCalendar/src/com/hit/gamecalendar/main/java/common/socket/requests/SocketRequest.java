package com.hit.gamecalendar.main.java.common.socket.requests;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.common.socket.interfaces.ISocketRequest;

public class SocketRequest implements ISocketRequest {
    private String method;
    private String path;
    private ParamRequestMap queryData;
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
        var gson = new Gson();
        return gson.fromJson(this.getJsonData(), c);
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