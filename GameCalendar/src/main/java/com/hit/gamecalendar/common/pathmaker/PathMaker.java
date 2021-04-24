package main.java.com.hit.gamecalendar.common.pathmaker;

import com.sun.net.httpserver.HttpServer;
import javassist.NotFoundException;
import main.java.com.hit.gamecalendar.common.Triplet;
import main.java.com.hit.gamecalendar.common.annotations.Controller;
import main.java.com.hit.gamecalendar.common.annotations.EHttpMethod;
import main.java.com.hit.gamecalendar.common.annotations.HttpMethod;
import main.java.com.hit.gamecalendar.common.http.responses.HttpResponseFactory;
import main.java.com.hit.gamecalendar.Startup;

import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PathMaker {
    /**
     *  A method map of all methods with "@HttpMethod" annotation. (for use in runtime)
     * */
    public static Map<Triplet<String, EHttpMethod, Boolean>, Method> methodMap;
    /**
     * The package to scan for controllers and methods.
     */
    public static final String PACKAGE_NAME = "main.java.com.hit";

    /**
     * This function creates all controllers with @Controller annotation and with @HttpMethod annotations by using reflection
     * @param server The current HTTP server being used (in the main)
     */
    public static void makePaths(HttpServer server) {
        Reflections reflections = new Reflections(PACKAGE_NAME);

        // get all controllers with "@Controller" annotation
        var typesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);

        PathMaker.methodMap = new HashMap<>();
        for (var controller: typesWithControllerAnnotation) {
            // get the current controller annotation (for the parameters)
            var controllerAnnotation = controller.getAnnotation(Controller.class);
            createControllerMethodMapping(controller, controllerAnnotation);
            createControllerServerContext(server, controllerAnnotation);
        }
    }

    /**
     * Adds all current controller methods to the hashmap
     * @param controller The current controller Class
     * @param controllerAnnotation The current controller's annotation ("@Controller")
     */
    private static void createControllerMethodMapping(Class<?> controller, Controller controllerAnnotation) {
        Method[] methods = controller.getDeclaredMethods();

        for (var method : methods) {
            var methodHttpAnnotation = method.getAnnotation(HttpMethod.class);
            if (methodHttpAnnotation == null) continue;

            methodMap.put(
                    new Triplet<>( controllerAnnotation.path() + "/" + methodHttpAnnotation.template(), methodHttpAnnotation.method(), methodHttpAnnotation.hasParams()),
                    method
            );
        }
    }

    /**
     * Creates the http context of the current Controller
     * @param server The HttpServer created in main
     * @param controllerAnnotation The current controller annotation (for using path)
     * @return The same HttpServer
     */
    private static HttpServer createControllerServerContext(HttpServer server, Controller controllerAnnotation) {
        server.createContext("/api/"+controllerAnnotation.path(), exchange -> {
            try {
                var query = exchange.getRequestURI().getQuery();
                var path = String.join("", exchange.getRequestURI().getPath().split(controllerAnnotation.path()));
                var method = exchange.getRequestMethod();

                String controllerFullPath = controllerAnnotation.path() + "/" + String.join("", path.split("/api/"));
                var methodToRun = methodMap.get(new Triplet<>(controllerFullPath, EHttpMethod.getFromString(method), query != null));
                if (methodToRun == null) {
                    throw new NotFoundException("Http method not found");
                }

                ArrayList<Object> invokeParams = new ArrayList<>();
                invokeParams.add(exchange);

                if (query != null) {
                    invokeParams.addAll(createInvokeExtraParameters(query, methodToRun));
                }

                methodToRun.invoke(null, invokeParams.toArray());

            } catch (NotFoundException e) {
                Startup.logger.logError("Error occurred in http exchange " + e);
                HttpResponseFactory.CreateNotFoundResponse(exchange, "Error occurred in http exchange", e);
            }
            catch (Exception e) {
                Startup.logger.logError("Error occurred in http exchange " + e);
                HttpResponseFactory.CreateErrorResponse(exchange, "Error occurred in http exchange", e);
            } finally {
                exchange.close();
            }
        });

        return server;
    }

    private static ArrayList<Object> createInvokeExtraParameters(String query, Method methodToRun) {
        ArrayList<Object> invokeParams = new ArrayList<>();
        var queryParams = getQueryParams(query);

        Parameter[] methodParameters = methodToRun.getParameters();

        // add all other parameters (if passed)
        for (int i = 1; i < methodParameters.length; i++) {
            var queryVal = queryParams.get(i - 1);
            setInvokeParamsBasedOnType(methodParameters[i], queryVal, invokeParams);
        }
        return invokeParams;
    }

    /**
     * convert query param value (string) to the requested type of the function - Supports only primitive data types.
     * @param methodParameter the method parameter (for metadata about it)
     * @param queryVal the value to parse
     * @param invokeParams the ArrayList add the parameter to
     */
    private static void setInvokeParamsBasedOnType(Parameter methodParameter, String queryVal, ArrayList<Object> invokeParams) {
        Class<?> type = methodParameter.getType();
        if (Byte.class.equals(type)) {
            invokeParams.add(Byte.parseByte(queryVal));
        } else if (Short.class.equals(type)) {
            invokeParams.add(Short.parseShort(queryVal));
        }else if (Integer.class.equals(type)) {
            invokeParams.add(Integer.parseInt(queryVal));
        }else if (Long.class.equals(type)) {
            invokeParams.add(Long.parseLong(queryVal));
        }else if (Float.class.equals(type)) {
            invokeParams.add(Float.parseFloat(queryVal));
        } else if (Double.class.equals(type)) {
            invokeParams.add(Double.parseDouble(queryVal));
        } else if (Boolean.class.equals(type)) {
            invokeParams.add(Boolean.parseBoolean(queryVal));
        } else if (Character.class.equals(type)) {
            invokeParams.add(queryVal.charAt(0));
        } else if (String.class.equals(type)) {
            invokeParams.add(queryVal);
        }
    }

    /**
     * Convert queryString to query array.
     * @param query the queryString to convert
     * @return list of query values
     */
    private static List<String> getQueryParams(String query) {
        String[] queryParams = query.split("&");
        List<String> queryParamValues = new ArrayList<>();
        for (var queryParam : queryParams) {
            var keyValueArr = queryParam.split("=");
            queryParamValues.add(keyValueArr[1]);
        }
        return queryParamValues;
    }
}
