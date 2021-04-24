package main.java.com.hit.gamecalendar.common;

import main.java.com.hit.gamecalendar.common.interfaces.ILogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Logger implements ILogger {
    private static final Function<String, String> FORMAT = (String s) -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " -- " + s;
    @Override
    public void logInformation(String s) {
        System.out.println(FORMAT.apply("[INFO]: " + s));
    }

    @Override
    public void logDebug(String s) {
        System.out.println(FORMAT.apply("[DEBUG]: " + s));
    }

    @Override
    public void logError(String s) {
        System.out.println(FORMAT.apply("[ERROR]: " + s));
    }
}
