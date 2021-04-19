package repositories.interfaces;

import java.util.List;
import java.util.Map;

public interface IDatabase {
    public String connectionString();
    public <T> List<T> selectAllFrom(String table, T data);
    public <T> T getById(String table, int id, T data);
    public <T> T addToTable(String table, T item);
    public boolean deleteFromTable(String table, int id);
}
