package main.java.com.hit.gamecalendar.common.http.responses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.java.com.hit.gamecalendar.Startup;

import java.io.OutputStream;

public class HttpResponseFactory {
    public static <T> void CreateJsonResponse(HttpExchange exchange, T data) {
        try {
            Gson gson = new Gson();
            String respText = gson.toJson(new HttpOKResponse<>(data));

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }
    public static <E extends Exception> void CreateErrorResponse(HttpExchange exchange, String message, E e) {
        try {
            Gson gson = new Gson();
            var errorResponse = new HttpExceptionResponse<>(message, e);
            String respText = gson.toJson(errorResponse);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }
    public static <E extends Exception> void CreateNotFoundResponse(HttpExchange exchange, String message, E e) {
        try {
            Gson gson = new Gson();
            var errorResponse = new HttpNotFoundResponse<>(message, e);
            String respText = gson.toJson(errorResponse);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }

    public static <E extends Exception> void CreateErrorResponse(HttpExchange exchange, E e) {
        try {
            Gson gson = new Gson();
            String respText = gson.toJson(new HttpExceptionResponse<>(e));

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }
}
