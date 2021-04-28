package com.hit.gamecalendar.main.java.common.cache;
import java.time.Instant;
import java.time.LocalDateTime;

public interface ICache {
    <T> T get(String key);
    <T> void set(String key, T value);
    <T> void set(String key, T value, long durationInSeconds);
    <T> void set(String key, T value, LocalDateTime expiration);
    Boolean exists(String key);
    <T> T remove(String key);
}
