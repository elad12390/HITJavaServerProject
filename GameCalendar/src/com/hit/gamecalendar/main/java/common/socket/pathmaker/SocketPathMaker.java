package com.hit.gamecalendar.main.java.common.socket.pathmaker;

import com.hit.gamecalendar.main.java.common.Triplet;
import com.hit.gamecalendar.main.java.common.annotations.Controller;
import com.hit.gamecalendar.main.java.common.logger.Logger;
import com.hit.gamecalendar.main.java.common.socket.exceptions.SocketNotFoundException;
import com.hit.gamecalendar.main.java.common.reflections.ReflectionHelper;
import com.hit.gamecalendar.main.java.common.socket.SocketDriver;
import com.hit.gamecalendar.main.java.common.socket.annotations.SocketMethod;
import com.hit.gamecalendar.main.java.common.socket.enums.ESocketMethod;
import com.hit.gamecalendar.main.java.common.socket.exceptions.SocketPathAlreadyExistsException;
import com.hit.gamecalendar.main.java.common.socket.responses.SocketResponseFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SocketPathMaker {

    // ************************** Public Functions ************************** //

    /**
     *  A method map of all methods with "@HttpMethod" annotation. (for use in runtime)
     * */
    public static Map<Triplet<String, ESocketMethod, Boolean>, Method> methodMap;

    /**
     * The package to scan for controllers and methods.
     */
    public static final String PACKAGE_NAME = "com.hit.gamecalendar.main.java";

    /**
     * This function creates all controllers with @Controller annotation and with @HttpMethod annotations by using reflection
     * @param driver The current HTTP server being used (in the main)
     */
    public static void makePaths(SocketDriver driver) throws SocketPathAlreadyExistsException {

        // getRequest all controllers with "@Controller" annotation
        var typesWithControllerAnnotation = ReflectionHelper.getClassesWithAnnotation(PACKAGE_NAME, Controller.class);

        SocketPathMaker.methodMap = new HashMap<>();
        for (var controller: typesWithControllerAnnotation) {
            // getRequest the current controller annotation (for the parameters)
            var controllerAnnotation = controller.getAnnotation(Controller.class);
            createControllerMethodMapping(controller, controllerAnnotation);
            createControllerServerContext(driver, controllerAnnotation);
        }
    }

    // ************************** Private Functions ************************** //

    /**
     * Adds all current controller methods to the hashmap
     * @param controller The current controller Class
     * @param controllerAnnotation The current controller's annotation ("@Controller")
     */
    private static void createControllerMethodMapping(Class<?> controller, Controller controllerAnnotation) {
        Method[] methods = controller.getDeclaredMethods();

        for (var method : methods) {
            var methodAnnotation = method.getAnnotation(SocketMethod.class);
            if (methodAnnotation == null) continue;

            methodMap.put(
                    new Triplet<>( "/api/" + controllerAnnotation.path() + "/" + methodAnnotation.template(), methodAnnotation.method(), methodAnnotation.hasParams()),
                    method
            );
        }
    }

    /**
     * Creates the http context of the current Controller
     * @param server The HttpServer created in main
     * @param controllerAnnotation The current controller annotation (for using path)
     */
    private static void createControllerServerContext(SocketDriver server, Controller controllerAnnotation) throws SocketPathAlreadyExistsException {
        server.createPath("/api/"+controllerAnnotation.path() + "/", (exchange) -> {
            try {
                var request = exchange.getRequest();
                var query = request.getQueryData();
                var path = request.getPath();
                var method = request.getMethod();

//                String controllerFullPath = controllerAnnotation.path() + "/" + String.join("", path.split("/api/"));

                var methodToRun = methodMap.get(new Triplet<>(path, ESocketMethod.getFromString(method), query != null));
                if (methodToRun == null) {
                    throw new SocketNotFoundException("Could not find path: " + path + " with method " + method);
                }

                ArrayList<Object> invokeParams = new ArrayList<>();
                invokeParams.add(exchange);
                if (query != null && query.size() > 0) {
                    invokeParams.add(query);
                }

                methodToRun.invoke(null, invokeParams.toArray());
            }
            catch (SocketNotFoundException e) {
                e.getStackTrace();
                Logger.logError(e.getMessage());
                SocketResponseFactory.createNotFoundResponse(e.getMessage(), e);
            }
            catch (Exception e) {
                Logger.logError("Error occurred in http exchange " + e);
                SocketResponseFactory.createExceptionResponse("Error occurred in http exchange", e);
            } finally {
                exchange.close();
            }

        });
    }
}
