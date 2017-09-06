package ru.terra.jbrss.storage.entity;

import android.net.Uri;
import android.provider.BaseColumns;

import ru.terra.jbrss.net.Constants;

public class FeedContract {

    public static String CONTENT_DIRECTORY = "feed";
    public static Uri CONTENT_URI = Uri.parse("content://" + Constants.AUTHORITY + "/" + CONTENT_DIRECTORY);


    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "feed";
        public static final String EXTERNAL_ID = "ext_id";
        public static final String NAME = "feed_name";
        public static final String URL = "feed_url";
        public static final String UPDATE_TIME = "feed_updated";
    }
}
