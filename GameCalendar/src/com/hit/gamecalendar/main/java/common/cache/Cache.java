package com.hit.gamecalendar.main.java.common.cache;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

@SuppressWarnings("unchecked")
public class Cache implements ICache {
    private final static HashMap<String, Object> _cache = new HashMap<>();
    public static final List<Timer> timers = new ArrayList<>();
    private static final long DEFAULT_CACHE_DURATION = 2;

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

    public static void clear() {
        _cache.clear();
    }

    public static void cancelTimers() {
        timers.forEach(timer -> timer.cancel());
    }

    // **************************** Private Functions **************************** //

    private void createSlidingTimer(String key, long delay) {
        var newTimer = new Timer();
        newTimer.schedule(new CacheSlidingRemover(_cache, key), delay);
        timers.add(newTimer);
    }

    private <T> void _set(String key, T val) {
        this._cache.put(key, val);
    }

}
