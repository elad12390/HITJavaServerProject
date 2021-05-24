package com.hit.gamecalendar.test;


import com.hit.gamecalendar.main.java.api.Startup;
import com.hit.gamecalendar.main.java.api.socket.SocketExchange;
import com.hit.gamecalendar.main.java.api.socket.requests.ParamRequestMap;
import com.hit.gamecalendar.main.java.api.socket.requests.SocketRequest;
import com.hit.gamecalendar.main.java.api.socket.responses.CreateItemDBResponse;
import com.hit.gamecalendar.main.java.api.socket.responses.SocketResponse;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.dao.PlayerModel;
import com.hit.gamecalendar.main.java.dao.TeamModel;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TeamControllerTest {
    public static InetAddress clientAddress;
    public static final int port = 9110;

    @BeforeClass
    public static void StartServer() throws IOException {
        String[] args = {
                "--l=d",
                "--m=kmp"
        };

        Startup.main(args);

        // set this pc current ip address for sending requests
        clientAddress = InetAddress.getLocalHost();
    }


    @Test
    public void notCommandTest() {

        // use the client to send the request
        try {
            SocketResponse exceptionRes = notCommand();
            Logger.logDebug("response = " + exceptionRes.toString());

            // should not get any response
            Assert.assertNotNull(exceptionRes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //********************************** Team Tests *****************************************//

    public static SocketResponse createTeamResponse = null;
    public static SocketResponse deletedTeamResponse = null;

    @Test
    public void getAllTeamsTest() {

        // use the client to send the request
        try {
            SocketResponse teams = getAllTeams();
            Logger.logDebug("response = " + teams.toString());

            // should get any response
            Assert.assertNotNull(teams);

            // if not null should get more than one game (assuming db is not empty)
            Assert.assertNotEquals(null, teams.getDataJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTeamTest() {

        // use the client to send the request
        try {

            SocketResponse response = getTeam();

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(TeamModel.class);
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
    public void createTeamTest() {

        // use the client to send the request
        try {
            createTeamResponse = createTeam();

            // should get any response
            Assert.assertNotNull(createTeamResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertNotEquals(null, createTeamResponse.getDataJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteTeamTest() {

        // use the client to send the request
        try {
            deletedTeamResponse = deleteTeamCreated(createTeamResponse);

            // should get any response
            Assert.assertNotNull(deletedTeamResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertEquals(true, deletedTeamResponse.getData(Boolean.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //*****************************************************************************************//



    //********************************** Player Tests *****************************************//


    public static SocketResponse createPlayerResponse = null;
    public static SocketResponse deletedPlayerResponse = null;

    @Test
    public void getAllPlayersTest() {

        // use the client to send the request
        try {
            SocketResponse players = getAllPlayers();
            Logger.logDebug("response = " + players.toString());

            // should get any response
            Assert.assertNotNull(players);

            // if not null should get more than one game (assuming db is not empty)
            Assert.assertNotEquals(null, players.getDataJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getPlayerTest() {

        // use the client to send the request
        try {

            SocketResponse response = getPlayer();

            Assert.assertTrue("Response should return successful true", response.isSuccessful());
            if (response.isSuccessful()) {
                var data = response.getData(PlayerModel.class);
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
    public void createPlayerTest() {

        // use the client to send the request
        try {
            createPlayerResponse = createPlayer();

            // should get any response
            Assert.assertNotNull(createPlayerResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertNotEquals(null, createPlayerResponse.getDataJson());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deletePlayerTest() {

        // use the client to send the request
        try {
            deletedPlayerResponse = deletePlayerCreated(createPlayerResponse);

            // should get any response
            Assert.assertNotNull(deletedPlayerResponse);

            // game number 1 should exist (assuming db is not empty)
            Assert.assertEquals(true, deletedPlayerResponse.getData(Boolean.class));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //***************************************************************************************//




    private SocketResponse getAllTeams() throws IOException {
        var request = new SocketRequest("GET", "/api/Team/", null, "Testing");
        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);
        return exchange.get(SocketResponse.class);
    }

    private SocketResponse getTeam() throws IOException {
        var query = new ParamRequestMap();
        query.put("id", 1);
        var request = new SocketRequest("GET", "/api/Team/", query ,"Testing");

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse createTeam() throws IOException {
        var newTeam = new TeamModel("TestTeam", "Test", "Test", "Test");

        var newTeamJson = Startup.gson.toJson(newTeam);
        var request = new SocketRequest("CREATE", "/api/Team/", null, newTeamJson);

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse deleteTeamCreated(SocketResponse teamResponse) throws IOException {
        SocketRequest request;
        SocketExchange exchange;
        var creationResponse = teamResponse.getData(CreateItemDBResponse.class);

        var query = new ParamRequestMap();
        query.put("id", creationResponse.getId().intValue());

        request = new SocketRequest("DELETE", "/api/Team/", query, null);
        exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }





    private SocketResponse notCommand() throws IOException {
        var request = new SocketRequest("GET", "/api/404/", null, "Testing");
        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);
        return exchange.get(SocketResponse.class);
    }

    private SocketResponse getAllPlayers() throws IOException {
        var request = new SocketRequest("GET", "/api/Team/Player/", null, "Testing");
        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);
        return exchange.get(SocketResponse.class);
    }

    private SocketResponse getPlayer() throws IOException {
        var query = new ParamRequestMap();
        query.put("id", 1);
        var request = new SocketRequest("GET", "/api/Team/Player/", query ,"Testing");

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse createPlayer() throws IOException {
        var newGame = new PlayerModel(1, "TestPlayer", "123", "Test", "Player");

        var newGameJson = Startup.gson.toJson(newGame);
        var request = new SocketRequest("CREATE", "/api/Team/Player/", null, newGameJson);

        SocketExchange exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }

    private SocketResponse deletePlayerCreated(SocketResponse playerResponse) throws IOException {
        SocketRequest request;
        SocketExchange exchange;
        var creationResponse = playerResponse.getData(CreateItemDBResponse.class);

        var query = new ParamRequestMap();
        query.put("id", creationResponse.getId().intValue());

        request = new SocketRequest("DELETE", "/api/Team/Player/", query, null);
        exchange = new SocketExchange(new Socket(clientAddress, port));
        exchange.send(request);

        return exchange.get(SocketResponse.class);
    }
}
