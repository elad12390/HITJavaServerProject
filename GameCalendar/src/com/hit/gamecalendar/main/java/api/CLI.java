package com.hit.gamecalendar.main.java.api;

import com.google.gson.JsonObject;
import com.hit.gamecalendar.main.java.api.socket.SocketDriver;
import com.hit.gamecalendar.main.java.api.socket.SocketExchange;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.api.socket.responses.SocketResponse;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import org.junit.runner.Request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class CLI implements Runnable {
    public static final String HELP_TEXT = "Commands:" +
            "\n\tstart - starts the server" +
            "\n\tstop - stops the server" +
            "\n\tsend [JSON] - sends the [JSON] to the server and presents the result - for example send {\"method\":\"GET\",\"path\":\"/api/Game/\"}" +
            "\n\thelp - print this menu";


    Scanner scanner = new Scanner(System.in);
    Thread thisThread;
    public void start() {
        thisThread = (new Thread(this));
        thisThread.start();
        System.out.println();
    }
    public void run() {
        System.out.println(HELP_TEXT);
        while (Startup.isRunning.get()) {
            try {
                handleRequest(getUserInput());
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
            if(!Startup.isRunning.get()) {
                return;
            }
        }
    }

    private String getUserInput() {
        return scanner.nextLine();
    }

    private void handleRequest(String req) throws InterruptedException, IOException {
        switch (req) {
            case "start":
                if (!Startup.isServerRunning.get()) {
                    Startup.runServer();
                } else {
                    System.out.println("Server has already started.");
                }
                break;
            case "stop":
                if (Startup.isServerRunning.get()) {
                    Startup.closeServer();
                    Logger.logInformation("Server closed.");
                } else {
                    Logger.logInformation("Server is not running.");
                }
                break;
            case "exit":
                Startup.shutdown();
                Thread.sleep(10);
                break;
            case "help":
                System.out.println(HELP_TEXT);
                break;
            default:
                if (req.startsWith("send ")) {
                    var json = req.substring(5);
                    Logger.logDebug("Sending " + json + " to the server");
                    var reqObj = getRequestFromJsonOrNull(json);
                    if (reqObj != null) {
                        if (Startup.isServerRunning.get()) {
                            SocketExchange exchange = new SocketExchange(new Socket(Config.getClientAddress(), Config.getServerPort()));
                            exchange.send(reqObj);
                            var result = exchange.get(SocketResponse.class);
                            Logger.logInformation(result.getDataJson());
                        } else {
                            Logger.logInformation("Server is not running, what are you trying to do ?");
                        }
                    } else {
                        Logger.logInformation("Json is invalid. please try again.");
                    }
                }
        }
    }

    private SocketRequest getRequestFromJsonOrNull(String test) {
        try {
            return Startup.gson.fromJson(test, SocketRequest.class);
        } catch (Exception ex) {
            return null;
        }
    }

}
