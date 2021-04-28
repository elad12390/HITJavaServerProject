package com.hit.gamecalendar.main.java.api.socket;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.api.socket.exceptions.SocketPathAlreadyExistsException;
import com.hit.gamecalendar.main.java.api.socket.interfaces.ISocketDriver;
import com.hit.gamecalendar.main.java.api.socket.interfaces.ISocketHandler;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketDriver implements ISocketDriver {

    private final int port;
    private final int backlog;
    private final InetAddress bindAddr;

    private static ServerSocket server;
    private static  Socket socket;

    private final ExecutorService threads = Executors.newCachedThreadPool();
    // path map
    private final SocketPathMap socketPaths = new SocketPathMap();

    public SocketDriver(String ipAddress, int port) throws IOException {
        this.port = port;
        this.backlog = 1;
        this.bindAddr = ipAddress != null && !ipAddress.isEmpty() ?
                InetAddress.getByName(ipAddress) :
                InetAddress.getLocalHost();
    }

    public SocketDriver(int port) throws IOException {
        this.port = port;
        this.backlog = 1;
        this.bindAddr = InetAddress.getLocalHost();
    }


    @Override
    public void listen() {
        threads.submit(() -> {
            server = new ServerSocket(this.port);
            Logger.logInformation("Running at " + this.getSocketAddress().getHostAddress() + ":" + this.getPort());

            do {
                socket = server.accept();
                threads.submit(handleClientRequest());
            } while (true);
        });
    }

    private Runnable handleClientRequest() {
        return () -> {
            try {
                SocketExchange exchange = new SocketExchange(socket);
                SocketRequest request = exchange.getRequest();

                // we have request from client lets do something with it !
                // lets try to find the requested request in the method list
                var key = socketPaths
                        .keySet()
                        .stream()
                        .filter((k) -> request.getPath().indexOf(k) != -1)
                        .findAny().orElse(null);

                if (key != null) {
                    var p = socketPaths.get(key);
                    p.handle(exchange);
                }
            } catch (Exception e) {
                Logger.logError("Could not ");
            }
        };
    }

    public void createPath(String path, ISocketHandler handler) throws SocketPathAlreadyExistsException {
        if (this.socketPaths.containsKey(path)) {
            throw new SocketPathAlreadyExistsException(path + " Already exists.");
        }
        this.socketPaths.put(path, handler);
    }

    @Override
    public InetAddress getSocketAddress() {
        return server.getInetAddress();
    }

    @Override
    public int getPort() {
        return server.getLocalPort();
    }
}
