package controllers;

import com.sun.net.httpserver.HttpServer;
import controllers.interfaces.IController;
import models.GameModel;
import org.json.simple.JSONValue;
import services.GameService;

import java.io.OutputStream;

public class GameController implements IController {
    @Override
    public String name() {
        return "game";
    }

    @Override
    public HttpServer setControllerPaths(HttpServer server) {
        server.createContext("/api/" + name(), (exchange -> {
            var service = new GameService();
            String respText = JSONValue.toJSONString(service.getAllGames());

            exchange.sendResponseHeaders(200, respText.getBytes().length);

            OutputStream output = exchange.getResponseBody();
            output.write(respText.getBytes());
            output.flush();

            exchange.close();
        }));
        return server;
    }
}
