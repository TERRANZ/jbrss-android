package ru.terra.jbrss.storage.entity;

import android.provider.BaseColumns;

public class PostContract {
    public static abstract class PostEntity implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String EXTERNAL_ID = "ext_id";
        public static final String FEED_ID = "post_feed_id";
        public static final String DATE = "post_date";
        public static final String TITLE = "post_title";
        public static final String LINK = "post_link";
        public static final String TEXT = "post_text";
    }
}
