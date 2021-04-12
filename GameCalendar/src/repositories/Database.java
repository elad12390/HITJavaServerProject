package repositories;

import repositories.interfaces.IDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements IDatabase {
    String _connectionString = "";
    String _username = "";
    String _pass = "";

    public Database(String connectionString, String username, String password) {
        this._connectionString = connectionString;
        this._username = username;
        this._pass = password;
    }

    @Override
    public String connectionString() {
        return _connectionString;
    }

    @Override
    public List<Map<String, Object>> selectAllFrom(String table) {
        try {
            // connect to database.
            Connection connection = DriverManager.getConnection(this.connectionString(), this._username, this._pass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from `game-calendar`." + table);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            final int columnCount = resultSetMetaData.getColumnCount();

            List<Map<String, Object>> results = new ArrayList<>(columnCount);

            while (resultSet.next()) {
                HashMap<String, Object> rowValues = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowValues.put(resultSetMetaData.getColumnName(i), resultSet.getObject(i));
                }
                results.add(rowValues);
            }

            return results;
        } catch (Exception e) {
            System.out.println("Exception occured in database, " + e);
            return null;
        }
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
