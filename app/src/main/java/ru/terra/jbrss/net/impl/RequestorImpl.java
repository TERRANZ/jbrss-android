package ru.terra.jbrss.net.impl;

import android.content.Context;

import java.util.List;

import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.FeedDto;
import ru.terra.jbrss.net.dto.FeedPostDto;

public class RequestorImpl implements Requestor {

    private Context context;

    public RequestorImpl(Context context) {
        this.context = context;
    }

    @Override
    public String login(String user, String pass) {
        return null;
    }

    @Override
    public List<FeedDto> getFeeds(String authToken, String uid) {
        return null;
    }

    @Override
    public List<FeedPostDto> getFeedPosts(String authToken, Integer targetFeed, Integer page, Integer perPage, String uid) {
        return null;
    }

    @Override
    public boolean addFeed(String authToken, String url, String uid) {
        return false;
    }

    @Override
    public boolean removeFeed(String authToken, Integer feedId, String uid) {
        return false;
    }

    @Override
    public boolean updateSchedulingForUser(String authToken, String uid) {
        return false;
    }

    @Override
    public void updateSetting(String key, String val, String authToken, String uid) {

    }

    @Override
    public void createUser(String uid) {

    }
}
