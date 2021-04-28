package com.hit.gamecalendar.main.java.api.socket.interfaces;

import com.hit.gamecalendar.main.java.api.socket.requests.ParamRequestMap;

public interface ISocketRequest {
    String getMethod();
    String getPath();
    ParamRequestMap getQueryData();
    String getJsonData();
}
