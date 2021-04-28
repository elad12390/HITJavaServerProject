package com.hit.gamecalendar.main.java.common.cache;

import java.util.HashMap;
import java.util.TimerTask;

public class CacheSlidingRemover extends TimerTask {
    private final HashMap<String, Object> _cache;
    private final String key;

    public CacheSlidingRemover(HashMap<String, Object> cache, String key) {
        this._cache = cache;
        this.key = key;
    }

    @Override
    public void run() {
        _cache.remove(key);
    }
}
