package com.hit.gamecalendar.main.java.common.logger;

import com.hit.gamecalendar.main.java.common.enums.EAnsiColor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class Logger implements ILogger {
    public static ELoggingLevel loggingLevel;
    private static final Function<String, String> FORMAT =
            (String s) ->
                EAnsiColor.ANSI_GREEN.getAnsiCharacter() +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) +
                " ~ $" + EAnsiColor.ANSI_RESET.getAnsiCharacter() + s;

    public static void setLoggingLevel(ELoggingLevel loggingLevel) {
        Logger.loggingLevel = loggingLevel;
    }

    public static void logInformation(String s) {
        if (loggingLevel.compareNum(ELoggingLevel.INFORMATION) >= 0)
            System.out.println(FORMAT.apply(EAnsiColor.ANSI_PURPLE.getAnsiCharacter() + "[INFO]" + EAnsiColor.ANSI_RESET.getAnsiCharacter() + ": " + s));
    }

    public static void logDebug(String s) {
        if (loggingLevel.compareNum(ELoggingLevel.DEBUG) >= 0)
            System.out.println(FORMAT.apply(EAnsiColor.ANSI_BLUE.getAnsiCharacter() + "[DEBUG]" + EAnsiColor.ANSI_RESET.getAnsiCharacter() + ": " + s));
    }

    public static void logError(String s) {
        var st = Thread.currentThread().getStackTrace();
        var callerMethod = st[2];

        var callerMethodName =  callerMethod.getMethodName();
        var callerClassPath = callerMethod.getClassName().split("\\.");
        var callerClassName = callerClassPath[callerClassPath.length - 1];
        if (loggingLevel.compareNum(ELoggingLevel.ERROR) >= 0)
            System.out.println(FORMAT.apply(EAnsiColor.ANSI_RED.getAnsiCharacter() + "[ERROR]" + EAnsiColor.ANSI_RESET.getAnsiCharacter() + ": Error in " + callerClassName + " in method " + callerMethodName + s));
    }
}
