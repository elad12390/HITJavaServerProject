package main.java.com.hit.gamecalendar.services.interfaces;

import main.java.com.hit.gamecalendar.repositories.interfaces.IRepository;

public interface IService<M, R extends IRepository<M>> {
    R getRepository();
    void setRepository(R repo);
}
