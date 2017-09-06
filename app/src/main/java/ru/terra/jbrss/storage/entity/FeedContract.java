package ru.terra.jbrss.storage.entity;

import android.provider.BaseColumns;

public class FeedContract {

    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "feed";
        public static final String EXTERNAL_ID = "ext_id";
        public static final String NAME = "feed_name";
        public static final String URL = "feed_url";
        public static final String UPDATE_TIME = "feed_updated";
    }
}
