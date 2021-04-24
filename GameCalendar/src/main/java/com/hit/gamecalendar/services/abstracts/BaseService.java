package main.java.com.hit.gamecalendar.services.abstracts;

import main.java.com.hit.gamecalendar.repositories.interfaces.IRepository;
import main.java.com.hit.gamecalendar.services.interfaces.IService;

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
