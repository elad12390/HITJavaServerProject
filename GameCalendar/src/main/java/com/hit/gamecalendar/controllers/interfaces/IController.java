package main.java.com.hit.gamecalendar.controllers.interfaces;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

public interface IController {
    static String name() {return "";}
    static Gson getGson(Gson g) {return null;}
    static Gson setGson() {return null;}
}
