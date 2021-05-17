package com.hit.gamecalendar.main.java.services;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.dao.PlayerModel;
import com.hit.gamecalendar.main.java.repositories.GameRepository;
import com.hit.gamecalendar.main.java.repositories.PlayerRepository;
import com.hit.gamecalendar.main.java.services.abstracts.BaseService;

import java.util.List;

public class PlayerService extends BaseService<PlayerModel, PlayerRepository> {

    public PlayerService() {
        super(new PlayerRepository());
    }

    public List<PlayerModel> getAllPlayers() {
        return this.repo.getAll();
    }

    public PlayerModel getPlayerById(int id) {
        return this.repo.getItemById(id);
    }

    public Long createPlayer(PlayerModel player) {
        return this.repo.createTableItem(player);
    }

    public boolean updatePlayer(int id, PlayerModel player) {
        return this.repo.updateTableItem(id, player);
    }

    public boolean deletePlayer(int id) {
        return this.repo.deleteTableItem(id);
    }
}
