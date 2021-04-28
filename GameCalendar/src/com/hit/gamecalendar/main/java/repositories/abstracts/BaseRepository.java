package com.hit.gamecalendar.main.java.repositories.abstracts;

import com.hit.gamecalendar.main.java.common.cache.Cache;

public abstract class BaseRepository {
    protected final Cache cache = new Cache();
    protected BaseRepository() {
    }
}
