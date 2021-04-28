package com.hit.gamecalendar.main.java.repositories;

import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.repositories.abstracts.BaseRepository;
import com.hit.gamecalendar.main.java.repositories.interfaces.IRepository;

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

        List<GameModel> data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            data = this.cache.get(tableName);
        } else {
            data = Startup.db.getAllTableItems(tableName, new GameModel());
            this.cache.set(tableName, data);
        }

        return data;
    }

    @Override
    public GameModel getItemById(int id) {
        GameModel data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            List<GameModel> allData = this.cache.get(tableName);

            data = allData.stream()
                    .filter((game) -> game.id ==  id)
                    .findAny()
                    .orElse(null);
        } else {
            data = Startup.db.getTableItemById(tableName, id, new GameModel());
        }
        return data;
    }

    @Override
    public Long createTableItem(GameModel m) {
        final String tableName = this.tableName();

        this.cache.remove(tableName);
        return Startup.db.createTableItem(tableName, m);
    }

    @Override
    public boolean updateTableItem(int id, GameModel m) {
        final String tableName = this.tableName();

        this.cache.remove(tableName);
        return Startup.db.updateTableItem(tableName(), id, m);
    }

    @Override
    public boolean deleteTableItem(int id) {
        final String tableName = this.tableName();

        this.cache.remove(tableName);
        return Startup.db.deleteFromTable(tableName(), id);
    }
}
