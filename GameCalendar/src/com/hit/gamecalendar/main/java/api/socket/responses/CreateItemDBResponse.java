package com.hit.gamecalendar.main.java.api.socket.responses;

public class CreateItemDBResponse {
    private final Long id;

    public CreateItemDBResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
