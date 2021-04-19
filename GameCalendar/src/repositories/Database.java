package repositories;

import org.json.simple.JSONObject;
import repositories.interfaces.IDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.json.simple.JSONValue.parse;

public class Database implements IDatabase {
    String _connectionString = "";
    String _username = "";
    String _pass = "";
    String _schemaName = "";

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

            List<T> results = new ArrayList<>(columnCount);
            while (resultSet.next()) {
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, i);
                }
                results.add((T) parse(obj.toJSONString()));
            }

            return results;
        } catch (Exception e) {
            System.out.println("Exception occured in database, " + e);
            return null;
        }
    }

    @Override
    public <T> T getById(String table, int id, T data) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from `"+ _schemaName + "`." + table + " where id=" + id);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();

            if (resultSet.next()) {
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    putData(data, resultSet, resultSetMetaData, obj, i);
                }
                return (T)parse(obj.toJSONString());
            }

            return null;
        } catch (Exception e) {
            System.out.println("Exception occured in database, " + e);
            return null;
        }
    }

    private <T> JSONObject putData(T data, ResultSet resultSet, ResultSetMetaData resultSetMetaData, JSONObject obj, int i) throws NoSuchFieldException, SQLException {
        var f = data.getClass().getDeclaredField(resultSetMetaData.getColumnName(i));
        f.setAccessible(true);

        switch (resultSetMetaData.getColumnType(i)) {
            case Types.BIT:
            case Types.BOOLEAN:
                obj.put(resultSetMetaData.getColumnName(i), resultSet.getBoolean(i));
                break;
            case Types.TINYINT:
            case Types.SMALLINT:
            case Types.INTEGER:
            case Types.BIGINT:
                obj.put(resultSetMetaData.getColumnName(i), resultSet.getInt(i));
                break;
            case Types.FLOAT:
                obj.put(resultSetMetaData.getColumnName(i), resultSet.getFloat(i));
                break;
            case Types.REAL:
            case Types.DOUBLE:
            case Types.NUMERIC:
            case Types.DECIMAL:
                obj.put(resultSetMetaData.getColumnName(i), resultSet.getDouble(i));
                break;
            case Types.CHAR:
                var value = resultSet.getObject(i);
                obj.put(resultSetMetaData.getColumnName(i), (char)value);
                break;
            default:
                obj.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                break;
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
