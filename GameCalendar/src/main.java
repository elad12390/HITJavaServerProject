import com.mysql.cj.xdevapi.JsonParser;
import com.mysql.cj.xdevapi.JsonString;
import com.sun.net.httpserver.HttpServer;
import controllers.GameController;
import main.java.com.hit.stringmatching.implementations.KnuthMorrisPrattAlgoMatcherImpl;
import models.GameModel;
import org.json.simple.JSONValue;
import repositories.Database;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class main {
    public static Database db;
    public static Boolean finishedSetup = false;
    public static final int serverPort = 9110;
    private static GameController gameController;

    private static void setup() {
        try {
            main.db = new Database("jdbc:mysql://localhost:3306/?user=root", "root" , "1234", "game-calendar");
            main.gameController = new GameController();
        } catch (Exception e) {
            System.out.println("[ERROR] Could not connect to database!");
            throw e;
        }


        main.finishedSetup = true;
    }

    private static void run() throws IOException {
        System.out.println("running at port " + main.serverPort);

        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        // Controller contexts
        gameController.setControllerPaths(server);


        server.setExecutor(null); // creates a default executor
        server.start();

    }

    public static void main(String[] args) {
        main.setup();
        try {
            main.run();
        } catch (Exception e) {
            System.out.println("Server caught an Exception" + e);
        }
    }
}
