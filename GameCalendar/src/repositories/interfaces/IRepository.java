package repositories.interfaces;

import java.util.List;

public interface IRepository<E> {
    String tableName();
    List<E> getAll();
    List<E> getItem(int id);
}
