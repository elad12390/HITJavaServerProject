package com.hit.gamecalendar.main.java.repositories;

import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.dao.PlayerModel;
import com.hit.gamecalendar.main.java.repositories.abstracts.BaseRepository;
import com.hit.gamecalendar.main.java.repositories.interfaces.IRepository;

import java.util.List;

public class PlayerRepository extends BaseRepository implements IRepository<PlayerModel> {
    public PlayerRepository() {
        super();
    }


    @Override
    public String tableName() {
        return "Player";
    }

    @Override
    public List<PlayerModel> getAll() {

        List<PlayerModel> data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            data = this.cache.get(tableName);
        } else {
            data = Startup.db.getAllTableItems(tableName, new PlayerModel());
            this.cache.set(tableName, data);
        }

        return data;
    }

    @Override
    public PlayerModel getItemById(int id) {
        PlayerModel data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            List<PlayerModel> allData = this.cache.get(tableName);

            data = allData.stream()
                    .filter((game) -> game.getId() ==  id)
                    .findAny()
                    .orElse(null);
        } else {
            data = Startup.db.getTableItemById(tableName, id, new PlayerModel());
        }
        return data;
    }

    @Override
    public Long createTableItem(PlayerModel m) {
        final String tableName = this.tableName();

        this.cache.remove(tableName);
        return Startup.db.createTableItem(tableName, m);
    }

    @Override
    public boolean updateTableItem(int id, PlayerModel m) {
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
