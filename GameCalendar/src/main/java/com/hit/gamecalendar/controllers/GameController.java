package main.java.com.hit.gamecalendar.controllers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
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
        HttpResponseFactory.CreateJsonResponse(exchange, service.getGameById(id));
    }

    @HttpMethod(method = EHttpMethod.Put, hasParams = true)
    public static void updateGame(HttpExchange exchange, Integer id) {
        Startup.logger.logInformation("Client updated game by id " + id);
        var service = new GameService();
        Gson gson = new Gson();

        try {
            GameModel reqObj = gson.fromJson(getBodyAsText(exchange), GameModel.class);
            HttpResponseFactory.CreateJsonResponse(exchange, service.updateGame(id, reqObj));
        } catch (Exception e) {
            HttpResponseFactory.CreateErrorResponse(exchange, "could not parse json!", e);
        }

    }


}
