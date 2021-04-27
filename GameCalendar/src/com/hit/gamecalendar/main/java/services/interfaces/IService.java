package com.hit.gamecalendar.main.java.services.interfaces;

import com.hit.gamecalendar.main.java.repositories.interfaces.IRepository;

public interface IService<M, R extends IRepository<M>> {
    R getRepository();
    void setRepository(R repo);
}
