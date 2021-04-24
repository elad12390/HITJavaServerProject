package main.java.com.hit.gamecalendar.dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.repositories.interfaces.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Database implements IDatabase {
    String _connectionString;
    String _username;
    String _pass;
    String _schemaName;

    public Database(String connectionString, String username, String password, String tableName) {
        this._connectionString = connectionString;
        this._username = username;
        this._pass = password;
        this._schemaName = tableName;
    }

    @Override
    public String connectionString() {
        return _connectionString;
    }

    @Override
    public <T> List<T> selectAllFrom(String table, T data) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from `"+ _schemaName + "`." + table);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();
            var gson = new Gson();


            List<T> results = new ArrayList<>(columnCount);
            while (resultSet.next()) {
                JsonObject obj = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, gson, i);
                }
                results.add((T) gson.fromJson(obj.toString(), data.getClass()));
            }

            return results;
        } catch (Exception e) {
            Startup.logger.logError("Exception occured in database, " + e);
            return null;
        }
    }

    @Override
    public <T> T getById(String table, int id, T data) {
        try {
            // connect to database.
            final var connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            final var statement = connection.createStatement();
            final var resultSet = statement.executeQuery("select * from `"+ _schemaName + "`." + table + " where id=" + id);
            final var resultSetMetaData = resultSet.getMetaData();
            final var columnCount = resultSetMetaData.getColumnCount();
            final var gson = new Gson();

            if (resultSet.next()) {
                JsonObject obj = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, gson, i);
                }
                return (T) gson.fromJson(obj.toString(), data.getClass());
            }

            return null;
        } catch (Exception e) {
            Startup.logger.logError("Exception occurred in database: " + e);
            return null;
        }
    }

    @Override
    public <T> boolean updateTableItem(String table, int id, T data) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            StringBuilder query = new StringBuilder("UPDATE `" + _schemaName + "`." + table + " SET ");
            Gson gson = new Gson();

            var json = gson.toJson(data);
            Map<String, Object> objMap = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            String[] keys = objMap.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                var key = keys[i];
                var mapObject = objMap.get(key);

                query.append(key).append("=");
                if (mapObject instanceof String)
                    query.append("'").append(mapObject).append("'");
                else
                    query.append(mapObject);
                if (i < (keys.length - 1))
                    query.append(",");
            }
            query.append(" WHERE id = ").append(id);

            var resultSet = statement.executeUpdate(query.toString());


            return resultSet > 0;
        } catch (Exception e) {
            Startup.logger.logError("Exception occured in database: " + e);
            return false;
        }
    }

    private <T> JsonObject putData(
            T data,
            ResultSet resultSet,
            ResultSetMetaData resultSetMetaData,
            JsonObject obj,
            Gson gson,
            int i
    ) throws NoSuchFieldException, SQLException {

        var f = data.getClass().getDeclaredField(resultSetMetaData.getColumnName(i));
        f.setAccessible(true);

        switch (resultSetMetaData.getColumnType(i)) {
            case Types.BIT, Types.BOOLEAN -> obj.addProperty(resultSetMetaData.getColumnName(i), resultSet.getBoolean(i));
            case Types.TINYINT, Types.SMALLINT, Types.INTEGER, Types.BIGINT -> obj.addProperty(resultSetMetaData.getColumnName(i), resultSet.getInt(i));
            case Types.FLOAT -> obj.addProperty(resultSetMetaData.getColumnName(i), resultSet.getFloat(i));
            case Types.REAL, Types.DOUBLE, Types.NUMERIC, Types.DECIMAL -> obj.addProperty(resultSetMetaData.getColumnName(i), resultSet.getDouble(i));
            case Types.CHAR -> {
                var value = resultSet.getObject(i);
                obj.addProperty(resultSetMetaData.getColumnName(i), (char) value);
            }
            default -> obj.add(resultSetMetaData.getColumnName(i), gson.toJsonTree(resultSet.getObject(i)));
        }
        return obj;
    }

    @Override
    public <T> T addToTable(String table, T item) {
        return null;
    }


    @Override
    public boolean deleteFromTable(String table, int id) {
        return false;
    }
}
