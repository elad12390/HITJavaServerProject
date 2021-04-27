package com.hit.gamecalendar.test;
import com.google.gson.Gson;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class JsonBodyHandler<W> implements HttpResponse.BodyHandler<W> {
    private final Class<W> wClass;

    public JsonBodyHandler(Class<W> wClass) {
        this.wClass = wClass;
    }

    @Override
    public HttpResponse.BodySubscriber<W> apply(HttpResponse.ResponseInfo responseInfo) {
        return asJSON(wClass);
    }

    public static <W> HttpResponse.BodySubscriber<W> asJSON(Class<W> targetType) {
        HttpResponse.BodySubscriber<String> upstream = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);

        return HttpResponse.BodySubscribers.mapping(
                upstream,
                (String body) -> {
                    Gson gson = new Gson();
                    return gson.fromJson(body, targetType);
                });
    }

}
