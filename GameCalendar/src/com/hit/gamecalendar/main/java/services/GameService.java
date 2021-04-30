package com.hit.gamecalendar.main.java.services;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.repositories.GameRepository;
import com.hit.gamecalendar.main.java.services.abstracts.BaseService;

import java.util.List;

public class GameService extends BaseService<GameModel, GameRepository> {

    public GameService() {
        super(new GameRepository());
    }

    public List<GameModel> getAllGames() {
        return this.repo.getAll();
    }

    public GameModel getGameById(int id) {
        return this.repo.getItemById(id);
    }

    public Long createGame(GameModel gameModel) {
        return this.repo.createTableItem(gameModel);
    }

    public boolean updateGame(int id, GameModel gameModel) {
        return this.repo.updateTableItem(id, gameModel);
    }

    public boolean deleteGame(int id) {
        return this.repo.deleteTableItem(id);
    }
}
