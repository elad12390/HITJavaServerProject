package main.java.com.hit.gamecalendar.services;
import main.java.com.hit.gamecalendar.dao.GameModel;
import main.java.com.hit.gamecalendar.repositories.GameRepository;
import main.java.com.hit.gamecalendar.services.abstracts.BaseService;

import java.util.List;

public class GameService extends BaseService<GameModel, GameRepository> {
    public GameService() {
        super(new GameRepository());
    }

    public List<GameModel> getAllGames() {
        return this.repo.getAll();
    }

    public GameModel getGameById(int id) {
        return this.repo.getItem(id);
    }

    public boolean updateGame(int id, GameModel gameModel) {
        return this.repo.updateTableItem(id, gameModel);
    }
}
