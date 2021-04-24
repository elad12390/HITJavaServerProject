package main.java.com.hit.gamecalendar.repositories;

import main.java.com.hit.gamecalendar.dao.GameModel;
import main.java.com.hit.gamecalendar.repositories.abstracts.BaseRepository;
import main.java.com.hit.gamecalendar.repositories.interfaces.IRepository;

import java.util.List;

public class GameRepository extends BaseRepository implements IRepository<GameModel> {
    public GameRepository() {
        super();
    }

    @Override
    public String tableName() {
        return "games";
    }

    @Override
    public List<GameModel> getAll() {
        return db.selectAllFrom(tableName(), new GameModel());
    }

    @Override
    public GameModel getItem(int id) {
        return db.getById(tableName(), id, new GameModel());
    }

    @Override
    public boolean updateTableItem(int id, GameModel m) {
        return db.updateTableItem(tableName(), id, m);
    }
}
