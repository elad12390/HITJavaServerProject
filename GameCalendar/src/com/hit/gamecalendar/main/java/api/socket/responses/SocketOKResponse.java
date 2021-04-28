package com.hit.gamecalendar.main.java.api.socket.responses;

import com.hit.gamecalendar.main.java.api.socket.enums.ESocketResponseStatus;

public class SocketOKResponse extends SocketResponse {
    public SocketOKResponse(String data) {
        super(ESocketResponseStatus.OK, data);
    }
}
