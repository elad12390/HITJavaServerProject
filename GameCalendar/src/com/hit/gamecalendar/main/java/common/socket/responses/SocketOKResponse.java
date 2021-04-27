package com.hit.gamecalendar.main.java.common.socket.responses;

import com.hit.gamecalendar.main.java.common.socket.enums.ESocketResponseStatus;

public class SocketOKResponse extends SocketResponse {
    public SocketOKResponse(String data) {
        super(ESocketResponseStatus.OK, data);
    }
}
