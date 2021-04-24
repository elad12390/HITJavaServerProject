package main.java.com.hit.gamecalendar.controllers.abstracts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import main.java.com.hit.gamecalendar.controllers.interfaces.IController;
import main.java.com.hit.gamecalendar.dao.GameModel;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseController implements IController {

    public BaseController() {
    }

    protected static String getBodyAsText(HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1) {
            requestBody.append((char) i);
        }
        return requestBody.toString();
    }

    protected static <T> T getBodyAsEntity(HttpExchange exchange, T t)  throws IOException {
        StringBuilder requestBody = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1) {
            requestBody.append((char) i);
        }
        return (new Gson()).fromJson(getBodyAsText(exchange), (Class<T>)t.getClass());
    }
}
