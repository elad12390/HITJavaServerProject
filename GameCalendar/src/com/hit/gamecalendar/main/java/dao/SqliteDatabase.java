package com.hit.gamecalendar.main.java.dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.dao.interfaces.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.function.Function;


public class SqliteDatabase implements IDatabase {
    String _connectionString;

    // allow only one query at a time to the database
    private Semaphore semaphore = new Semaphore(1);

    public SqliteDatabase(String connectionString) {
        this._connectionString = connectionString;
    }

    // *********************************** Public Functions ************************************ //

    @Override
    public String connectionString() {
        return _connectionString;
    }

    @Override
    public <T> List<T> getAllTableItems(String table, T data) {
        Connection connection = createConnection(false);
        if (connection == null) return null;
        try {
            // connect to database.

            var statement = connection.prepareStatement("select * from "+ table);
            ResultSet resultSet = statement.executeQuery();

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();
            var gson = new Gson();


            List<T> results = new ArrayList<>(columnCount);
            while (resultSet.next()) {
                JsonObject obj = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, gson, i);
                }
                results.add(gson.fromJson(obj.toString(), (Class<T>)data.getClass()));
            }

            return results;
        } catch (Exception e) {
            Logger.logError("Exception occurred in database, " + e);
            return null;
        } finally {
            closeConnection(connection, false);
        }
    }

    @Override
    public <T> T getTableItemById(String table, int id, T data) {
        Connection connection = createConnection(false);
        if (connection == null) return null;

        try {
            // connect to database.
            final var statement = connection.prepareStatement("select * from "+ table + " where id=" + id);
            final var resultSet = statement.executeQuery();
            final var resultSetMetaData = resultSet.getMetaData();
            final var columnCount = resultSetMetaData.getColumnCount();
            final var gson = new Gson();

            if (resultSet.next()) {
                JsonObject obj = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, gson, i);
                }
                return  gson.fromJson(obj.toString(), (Class<T>)data.getClass());
            }

            return null;
        } catch (Exception e) {
            Logger.logError("Exception occurred in database: " + e);
            return null;
        } finally {
            closeConnection(connection, false);
        }
    }

    @Override
    public <T> Long createTableItem(String table, T data) {
        Connection connection = createConnection(true);
        if (connection == null) return null;

        try {
            StringBuilder query = new StringBuilder("INSERT INTO " + table + " VALUES (");
            Gson gson = new Gson();

            var json = gson.toJson(data);
            Map<String, Object> objMap = gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType());
            String[] keys = objMap.keySet().toArray(new String[0]);
            for (int i = 0; i < keys.length; i++) {
                var key = keys[i];
                var mapObject = objMap.get(key);
                if (key.equals("id")) {
                        query.append("null,");
                    continue;
                }

                if (mapObject instanceof String)
                    query.append("'").append(mapObject).append("'");
                else
                    query.append(mapObject);
                if (i < (keys.length - 1))
                    query.append(",");
            }
            query.append(")");

            var statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            long id = -1L;
            if (rs.next()) {
                id = rs.getLong(1);
                Logger.logInformation("Inserted Id " + id + " into table " + table); // display inserted record
            }

            return id;
        } catch (Exception e) {
            Logger.logError("Exception occured in database: " + e);
            return -1L;
        } finally {
            closeConnection(connection, true);
        }
    }

    @Override
    public <T> boolean updateTableItem(String table, int id, T data) {
        Connection connection = createConnection(true);
        if (connection == null) return false;

        try {

            StringBuilder query = new StringBuilder("UPDATE " + table + " SET ");
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

            var statement = connection.prepareStatement(query.toString());
            var resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (Exception e) {
            Logger.logError("Exception occurred in database: " + e);
            return false;
        } finally {
            closeConnection(connection, true);
        }
    }

    @Override
    public boolean deleteFromTable(String table, int id) {
        Connection connection = createConnection(true);
        if (connection == null) return false;

        try {
            var statement = connection.prepareStatement("DELETE FROM " + table + " WHERE id = " + id);
            var resultSet = statement.executeUpdate();

            return resultSet > 0;
        } catch (Exception e) {
            Logger.logError("Exception occured in database: " + e);
            return false;
        } finally {
            closeConnection(connection, true);
        }
    }

    // *********************************** Private Functions ************************************ //


    private <T> JsonObject putData(
            T data, ResultSet resultSet, ResultSetMetaData resultSetMetaData,
            JsonObject obj, Gson gson, int i
    ) throws NoSuchFieldException, SQLException {

        var f = data.getClass().getDeclaredField(resultSetMetaData.getColumnName(i));
        f.setAccessible(true);

        switch (resultSetMetaData.getColumnType(i)) {
            case Types.TINYINT, Types.SMALLINT, Types.INTEGER, Types.BIGINT -> addIntegerToObj(resultSetMetaData, obj, i, resultSet.getInt(i));

            case Types.REAL, Types.DOUBLE, Types.NUMERIC, Types.DECIMAL -> addDoubleToObj(resultSetMetaData, obj, i, resultSet.getDouble(i));

            case Types.FLOAT -> addFloatToObj(resultSetMetaData, obj, i, resultSet.getFloat(i));

            case Types.BIT, Types.BOOLEAN -> addBoolean(resultSet, resultSetMetaData, obj, i);

            case Types.CHAR -> addCharToObj(resultSet, resultSetMetaData, obj, i);

            default -> addChildObj(resultSet, resultSetMetaData, obj, gson, i);
        }

        return obj;
    }

    private Connection createConnection(boolean lock) {
        try {
            this.semaphore.acquire();
            return DriverManager.getConnection(this.connectionString());
        } catch (Exception throwables) {
            Logger.logError("Connection with database could not be established");
            throwables.printStackTrace();
        }
        return null;
    }

    private void closeConnection(Connection connection, boolean release) {
        try {
            connection.close();
            semaphore.release();
        } catch (SQLException throwables) {
            Logger.logError("Could not close sql connection");
            throwables.printStackTrace();
        }
    }

    private void addChildObj(ResultSet resultSet, ResultSetMetaData resultSetMetaData, JsonObject obj, Gson gson, int i) throws SQLException {
        obj.add(resultSetMetaData.getColumnName(i), gson.toJsonTree(resultSet.getObject(i)));
    }

    private void addCharToObj(ResultSet resultSet, ResultSetMetaData resultSetMetaData, JsonObject obj, int i) throws SQLException {
        obj.addProperty(resultSetMetaData.getColumnName(i), (char) resultSet.getObject(i));
    }

    private void addDoubleToObj(ResultSetMetaData resultSetMetaData, JsonObject obj, int i, double aDouble) throws SQLException {
        obj.addProperty(resultSetMetaData.getColumnName(i), aDouble);
    }

    private void addFloatToObj(ResultSetMetaData resultSetMetaData, JsonObject obj, int i, double aFloat) throws SQLException {
        obj.addProperty(resultSetMetaData.getColumnName(i), aFloat);
    }

    private void addIntegerToObj(ResultSetMetaData resultSetMetaData, JsonObject obj, int i, float anInt) throws SQLException {
        obj.addProperty(resultSetMetaData.getColumnName(i), anInt);
    }

    private void addBoolean(ResultSet resultSet, ResultSetMetaData resultSetMetaData, JsonObject obj, int i) throws SQLException {
        obj.addProperty(resultSetMetaData.getColumnName(i), resultSet.getBoolean(i));
    }
}
