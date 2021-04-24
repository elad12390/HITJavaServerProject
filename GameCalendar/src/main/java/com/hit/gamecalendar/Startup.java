package main.java.com.hit.gamecalendar;

import com.sun.net.httpserver.HttpServer;
import main.java.com.hit.gamecalendar.common.logger.Logger;
import main.java.com.hit.gamecalendar.common.logger.ILogger;
import main.java.com.hit.gamecalendar.common.pathmaker.PathMaker;
import main.java.com.hit.gamecalendar.dao.Database;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Startup {
    public static Database db;
    public static ILogger logger;
    public static Boolean finishedSetup = false;
    public static final int serverPort = 9110;

    /**
     * Setup dependencies.
     * */
    private static void setup() {
        try {
            Startup.db = new Database("jdbc:mysql://localhost:3306/?user=root", "root" , "1234", "game-calendar");
            Startup.logger = new Logger();
        } catch (Exception e) {
            logger.logError("Setup caught an exception: " + e);
            throw e;
        }

        Startup.finishedSetup = true;
    }

    private static void run() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

            // Controller contexts
            PathMaker.makePaths(server);

            // Makes the server run using threads.
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            logger.logInformation("running at port " + Startup.serverPort);
        } catch (Exception e) {
            logger.logError("Server could not start, Exception: " + e);
        }
    }

    public static void main(String[] args) {
        Startup.setup();
        Startup.run();
    }
}
