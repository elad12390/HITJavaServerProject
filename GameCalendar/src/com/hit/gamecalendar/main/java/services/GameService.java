package com.hit.gamecalendar.main.java.services;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.repositories.GameRepository;
import com.hit.gamecalendar.main.java.services.abstracts.BaseService;

import java.sql.Date;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public class GameService extends BaseService<GameModel, GameRepository> {

    public GameService() {
        super(new GameRepository());
    }

    public List<GameModel> getAllGames() {
        return this.repo.getAll();
    }

    public GameModel getNextGame() {
        var allGames = this.repo.getAll();
         var nextGame = allGames.stream()
                .filter(gameModel -> {
                    try {
                        return gameModel.getDateTimeParsed().compareTo(Date.from(Instant.now())) >= 0;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return false;
                })
                .min((game1, game2) -> {
                    try {
                        return game1.getDateTimeParsed().compareTo(game2.getDateTimeParsed());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 1;
                });

        return nextGame.orElse(null);
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
