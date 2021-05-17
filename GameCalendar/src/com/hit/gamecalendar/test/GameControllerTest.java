package com.hit.gamecalendar.test;


import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.api.socket.SocketExchange;
import com.hit.gamecalendar.main.java.api.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.api.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.api.socket.responses.SocketResponse;
import com.hit.gamecalendar.main.java.dao.GameModel;
import com.hit.gamecalendar.main.java.dao.GameType;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class GameControllerTest {
    public static InetAddress clientAddress;
    public static final int port = 9110;
    public static SocketResponse createdGameResponse = null;
    public static SocketResponse deletedGameResponse = null;

    @BeforeClass
    public static void StartServer() throws IOException, InterruptedException {
        String[] args = {
                "--l=d",
                "--m=kmp"
        };

        Startup.main(args);

        // set this pc current ip address for sending requests
        clientAddress = InetAddress.getLocalHost();
    }

    @Test
    public void getAllGamesTest() {

        // use the client to send the request
        try {
            SocketResponse games = getAllGames();
            Logger.logDebug("response = " + games.toString());

            // should get any response
            Assert.assertNotNull(games);

            // if not null should get more than one game (assuming db is not empty)
            Assert.assertNotEquals(null, games.getDataJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGameTest() {

        // use the client to send the request
        try {

            SocketResponse response = getGame();

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(GameModel.class);
                Logger.logDebug("response = " + data);
            }
            // should get any response
            Assert.assertNotNull(response);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertNotEquals(null, response.getDataJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNextGameTest() {

        // use the client to send the request
        try {

            SocketResponse response = getNextGame();

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(GameModel.class);
                Logger.logDebug("response = " + data);
            }
            // should get any response
            Assert.assertNotNull(response);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertNotEquals(null, response.getDataJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createGameTest() {

        // use the client to send the request
        try {
            createdGameResponse = createGame();

            // should get any response
            Assert.assertNotNull(createdGameResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertNotEquals(null, createdGameResponse.getDataJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteGameTest() {

        // use the client to send the request
        try {
            deletedGameResponse = deleteGameCreated(createdGameResponse);

            // should get any response
            Assert.assertNotNull(deletedGameResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertEquals(true, deletedGameResponse.getData(Boolean.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocketResponse getAllGames() throws IOException {
        var request = new SocketRequest("GET", "/api/Game/", null, "Testing");
        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);
        return exchange.get(SocketResponse.class);
    }

    private SocketResponse getGame() throws IOException {
        var query = new ParamRequestMap();
        query.put("id", 1);
        var request = new SocketRequest("GET", "/api/Game/", query ,"Testing");

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse getNextGame() throws IOException {
        var request = new SocketRequest("GET", "/api/Game/Next/", null ,"Testing");

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse createGame() throws IOException {
        var newGame = new GameModel(
                            Instant.now(),
                            GameType.League,
                            2,
                            "court",
                            1,
                            2,
                            null);

        var newGameJson = Startup.gson.toJson(newGame);
        var request = new SocketRequest("CREATE", "/api/Game/", null, newGameJson);

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse deleteGameCreated(SocketResponse gameResponse) throws IOException {
        SocketRequest request;
        SocketExchange exchange;
        var creationResponse = gameResponse.getData(CreateItemDBResponse.class);

        var query = new ParamRequestMap();
        query.put("id", creationResponse.getId().intValue());

        request = new SocketRequest("DELETE", "/api/Game/", query, null);
        exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }
}
