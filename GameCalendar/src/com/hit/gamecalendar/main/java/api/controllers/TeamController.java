package com.hit.gamecalendar.main.java.api.controllers;

import com.hit.gamecalendar.main.java.api.controllers.abstracts.BaseController;
import com.hit.gamecalendar.main.java.api.socket.SocketExchange;
import com.hit.gamecalendar.main.java.api.socket.annotations.SocketMethod;
import com.hit.gamecalendar.main.java.api.socket.enums.ESocketMethod;
import com.hit.gamecalendar.main.java.api.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.api.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.api.socket.responses.SocketResponseFactory;
import com.hit.gamecalendar.main.java.common.annotations.Controller;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.dao.PlayerModel;
import com.hit.gamecalendar.main.java.dao.TeamModel;
import com.hit.gamecalendar.main.java.services.GameService;
import com.hit.gamecalendar.main.java.services.PlayerService;
import com.hit.gamecalendar.main.java.services.TeamService;

@Controller(path = "Team")
public class TeamController extends BaseController {

    public TeamController() {
        super();
    }

    @SocketMethod(method = ESocketMethod.Get)
    public static void getAllTeams(SocketExchange exchange) {
        var service = new TeamService();

        Logger.logInformation("Client requested all teams");

        var res = SocketResponseFactory.createOkResponse(service.getAllTeams());

        exchange.send(res);
    }

    @SocketMethod(template = "Player",method = ESocketMethod.Get)
    public static void getAllPlayers(SocketExchange exchange) {
        var service = new PlayerService();

        Logger.logInformation("Client requested all teams");

        var res = SocketResponseFactory.createOkResponse(service.getAllPlayers());

        exchange.send(res);
    }

    @SocketMethod(method = ESocketMethod.Get, hasParams = true)
    public static void getTeamById(SocketExchange exchange, ParamRequestMap params) {
        var service = new TeamService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client requested team by id");
        Logger.logDebug("Id was " + id);

        var response = service.getTeamById(id);
        var res = (response != null) ?
                SocketResponseFactory.createOkResponse(response) :
                SocketResponseFactory.createExceptionResponse("Item not found with id " + id);

        exchange.send(res);
    }

    @SocketMethod(template = "Player",method = ESocketMethod.Get, hasParams = true)
    public static void getPlayerById(SocketExchange exchange, ParamRequestMap params) {
        var service = new PlayerService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client requested player by id");
        Logger.logDebug("Id was " + id);

        var response = service.getPlayerById(id);
        var res = (response != null) ?
                SocketResponseFactory.createOkResponse(response) :
                SocketResponseFactory.createExceptionResponse("Item not found with id " + id);

        exchange.send(res);
    }

    @SocketMethod(method = ESocketMethod.Create)
    public static void createTeam(SocketExchange exchange) {
        var service = new TeamService();

        SocketRequest req = exchange.getRequest();
        var data = req.getData(TeamModel.class);

        Logger.logInformation("Client requested create team");
        Logger.logDebug("new Team: " + data);

        var response = SocketResponseFactory.createOkResponse(new CreateItemDBResponse(service.createTeam(data)));
        exchange.send(response);
    }

    @SocketMethod(template = "Player", method = ESocketMethod.Create)
    public static void createPlayer(SocketExchange exchange) {
        var service = new PlayerService();

        SocketRequest req = exchange.getRequest();
        var data = req.getData(PlayerModel.class);

        Logger.logInformation("Client requested create player");
        Logger.logDebug("new Player: " + data);

        var response = SocketResponseFactory.createOkResponse(new CreateItemDBResponse(service.createPlayer(data)));
        exchange.send(response);
    }

    @SocketMethod(method = ESocketMethod.Update, hasParams = true)
    public static void updateTeam(SocketExchange exchange, ParamRequestMap params) {
        var service = new TeamService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client updated team by id " + id);

        SocketRequest req = exchange.getRequest();
        var data = req.getData(TeamModel.class);

        var response = SocketResponseFactory.createOkResponse(service.updateTeam(id, data));
        exchange.send(response);
    }

    @SocketMethod(template = "Player", method = ESocketMethod.Update, hasParams = true)
    public static void updatePlayer(SocketExchange exchange, ParamRequestMap params) {
        var service = new PlayerService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client updated player by id " + id);

        SocketRequest req = exchange.getRequest();
        var data = req.getData(PlayerModel.class);

        var response = SocketResponseFactory.createOkResponse(service.updatePlayer(id, data));
        exchange.send(response);
    }

    @SocketMethod(method = ESocketMethod.Delete, hasParams = true)
    public static void deleteTeam(SocketExchange exchange, ParamRequestMap params) {
        var service = new TeamService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client deleted team by id " + id);

        var isDeleted = service.deleteTeam(id);
        var response = SocketResponseFactory.createOkResponse(isDeleted);
        exchange.send(response);
    }

    @SocketMethod(template = "Player",method = ESocketMethod.Delete, hasParams = true)
    public static void deletePlayer(SocketExchange exchange, ParamRequestMap params) {
        var service = new PlayerService();

        var id = ((Double)params.get("id")).intValue();
        if (id <= 0) return;

        Logger.logInformation("Client deleted player by id " + id);

        var isDeleted = service.deletePlayer(id);
        var response = SocketResponseFactory.createOkResponse(isDeleted);
        exchange.send(response);
    }


}
