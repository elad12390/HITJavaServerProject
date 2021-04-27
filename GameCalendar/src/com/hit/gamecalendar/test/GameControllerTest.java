package com.hit.gamecalendar.test;


import com.google.gson.Gson;
import com.hit.gamecalendar.main.java.Startup;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.common.socket.SocketExchange;
import com.hit.gamecalendar.main.java.common.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.common.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.common.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.common.socket.responses.SocketResponse;
import com.hit.gamecalendar.main.java.dao.GameModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameControllerTest {
    public static InetAddress clientAddress;
    public static final int port = 9110;

    @BeforeClass
    public static void StartServer() throws IOException, InterruptedException {
        Startup.main(null);
        Thread.sleep(1000);
        clientAddress = InetAddress.getLocalHost();
    }

    @Test
    public void testGettingAllGames() {
        var request = new SocketRequest("GET", "/api/game/", null, "Testing");

        // use the client to send the request
        try {
            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            exchange.send(request);
            var games = (SocketResponse)exchange.get(SocketResponse.class);

            // should get any response
            Assert.assertNotEquals(null, games);

            // if not null should get more than one game (assuming db is not empty)
            if (games != null)
                Assert.assertNotEquals(null, games.data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGettingOneGame() {

        // use the client to send the request
        try {

            var query = new ParamRequestMap();
            query.put("id", 2);
            var request = new SocketRequest("GET", "/api/game/", query ,"Testing");

            SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
            exchange.send(request);

            var response = (SocketResponse)exchange.get(SocketResponse.class);

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(GameModel.class);
                Logger.logDebug("response = " + data);
            }
            // should get any response
            Assert.assertNotEquals(null, response);

            // game number 1 should exist (assuming db is not empty)
            if (response != null)
                Assert.assertNotEquals(null, response.data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreatingAndDeleteGame() {

        // use the client to send the request
        try {
            var gson = new Gson();
            var gameResponse = createGame(gson);
            deleteGameCreated(gson, gameResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SocketResponse createGame(Gson gson) throws IOException {
        var newGame = new GameModel();
        newGame.cool_name = "TestingCreation" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        var newGameJson = gson.toJson(newGame);
        var request = new SocketRequest("CREATE", "/api/game/", null, newGameJson);

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        var gameResponse = (SocketResponse)exchange.get(SocketResponse.class);

        // should get any response
        Assert.assertNotEquals(null, gameResponse);

        // game number 1 should exist (assuming db is not empty)
        if (gameResponse != null)
            Assert.assertNotEquals(null, gameResponse.data);

        return gameResponse;
    }

    private void deleteGameCreated(Gson gson, SocketResponse gameResponse) throws IOException {
        SocketRequest request;
        SocketExchange exchange;
        var query = new ParamRequestMap();
        var creationResponse = gson.fromJson(gameResponse.data, CreateItemDBResponse.class);
        query.put("id", creationResponse.id.intValue());
        request = new SocketRequest("DELETE", "/api/game/", query, null);
        exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        var deleteResponse = (SocketResponse)exchange.get(SocketResponse.class);

        // should get any response
        Assert.assertNotEquals(null, deleteResponse);

        // game number 1 should exist (assuming db is not empty)
        if (deleteResponse != null)
            Assert.assertEquals(true, gson.fromJson(deleteResponse.data, Boolean.class));
    }
}
