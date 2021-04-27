package com.hit.gamecalendar.main.java.common.logger;

import com.hit.gamecalendar.main.java.common.enums.AnsiColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Logger implements ILogger {
    private static final Function<String, String> FORMAT = (String s) -> LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " -- " + s;

    public static void logInformation(String s) {
        System.out.println(FORMAT.apply(AnsiColor.ANSI_PURPLE.getAnsiCharacter() + "[INFO]" + AnsiColor.ANSI_RESET.getAnsiCharacter() + ": " + s));
    }

    public static void logDebug(String s) {
        System.out.println(FORMAT.apply(AnsiColor.ANSI_BLUE.getAnsiCharacter() + "[DEBUG]" + AnsiColor.ANSI_RESET.getAnsiCharacter() + ": " + s));
    }

    public static void logError(String s) {
        var st = Thread.currentThread().getStackTrace();
        var callerMethod = st[2];

        var callerMethodName =  callerMethod.getMethodName();
        var callerClassPath = callerMethod.getClassName().split("\\.");
        var callerClassName = callerClassPath[callerClassPath.length - 1];
        System.out.println(FORMAT.apply(AnsiColor.ANSI_RED.getAnsiCharacter() + "[ERROR]" + AnsiColor.ANSI_RESET.getAnsiCharacter() + ": Error in " + callerClassName + " in method " + callerMethodName + s));
    }
}
