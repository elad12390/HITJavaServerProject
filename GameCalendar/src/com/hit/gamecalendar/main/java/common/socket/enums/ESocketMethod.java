package com.hit.gamecalendar.main.java.common.socket.enums;

public enum ESocketMethod {
    None, Get, Create, Update, Delete, GetById;
    public static ESocketMethod getFromString(String methodStr) {
        return switch (methodStr) {
            case "GET" -> ESocketMethod.Get;
            case "GET_BY_ID" -> ESocketMethod.GetById;
            case "CREATE" -> ESocketMethod.Create;
            case "UPDATE" -> ESocketMethod.Update;
            case "DELETE" -> ESocketMethod.Delete;
            default -> ESocketMethod.None;
        };
    }
}
