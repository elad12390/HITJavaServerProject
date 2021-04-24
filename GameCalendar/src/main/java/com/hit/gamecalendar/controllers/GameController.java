package main.java.com.hit.gamecalendar.controllers;

import com.sun.net.httpserver.HttpExchange;
import main.java.com.hit.gamecalendar.common.http.responses.CreateItemDBResponse;
import main.java.com.hit.gamecalendar.common.http.responses.HttpResponseFactory;
import main.java.com.hit.gamecalendar.common.annotations.EHttpMethod;
import main.java.com.hit.gamecalendar.common.annotations.HttpMethod;
import main.java.com.hit.gamecalendar.controllers.abstracts.BaseController;
import main.java.com.hit.gamecalendar.common.annotations.Controller;
import main.java.com.hit.gamecalendar.dao.GameModel;
import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.services.GameService;

@Controller(path = "game")
public class GameController extends BaseController {

    public GameController() {
        super();
    }

    @HttpMethod(method = EHttpMethod.Get)
    public static void getAllGames(HttpExchange exchange) {
        Startup.logger.logInformation("Client requested all games");
        var service = new GameService();
        HttpResponseFactory.CreateJsonResponse(exchange, service.getAllGames());
    }

    @HttpMethod(method = EHttpMethod.Get, hasParams = true)
    public static void getGameById(HttpExchange exchange, Integer id) {
        Startup.logger.logInformation("Client requested game by id " + id);
        var service = new GameService();
        var response = service.getGameById(id);
        if (response != null) {
            HttpResponseFactory.CreateJsonResponse(exchange, response);
        } else {
            HttpResponseFactory.CreateNotFoundResponse(exchange, "Item not found with id " + id);
        }
    }

    @HttpMethod(method = EHttpMethod.Post)
    public static void createGame(HttpExchange exchange) {
        var service = new GameService();
        try {
            GameModel reqObj = getBodyAsEntity(exchange, new GameModel());
            Startup.logger.logInformation("Client requested create game " + reqObj);
            HttpResponseFactory.CreateJsonResponse(exchange, new CreateItemDBResponse(service.createGame(reqObj)));
        } catch (Exception e) {
            HttpResponseFactory.CreateExceptionResponse(exchange, "could not parse json!", e);
        }
    }

    @HttpMethod(method = EHttpMethod.Put, hasParams = true)
    public static void updateGame(HttpExchange exchange, Integer id) {
        Startup.logger.logInformation("Client updated game by id " + id);
        var service = new GameService();
        try {
            GameModel reqObj = getBodyAsEntity(exchange, new GameModel());
            HttpResponseFactory.CreateJsonResponse(exchange, service.updateGame(id, reqObj));
        } catch (Exception e) {
            HttpResponseFactory.CreateExceptionResponse(exchange, "could not parse json!", e);
        }

    }

    @HttpMethod(method = EHttpMethod.Delete, hasParams = true)
    public static void deleteGame(HttpExchange exchange, Integer id) {
        Startup.logger.logInformation("Client updated game by id " + id);
        var service = new GameService();
        try {
            HttpResponseFactory.CreateJsonResponse(exchange, service.deleteGame(id));
        } catch (Exception e) {
            HttpResponseFactory.CreateExceptionResponse(exchange, "could not parse json!", e);
        }

    }


}
