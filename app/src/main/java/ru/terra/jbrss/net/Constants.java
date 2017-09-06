package ru.terra.jbrss.net;

public class Constants {
    public static final String AUTHORITY = "ru.terra.jbrss";

    public static class Account {
        public static final String USER = "user";
        public static final String GET_ID = "/{client}/id";

        public static final String ALL_IDS = "/ids";
        public static final String CREATE = "/create";
        public static final String LOGIN = "/login";
        public static final String TOKEN = "oauth/token";
    }

    public static class Rss {
        public static final String FEED = "/feed";
        public static final String FEED_POSTS = "/{fid}/list";
        public static final String ADD = "/add";
        public static final String DEL = "/{fid}/del";
        public static final String UPDATE = "/update";
        public static final String SETTINGS = "/settings";
    }
}
