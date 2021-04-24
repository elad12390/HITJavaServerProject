package main.java.com.hit.gamecalendar.repositories.interfaces;

import java.util.List;

public interface IRepository<E> {
    String tableName();
    List<E> getAll();
    E getItemById(int id);
    boolean updateTableItem(int id, E item);
    Long createTableItem(E m);
    boolean deleteTableItem(int id);
}
