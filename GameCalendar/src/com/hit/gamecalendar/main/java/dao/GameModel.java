package com.hit.gamecalendar.main.java.dao;

public class GameModel {
    public int id;
    public String cool_name;
    public GameModel() {}

    @Override
    public String toString() {
        return "GameModel{" +
                "id=" + id +
                ", cool_name='" + cool_name + '\'' +
                '}';
    }
}
