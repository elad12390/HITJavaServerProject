package com.hit.gamecalendar.main.java.dao;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class GameTypeDeserializer implements JsonDeserializer<GameType> {
    @Override
    public GameType deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        int key = jsonElement.getAsInt();
        return GameType.fromValue(key);
    }
}
