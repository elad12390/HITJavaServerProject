package com.hit.gamecalendar.main.java.services.abstracts;

import com.hit.gamecalendar.main.java.repositories.interfaces.IRepository;
import com.hit.gamecalendar.main.java.services.interfaces.IService;

public abstract class BaseService<M, R extends IRepository<M>> implements IService<M, R> {

    protected R repo;

    protected BaseService(R repository) {
        this.setRepository(repository);
    }

    @Override
    public R getRepository() {
        return this.repo;
    }

    @Override
    public void setRepository(R repo) {
        this.repo = repo;
    }
}
