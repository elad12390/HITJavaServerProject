package com.hit.gamecalendar.main.java.common.socket.interfaces;

import com.hit.gamecalendar.main.java.common.socket.requests.ParamRequestMap;

public interface ISocketRequest {
    String getMethod();
    String getPath();
    ParamRequestMap getQueryData();
    String getJsonData();
}
