package main.java.com.hit.gamecalendar.common.http.responses;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.common.exceptions.NotFoundException;

import java.io.OutputStream;
import java.net.HttpURLConnection;

public class HttpResponseFactory {
    /**
     * Creates a json OK response from supplied data object
     * @param exchange Http Exchange to pass the results to
     * @param data The data to stringify
     * @param <T> The type of the data object
     */
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

    /**
     * Creates a json Exception response from supplied message and Exception
     * @param exchange Http Exchange to pass the results to
     * @param message A String message from the user to pass to the client
     * @param e An exception to be sent to the client
     * @param <E> The exception type thrown
     */
    public static <E extends Exception> void CreateExceptionResponse(HttpExchange exchange, String message, E e) {
        try {
            Gson gson = new Gson();
            var errorResponse = new HttpExceptionResponse<>(message, e);
            String respText = gson.toJson(errorResponse);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }

    /**
     * Creates a json Exception response from supplied message and Exception
     * @param exchange Http Exchange to pass the results to
     * @param e An exception to be sent to the client
     * @param <E> The exception type thrown
     */
    public static <E extends Exception> void CreateExceptionResponse(HttpExchange exchange, E e) {
        try {
            Gson gson = new Gson();
            String respText = gson.toJson(new HttpExceptionResponse<>(e));

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }

    /**
     * Creates an HTTP not found response
     * @param exchange Http Exchange to pass the results to
     * @param message A String message from the user to pass to the client
     */
    public static void CreateNotFoundResponse(HttpExchange exchange, String message) {
        try {
            Gson gson = new Gson();
            var errorResponse = new HttpNotFoundResponse<>(message);
            String respText = gson.toJson(errorResponse);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }

    /**
     * Creates an HTTP not found response
     * @param exchange Http Exchange to pass the results to
     * @param message A String message from the user to pass to the client
     * @param e The exception to return to the user
     * @param <E> A NotFoundException representing what happened and the stacktrace
     */
    public static <E extends NotFoundException> void CreateNotFoundResponse(HttpExchange exchange, String message, E e) {
        try {
            Gson gson = new Gson();
            var errorResponse = new HttpNotFoundResponse<>(message, e);
            String respText = gson.toJson(errorResponse);

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, respText.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();
        }
        catch (Exception err) {
            Startup.logger.logError("Could not create http response: " + err);
        }
    }
}
