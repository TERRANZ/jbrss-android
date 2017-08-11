package ru.terra.jbrss.net;


import java.util.List;

import ru.terra.jbrss.net.dto.FeedDto;
import ru.terra.jbrss.net.dto.FeedPostDto;

public interface Requestor {
    String login(String user, String pass);

    List<FeedDto> getFeeds(String authToken, String uid);

    List<FeedPostDto> getFeedPosts(String authToken, Integer targetFeed, Integer page, Integer perPage, String uid);

    boolean addFeed(String authToken, String url, String uid);

    boolean removeFeed(String authToken, Integer feedId, String uid);

    boolean updateSchedulingForUser(String authToken, String uid);

    void updateSetting(String key, String val, String authToken, String uid);

    void createUser(String uid);
}