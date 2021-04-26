package main.java.com.hit.gamecalendar.controllers.interfaces;

import com.google.gson.Gson;

public interface IController {
    static String name() {return "";}
    static Gson getGson(Gson g) {return null;}
    static Gson setGson() {return null;}
}
