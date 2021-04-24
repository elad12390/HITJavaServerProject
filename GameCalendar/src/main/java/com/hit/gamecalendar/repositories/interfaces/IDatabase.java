package main.java.com.hit.gamecalendar.repositories.interfaces;

import main.java.com.hit.gamecalendar.dao.GameModel;

import java.util.List;

public interface IDatabase {
    String connectionString();
    <T> List<T> getAllTableItems(String table, T data);
    <T> T getTableItemById(String table, int id, T data);
    <T> Long createTableItem(String tableName, T m);
    <T> boolean updateTableItem(String table, int id, T data);
    boolean deleteFromTable(String table, int id);
}
