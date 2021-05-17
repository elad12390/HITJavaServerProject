package com.hit.gamecalendar.main.java.repositories;

import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.dao.TeamModel;
import com.hit.gamecalendar.main.java.dao.TeamModel;
import com.hit.gamecalendar.main.java.repositories.abstracts.BaseRepository;
import com.hit.gamecalendar.main.java.repositories.interfaces.IRepository;

import java.util.List;

public class TeamRepository extends BaseRepository implements IRepository<TeamModel> {
    public TeamRepository() {
        super();
    }

    @Override
    public String tableName() { return "Team"; }

    @Override
    public List<TeamModel> getAll() {

        List<TeamModel> data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            data = this.cache.get(tableName);
        } else {
            data = Startup.db.getAllTableItems(tableName, new TeamModel());
            this.cache.set(tableName, data);
        }

        return data;
    }

    @Override
    public TeamModel getItemById(int id) {
        TeamModel data;
        final String tableName = this.tableName();

        if(this.cache.exists(tableName)) {
            List<TeamModel> allData = this.cache.get(tableName);

            data = allData.stream()
                    .filter((game) -> game.getId() ==  id)
                    .findAny()
                    .orElse(null);
        } else {
            data = Startup.db.getTableItemById(tableName, id, new TeamModel());
        }
        return data;
    }

    @Override
    public Long createTableItem(TeamModel m) {
        final String tableName = this.tableName();

        this.cache.remove(tableName);
        return Startup.db.createTableItem(tableName, m);
    }

    @Override
    public boolean updateTableItem(int id, TeamModel m) {
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
