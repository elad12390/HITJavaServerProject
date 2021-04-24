package main.java.com.hit.gamecalendar.common.http.responses;

public class HttpResponse<T> {
    public HttpResponse(int statusCode, String message, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.data = data;
    }

    public HttpResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.data = null;
        this.message = message;
    }

    public HttpResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public String message;
    public int statusCode;
    public T data;
}
