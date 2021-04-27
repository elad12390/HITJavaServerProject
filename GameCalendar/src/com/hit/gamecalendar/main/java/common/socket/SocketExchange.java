package com.hit.gamecalendar.main.java.common.socket;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.Startup;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.common.socket.interfaces.ISocketExchange;
import com.hit.gamecalendar.main.java.common.socket.interfaces.ISocketRequest;
import com.hit.gamecalendar.main.java.common.socket.requests.SocketRequest;

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
        return this.request != null ? (new Gson()).fromJson(this.request, SocketRequest.class) : null;
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
        return this.request != null ? (new Gson()).fromJson(this.request, c) : null;
    }

    @Override
    public <T> void send(T data) {
        String output = (new Gson()).toJson(data);
        try {
            out.writeUTF(output);
            out.flush();
        } catch (Exception e) {
            Logger.logError("Socket error " + e.toString());
        }

    }

    @Override
    public void close() throws IOException {
        this.socket.close();
    }


}
