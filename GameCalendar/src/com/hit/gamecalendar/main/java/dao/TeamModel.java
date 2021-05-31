package com.hit.gamecalendar.main.java.dao;

import com.hit.gamecalendar.main.java.api.Startup;

public class TeamModel {
    private int id;
    private String name;
    private String homeCourtName;
    private String homeCourtAddress;
    private String homeCourtPhone;
    private String logDateTime = Startup.clock.getTime();

    public TeamModel() {
        this.logDateTime = Startup.clock.getTime();
    }

    public TeamModel(String name, String homeCourtName, String homeCourtAddress, String homeCourtPhone) {
        this.logDateTime = Startup.clock.getTime();
        this.name = name;
        this.homeCourtName = homeCourtName;
        this.homeCourtAddress = homeCourtAddress;
        this.homeCourtPhone = homeCourtPhone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomeCourtName() {
        return homeCourtName;
    }

    public void setHomeCourtName(String homeCourtName) {
        this.homeCourtName = homeCourtName;
    }

    public String getHomeCourtAddress() {
        return homeCourtAddress;
    }

    public void setHomeCourtAddress(String homeCourtAddress) {
        this.homeCourtAddress = homeCourtAddress;
    }

    public String getHomeCourtPhone() {
        return homeCourtPhone;
    }

    public void setHomeCourtPhone(String homeCourtPhone) {
        this.homeCourtPhone = homeCourtPhone;
    }

    @Override
    public String toString() {
        return "TeamModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", homeCourtName='" + homeCourtName + '\'' +
                ", homeCourtAddress='" + homeCourtAddress + '\'' +
                ", homeCourtPhone='" + homeCourtPhone + '\'' +
                '}';
    }
}
