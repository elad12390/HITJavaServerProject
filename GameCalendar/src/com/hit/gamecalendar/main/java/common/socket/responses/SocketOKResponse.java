package com.hit.gamecalendar.main.java.common.socket.responses;

import com.hit.gamecalendar.main.java.common.socket.enums.SocketResponseStatus;

public class SocketOKResponse extends SocketResponse {
    public SocketOKResponse(String data) {
        super(SocketResponseStatus.OK, data);
    }
}
