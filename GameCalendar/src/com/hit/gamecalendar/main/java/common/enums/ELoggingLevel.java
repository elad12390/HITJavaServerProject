package com.hit.gamecalendar.main.java.common.enums;

public enum ELoggingLevel {
    ERROR(0),
    INFORMATION(1),
    DEBUG(2);

    private int numVal;

    ELoggingLevel(int i) {
        this.numVal = i;
    }

    int getNum() {
        return numVal;
    }

    public int compareNum(ELoggingLevel other) {
        return this.numVal - other.numVal;
    }

}
