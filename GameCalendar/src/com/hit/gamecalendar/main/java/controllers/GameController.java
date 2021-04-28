package com.hit.gamecalendar.main.java.controllers;

import com.hit.gamecalendar.main.java.common.annotations.Controller;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.common.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.common.socket.SocketExchange;
import com.hit.gamecalendar.main.java.common.socket.annotations.SocketMethod;
import com.hit.gamecalendar.main.java.common.socket.enums.ESocketMethod;
import com.hit.gamecalendar.main.java.common.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.common.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.common.socket.responses.SocketResponseFactory;
import com.hit.gamecalendar.main.java.controllers.abstracts.BaseController;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.services.GameService;

@Controller(path = "game")
public class GameController extends BaseController {

    public GameController() {
        super();
    }

    @SocketMethod(method = ESocketMethod.Get)
    public static void getAllGames(SocketExchange exchange) {
        var service = new GameService();

        Logger.logInformation("Client requested all games");

        var res = SocketResponseFactory.createOkResponse(service.getAllGames());

        exchange.send(res);
    }

    @SocketMethod(method = ESocketMethod.Get, hasParams = true)
    public static void getGameById(SocketExchange exchange, ParamRequestMap params) {
        var service = new GameService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client requested game by id");
        Logger.logDebug("Id was " + id);

        var response = service.getGameById(id);
        var res = (response != null) ?
                SocketResponseFactory.createOkResponse(response) :
                SocketResponseFactory.createExceptionResponse("Item not found with id " + id);

        exchange.send(res);
    }

    @SocketMethod(method = ESocketMethod.Create)
    public static void createGame(SocketExchange exchange) {
        var service = new GameService();

        SocketRequest req = exchange.getRequest();
        var data = req.getData(GameModel.class);

        Logger.logInformation("Client requested create game");
        Logger.logDebug("Game: " + data);

        var response = SocketResponseFactory.createOkResponse(new CreateItemDBResponse(service.createGame(data)));
        exchange.send(response);
    }

    @SocketMethod(method = ESocketMethod.Update, hasParams = true)
    public static void updateGame(SocketExchange exchange, ParamRequestMap params) {
        var service = new GameService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client updated game by id " + id);

        SocketRequest req = exchange.getRequest();
        var data = req.getData(GameModel.class);

        var response = SocketResponseFactory.createOkResponse(service.updateGame(id, data));
        exchange.send(response);
    }

    @SocketMethod(method = ESocketMethod.Delete, hasParams = true)
    public static void deleteGame(SocketExchange exchange, ParamRequestMap params) {
        var service = new GameService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client deleted game by id " + id);

        var isDeleted = service.deleteGame(id);
        var response = SocketResponseFactory.createOkResponse(isDeleted);
        exchange.send(response);

    }


}
