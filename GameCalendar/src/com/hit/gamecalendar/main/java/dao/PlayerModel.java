package com.hit.gamecalendar.main.java.dao;

import com.hit.gamecalendar.main.java.api.Startup;

public class PlayerModel {
    private int id;
    private int teamId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String logDateTime = Startup.clock.getTime();

    public PlayerModel() {
        this.logDateTime = Startup.clock.getTime();
    }

    public PlayerModel(int teamId, String username, String password, String firstName, String lastName) {
        this.logDateTime = Startup.clock.getTime();

        this.teamId = teamId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLogDateTime() {
        return logDateTime;
    }

    public void setLogDateTime(String logDateTime) {
        this.logDateTime = logDateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "id=" + id +
                ", teamId=" + teamId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
