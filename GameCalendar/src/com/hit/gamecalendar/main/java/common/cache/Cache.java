package com.hit.gamecalendar.main.java.common.cache;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Timer;
;

public class Cache implements ICache {
    private final HashMap<String, Object> _cache = new HashMap<>();
    private static final long DEFAULT_CACHE_DURATION = 60;

    @Override
    public <T> T get(String key) {
        return (T) this._cache.get(key);
    }

    @Override
    public <T> void set(String key, T value) {
        this._set(key, value);
        this.createSlidingTimer(key, DEFAULT_CACHE_DURATION * 1000);
    }

    @Override
    public <T> void set(String key, T value, long durationInSeconds) {
        this.set(key, value);
        this.createSlidingTimer(key, durationInSeconds * 1000);
    }

    @Override
    public <T> void set(String key, T value, LocalDateTime expiration) {
        this.set(key, value);
        this.createSlidingTimer(key, LocalDateTime.now().until(expiration, ChronoUnit.MILLIS));
    }

    @Override
    public Boolean exists(String key) {
        return this._cache.containsKey(key);
    }

    @Override
    public <T> T remove(String key) {
        return (T) this._cache.remove(key);
    }

    // **************************** Private Functions **************************** //

    private void createSlidingTimer(String key, long delay) {
        new Timer().schedule(new CacheSlidingRemover(_cache, key), delay);
    }

    private <T> void _set(String key, T val) {
        this._cache.put(key, val);
    }

}
