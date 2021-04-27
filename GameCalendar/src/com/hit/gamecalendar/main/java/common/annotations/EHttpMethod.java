package com.hit.gamecalendar.main.java.common.annotations;

public enum EHttpMethod {
    None, Get, Post, Put,
    Delete, Options, Patch, Head;
    public static EHttpMethod getFromString(String methodStr) {
        return switch (methodStr) {
            case "GET" -> EHttpMethod.Get;
            case "POST" -> EHttpMethod.Post;
            case "PUT" -> EHttpMethod.Put;
            case "DELETE" -> EHttpMethod.Delete;
            case "OPTIONS" -> EHttpMethod.Options;
            case "PATCH" -> EHttpMethod.Patch;
            case "HEAD" -> EHttpMethod.Head;
            default -> EHttpMethod.None;
        };
    }
}