package com.hit.gamecalendar.main.java.api.socket;

import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.api.socket.interfaces.ISocketExchange;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;

import java.io.*;
import java.net.Socket;

public class SocketExchange implements ISocketExchange {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String request;

    public SocketExchange(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(this.socket.getOutputStream());
    }

    @Override
    public SocketRequest getRequest() {
        if (this.request == null) {
            try {
                this.request = in.readUTF();
            } catch (Exception e) {
                Logger.logError(e.toString());
            }
        }
        return this.request != null ? Startup.gson.fromJson(this.request, SocketRequest.class) : null;
    }

    @Override
    public <T> T get(Class<T> c) {
        if (this.request == null) {
            try {
                this.request = in.readUTF();
            } catch (Exception e) {
                Logger.logError(e.toString());
            }
        }
        return this.request != null ? Startup.gson.fromJson(this.request, c) : null;
    }

    @Override
    public <T> void send(T data) {
        String output = Startup.gson.toJson(data);
        try {
            out.writeUTF(output);
            out.flush();
        } catch (Exception e) {
            Logger.logError("Socket error " + e.toString());
        }

    }

    @Override
    public void close() {
        try {
            if (!this.socket.isInputShutdown()) {
                this.socket.shutdownInput();
            }
            if (!this.socket.isOutputShutdown()) {
                this.socket.shutdownOutput();
            }
            if (!this.socket.isClosed()) {
                this.socket.close();
            }
        } catch (Exception e) {
            Logger.logError("Could not close socket");
        }
    }


}
