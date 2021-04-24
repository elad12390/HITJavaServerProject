package main.test.com.hit.gamecalendar;


import main.java.com.hit.gamecalendar.Startup;
import main.java.com.hit.gamecalendar.common.http.responses.HttpOKResponse;
import main.java.com.hit.gamecalendar.dao.GameModel;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

import static org.junit.Assert.assertNotEquals;


public class GameControllerTest {
    private static final HttpClient client = HttpClient.newHttpClient();
    public static final String clientAddress = "http://127.0.0.1:9110";

    @BeforeClass
    public static void StartServer() {
        Startup.main(new String[0]);
    }

    @Test
    public void testGettingAllGames() {
        var request = HttpRequest.newBuilder(URI.create(clientAddress + "/api/game"))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        try {
            HttpOKResponse<List<GameModel>> responseType = new HttpOKResponse<>();
            var testResponse = client.send(request, new JsonBodyHandler<>(responseType.getClass()));

            // Response should not be null.
            assertNotEquals(testResponse.body(), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGettingOneGame() {
        var request = HttpRequest.newBuilder(URI.create(clientAddress + "/api/game?id=1"))
                .header("accept", "application/json")
                .build();

        // use the client to send the request
        try {
            var testResponse = client.send(request, new JsonBodyHandler<>(HttpOKResponse.class));

            assertNotEquals(testResponse.body(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
