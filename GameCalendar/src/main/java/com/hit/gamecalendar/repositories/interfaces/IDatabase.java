package main.java.com.hit.gamecalendar.repositories.interfaces;

import java.util.List;

public interface IDatabase {
    String connectionString();
    <T> List<T> selectAllFrom(String table, T data);
    <T> T getById(String table, int id, T data);
    <T> T addToTable(String table, T item);
    <T> boolean updateTableItem(String table, int id, T data);
    boolean deleteFromTable(String table, int id);
}
