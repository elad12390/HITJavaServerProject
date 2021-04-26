package main.java.com.hit.gamecalendar.common.http.responses;

import java.net.HttpURLConnection;

public class HttpExceptionResponse<E extends Exception> extends HttpResponse<E> {

    public HttpExceptionResponse(String message, E exception) {
        super(HttpURLConnection.HTTP_INTERNAL_ERROR, message, exception);
        exception.getStackTrace();
    }

    public HttpExceptionResponse(E exception) {
        super(HttpURLConnection.HTTP_INTERNAL_ERROR, exception);
        exception.getStackTrace();
    }
}
