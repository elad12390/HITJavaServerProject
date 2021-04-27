package com.hit.gamecalendar.main.java.controllers.abstracts;

import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.controllers.interfaces.IController;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

public abstract class BaseController implements IController {

    public BaseController() {
    }

    protected static String getBodyAsText(HttpExchange exchange) throws IOException, InterruptedException {
        Thread.sleep(10);
        StringBuilder requestBody = new StringBuilder();
        InputStream ios = exchange.getRequestBody();
        int i;
        while ((i = ios.read()) != -1) {
            requestBody.append((char) i);
        }
        return requestBody.toString();
    }

    protected static <T> T getBodyAsEntity(HttpExchange exchange, T t) {
        try {
            var json = getBodyAsText(exchange);
            return (new Gson()).fromJson(json, (Class<T>)t.getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
