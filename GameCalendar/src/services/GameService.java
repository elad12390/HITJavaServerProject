package services;

import models.GameModel;
import repositories.GameRepository;
import services.interfaces.IService;

import java.util.List;

public class GameService implements IService {
    public List<GameModel> getAllGames() {
        var repo = new GameRepository();

        return repo.getAll();
    }
}
