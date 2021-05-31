package com.hit.gamecalendar.main.java.dao;

import com.hit.gamecalendar.main.java.api.Startup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class GameModel {
    private int id = 0;
    private String dateTime;
    // GameType
    private int type;
    private int roundNumber;
    private String court;
    private int hostingTeamId;
    private int participatingTeamId;
    private String result = "";
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String logDateTime = Startup.clock.getTime();

    public GameModel() {
    }

    public GameModel(String dateTime, GameType type, int roundNumber, String court, int hostingTeamId, int participatingTeamId, String result) {

        this.dateTime = dateTime;
        this.type = type.getValue();
        this.roundNumber = roundNumber;
        this.court = court;
        this.hostingTeamId = hostingTeamId;
        this.participatingTeamId = participatingTeamId;
        this.result = result;
    }

    public GameModel(Instant dateTime, GameType type, int roundNumber, String court, int hostingTeamId, int participatingTeamId, String result) {
        this.logDateTime = Startup.clock.getTime();

        this.dateTime = formatter.format(Date.from(dateTime));
        this.type = type.getValue();
        this.roundNumber = roundNumber;
        this.court = court;
        this.hostingTeamId = hostingTeamId;
        this.participatingTeamId = participatingTeamId;
        this.result = result;
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

    public String getDateTime() {
        return dateTime;
    }

    public Date getDateTimeParsed() throws ParseException {
        return formatter.parse(dateTime);
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public GameType getType() {
        return GameType.fromValue(this.type);
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public int getHostingTeamId() {
        return hostingTeamId;
    }

    public void setHostingTeamId(int hostingTeamId) {
        this.hostingTeamId = hostingTeamId;
    }

    public int getParticipatingTeamId() {
        return participatingTeamId;
    }

    public void setParticipatingTeamId(int participatingTeamId) {
        this.participatingTeamId = participatingTeamId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", type=" + type +
                ", roundNumber=" + roundNumber +
                ", court='" + court + '\'' +
                ", hostingTeamId=" + hostingTeamId +
                ", participatingTeamId=" + participatingTeamId +
                ", result='" + result + '\'' +
                '}';
    }
}
