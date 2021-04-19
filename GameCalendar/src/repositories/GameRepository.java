package repositories;

import models.GameModel;
import repositories.interfaces.IRepository;

import java.util.List;

public class GameRepository extends BaseRepository implements IRepository {
    public GameRepository() {
        super();
    }

    @Override
    public String tableName() {
        return "games";
    }

    @Override
    public List getAll() {
        return db.selectAllFrom(tableName(), new GameModel());
    }

    @Override
    public List getItem(int id) {
        return db.selectAllFrom(tableName(), new GameModel());
    }
}
