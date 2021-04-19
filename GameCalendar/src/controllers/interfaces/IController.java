package controllers.interfaces;

import com.sun.net.httpserver.HttpServer;

public interface IController {
    public String name();
    public HttpServer setControllerPaths(HttpServer server);
}
