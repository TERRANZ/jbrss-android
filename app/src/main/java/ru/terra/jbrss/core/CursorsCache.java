package ru.terra.jbrss.core;

import android.database.Cursor;

public class CursorsCache {
    private static CursorsCache instance = new CursorsCache();

    public static CursorsCache getInstance() {
        return instance;
    }

    private Cursor postCursor;

    public Cursor getPostCursor() {
        return postCursor;
    }

    public void setPostCursor(Cursor postCursor) {
        this.postCursor = postCursor;
    }
}
