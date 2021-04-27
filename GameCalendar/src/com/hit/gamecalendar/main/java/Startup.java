package com.hit.gamecalendar.main.java;

import com.hit.gamecalendar.main.java.common.socket.SocketDriver;
import com.hit.gamecalendar.main.java.dao.SqliteDatabase;
import com.hit.gamecalendar.main.java.common.logger.ILogger;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.common.socket.pathmaker.SocketPathMaker;

import java.io.File;

public class Startup {
    public static SqliteDatabase db;

    /**
     * Setup dependencies.
     * */
    private static void setup() {
        try {
            Logger.setLoggingLevel(Config.loggingLevel);
            var dbFilePath = (new File(Config.DATABASE_FILE_PATH)).getAbsolutePath();
            Startup.db = new SqliteDatabase("jdbc:sqlite:" + dbFilePath);
        } catch (Exception e) {
            Logger.logError("Setup caught an exception: " + e);
            throw e;
        }
    }

    private static void run() {
        try {
            SocketDriver driver = new SocketDriver(Config.serverPort);
            // Controller contexts
            SocketPathMaker.makePaths(driver);
            driver.listen();
        } catch (Exception e) {
            Logger.logError("Server could not start, Exception: " + e);
        }
    }

    public static void main(String[] args) {
        Startup.setup();
        Startup.run();
    }
}
