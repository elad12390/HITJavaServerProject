package main.java.com.hit.gamecalendar.dao;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.dao.interfaces.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MysqlDatabase implements IDatabase {
    String _connectionString;
    String _username;
    String _pass;
    String _schemaName;

    public MysqlDatabase(String connectionString, String username, String password, String schemaName) {
        this._connectionString = connectionString;
        this._username = username;
        this._pass = password;
        this._schemaName = schemaName;
    }

    // *********************************** Public Functions ************************************ //

    @Override
    public String connectionString() {
        return _connectionString;
    }

    @Override
    public <T> List<T> getAllTableItems(String table, T data) {
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
                results.add(gson.fromJson(obj.toString(), (Class<T>)data.getClass()));
            }

            return results;
        } catch (Exception e) {
            Startup.logger.logError("Exception occured in database, " + e);
            return null;
        }
    }

    @Override
    public <T> T getTableItemById(String table, int id, T data) {
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
                return  gson.fromJson(obj.toString(), (Class<T>)data.getClass());
            }
            return null;
        } catch (Exception e) {
            Startup.logger.logError("Exception occurred in database: " + e);
            return null;
        }
    }

    @Override
    public <T> Long createTableItem(String table, T data) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            StringBuilder query = new StringBuilder("INSERT INTO `" + _schemaName + "`." + table + " VALUES (");
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

            statement.executeUpdate(query.toString(), Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            long id = -1L;
            if (rs.next()) {
                id = rs.getLong(1);
                Startup.logger.logInformation("Inserted Id " + id + " into table " + table); // display inserted record
            }

            return id;
        } catch (Exception e) {
            Startup.logger.logError("Exception occured in database: " + e);
            return -1L;
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
            Startup.logger.logError("Exception occurred in database: " + e);
            return false;
        }
    }

    @Override
    public boolean deleteFromTable(String table, int id) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            var resultSet = statement.executeUpdate("DELETE FROM `" + _schemaName + "`." + table + " WHERE id = " + id);

            return resultSet > 0;
        } catch (Exception e) {
            Startup.logger.logError("Exception occured in database: " + e);
            return false;
        }
    }

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


    // *********************************** Private Functions ************************************ //

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
