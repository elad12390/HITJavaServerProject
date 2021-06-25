package com.hit.gamecalendar.main.java.api;


import com.hit.gamecalendar.main.java.common.enums.ELoggingLevel;
import main.java.com.hit.stringmatching.implementations.KnuthMorrisPrattAlgoMatcherImpl;
import main.java.com.hit.stringmatching.implementations.NaiveAlgoMatcherImpl;
import main.java.com.hit.stringmatching.implementations.RobinKarpAlgoMatcherImpl;
import main.java.com.hit.stringmatching.interfaces.IAlgoStringMatcher;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Config {
    private static ELoggingLevel loggingLevel = ELoggingLevel.DEBUG;
    private static String DATABASE_FILE_PATH = "GameCalendar/src/com/hit/gamecalendar/main/resources/database/gamedb.db";
    private static int serverPort = 9110;
    private static IAlgoStringMatcher matcher = new KnuthMorrisPrattAlgoMatcherImpl();

    public static ELoggingLevel getLoggingLevel() {
        return loggingLevel;
    }

    public static void setLoggingLevel(ELoggingLevel loggingLevel) {
        Config.loggingLevel = loggingLevel;
    }

    public static void setLoggingLevel(String loggingLevel) {
        switch (loggingLevel.toLowerCase()) {
            case "e" -> Config.setLoggingLevel(ELoggingLevel.ERROR);
            case "i" -> Config.setLoggingLevel(ELoggingLevel.INFORMATION);
            case "d" -> Config.setLoggingLevel(ELoggingLevel.DEBUG);
        }
    }

    public static String getDatabaseFilePath() {
        return DATABASE_FILE_PATH;
    }

    public static void setDatabaseFilePath(String databaseFilePath) {
        DATABASE_FILE_PATH = databaseFilePath;
    }

    public static int getServerPort() {
        return serverPort;
    }

    public static InetAddress getClientAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    public static void setServerPort(int serverPort) {
        Config.serverPort = serverPort;
    }

    public static void setServerPort(String serverPort) throws IllegalArgumentException {
        if (!serverPort.matches("-?\\d+")) throw new IllegalArgumentException("Server port is not a number");
        setServerPort(Integer.parseInt(serverPort));
    }

    public static IAlgoStringMatcher getMatcher() {
        return matcher;
    }

    public static void setMatcher(IAlgoStringMatcher matcher) {
        Config.matcher = matcher;
    }

    public static void setMatcher(String m) {
        switch (m.toLowerCase()) {
            case "rk" -> setMatcher(new RobinKarpAlgoMatcherImpl());
            case "kmp" -> setMatcher(new KnuthMorrisPrattAlgoMatcherImpl());
            case "naive" -> setMatcher(new NaiveAlgoMatcherImpl());
        }
    }
}
