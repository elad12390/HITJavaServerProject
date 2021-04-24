package main.java.com.hit.gamecalendar.repositories.abstracts;

import main.java.com.hit.gamecalendar.dao.Database;

public abstract class BaseRepository {
    protected Database db;

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/?user=root";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";
    private static final String TABLE_NAME = "game-calendar";

    protected BaseRepository() {
        db = new Database(CONNECTION_STRING, USERNAME, PASSWORD, TABLE_NAME);
    }
}
