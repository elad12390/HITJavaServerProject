package com.hit.gamecalendar.main.java.dao.interfaces;

import java.util.List;

public interface IDatabase {
    String connectionString();
    <T> List<T> getAllTableItems(String table, T data);
    <T> T getTableItemById(String table, int id, T data);
    <T> Long createTableItem(String tableName, T m);
    <T> boolean updateTableItem(String table, int id, T data);
    boolean deleteFromTable(String table, int id);
}
