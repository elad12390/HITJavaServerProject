package main.java.com.hit.gamecalendar.repositories.interfaces;

import java.util.List;

public interface IRepository<E> {
    String tableName();
    List<E> getAll();
    E getItem(int id);
    boolean updateTableItem(int id, E item);
}
