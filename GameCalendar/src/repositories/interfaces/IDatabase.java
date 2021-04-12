package repositories.interfaces;

import java.util.List;
import java.util.Map;

public interface IDatabase {
    public String connectionString();
    public <T> List<Map<String, Object>> selectAllFrom(String table);
    public <T> T addToTable(String table, T item);
    public boolean deleteFromTable(String table, int id);
}
