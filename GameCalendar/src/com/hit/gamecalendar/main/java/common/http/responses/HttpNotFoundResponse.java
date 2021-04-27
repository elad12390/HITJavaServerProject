package com.hit.gamecalendar.main.java.common.http.responses;

import java.net.HttpURLConnection;

public class HttpNotFoundResponse<T> extends HttpResponse<T> {

    public HttpNotFoundResponse(String message) {
        super(HttpURLConnection.HTTP_NOT_FOUND, message);
    }

    public HttpNotFoundResponse(String message, T data) {
        super(HttpURLConnection.HTTP_NOT_FOUND, message, data);
    }

    public HttpNotFoundResponse(T data) {
        super(HttpURLConnection.HTTP_NOT_FOUND, data);
    }
}
