package com.hit.gamecalendar.main.java.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.gamecalendar.main.java.api.socket.SocketDriver;
import com.hit.gamecalendar.main.java.api.socket.pathmaker.SocketPathMaker;
import com.hit.gamecalendar.main.java.dao.GameType;
import com.hit.gamecalendar.main.java.dao.GameTypeDeserializer;
import com.hit.gamecalendar.main.java.dao.SqliteDatabase;
import com.hit.gamecalendar.main.java.common.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import com.hit.clock.main.java.Clock;

public class Startup {
    public static SqliteDatabase db;
    public static Gson gson;
    public static Clock clock = new Clock();
    public static AtomicBoolean isServerRunning = new AtomicBoolean(false);
    public static AtomicBoolean isRunning = new AtomicBoolean(true);
    private static CLI cli;
    public static ExecutorService mainThreads = Executors.newCachedThreadPool();


    public static void main(String[] args) {
        var argumentMap = getFormattedArgumentMap(args);
        setArgumentsConfig(argumentMap);

        // NISSIM THIS IS WHERE I START IT BECAUSE MY SERVICE IS SCOPED
        mainThreads.submit(Startup.clock);

        Startup.setup();
        Startup.runCLI();
    }
    /**
     * Setup dependencies.
     * */
    private static void setup() {
        try {
            Logger.setLoggingLevel(Config.getLoggingLevel());
            var dbFilePath = (new File(Config.getDatabaseFilePath())).getAbsolutePath();
            Startup.db = new SqliteDatabase("jdbc:sqlite:" + dbFilePath);

            var gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(GameType.class, new GameTypeDeserializer());
            gson = gsonBuilder.create();
        } catch (Exception e) {
            Logger.logError("Setup caught an exception: " + e);
            throw e;
        }
    }

    public static void runServer() {
        try {
            isServerRunning.set(true);
            SocketDriver driver = new SocketDriver(Config.getServerPort());
            // Controller contexts
            SocketPathMaker.makePaths(driver);
            driver.listen();
        } catch (Exception e) {
            Logger.logError("Server could not start, Exception: " + e);
        }
    }

    public static void closeServer() throws InterruptedException, IOException {
        Startup.isServerRunning.set(false);
        try {
            SocketDriver.closeSocket();
        } catch (Exception e) {
            System.out.println(e);
        }
        SocketDriver.threads.shutdown();
    }

    private static void runCLI() {
        cli = new CLI();
        mainThreads.submit(cli);
    }

    public static void shutdown() {
        try {
            if (isServerRunning.get()) {
                Startup.closeServer();
            }

            cli.scanner.close();
            Startup.mainThreads.shutdown();
        } catch (Exception e) {
            Logger.logError(e.toString());
        }
    }

    private static void setArgumentsConfig(Map<String, String> args) {
        if (args.containsKey("l"))
            Config.setLoggingLevel(args.get("l"));

        if (args.containsKey("f"))
            Config.setDatabaseFilePath(args.get("f"));

        if (args.containsKey("p"))
            Config.setServerPort(args.get("p"));

        if (args.containsKey("m")) {
            Config.setMatcher(args.get("m"));
        }
    }

    private static Map<String, String> getFormattedArgumentMap(String[] args) {
        var res = new HashMap<String, String>();

        for (var argument: args) {
            if (!argument.startsWith("--"))
                continue;

            var splittedText = argument.split("=");
            res.put(splittedText[0].substring(2),splittedText[1]);
        }

        return res;
    }

}
