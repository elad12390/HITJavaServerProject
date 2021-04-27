package com.hit.gamecalendar.main.java.common.http.responses;

import java.net.HttpURLConnection;

public class HttpExceptionResponse extends HttpResponse<String> {

    public HttpExceptionResponse(String message, String exception) {
        super(HttpURLConnection.HTTP_INTERNAL_ERROR, message, exception);
    }
}
