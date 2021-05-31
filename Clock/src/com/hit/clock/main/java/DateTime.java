package com.hit.clock.main.java;

public class DateTime {
    private int year;
    private int day;
    private int hour;
    private int minute;
    private int second;

    public void addSecond() {
        second++;
        if (second >= 60) {
            minute++;
        }
        if (minute >= 60) {
            hour++;
        }
        if (hour >= 24) {
            day++;
        }
        if (day >= 365) {
            year++;
        }

        second %= 60;
        minute %= 60;
        hour %= 24;
        day %= 365;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return year + ":" + day + ":" + hour + ":" + minute + ":" + second;
    }
}
