package com.hit.gamecalendar.main.java.common.http.responses;

import java.net.HttpURLConnection;

public class HttpOKResponse<T> extends HttpResponse<T> {

    public HttpOKResponse(String message, T data) {
        super(HttpURLConnection.HTTP_OK, message, data);
    }

    public HttpOKResponse(T data) {
        super(HttpURLConnection.HTTP_OK, data);
    }

    public HttpOKResponse() { super(); }
}
