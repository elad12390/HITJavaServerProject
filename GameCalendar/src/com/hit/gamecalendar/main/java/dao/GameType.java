package com.hit.gamecalendar.main.java.dao;

import com.google.gson.annotations.SerializedName;

public enum GameType {
    None(0),
    League(1),
    Trophy(2),
    Playoff(3);

    private final int value;

    private GameType(int value) {
        this.value = value;
    }

    public static GameType fromValue(int value) {
        for(GameType type : GameType.values()) {
            if(type.getValue() == value) {
                return type;
            }
        }
        return null;
    }


    public int getValue() {
        return value;
    }
}
