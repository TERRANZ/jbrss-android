package ru.terra.jbrss.net;


import com.android.volley.Response;

import org.json.JSONObject;

import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.dto.LoginDTO;

public interface Requestor {
    void login(String user, String pass, Response.Listener<LoginDTO> listener);

    void getFeeds(String authToken, Response.Listener<FeedListDto> listener);

    void getFeedPosts(String authToken, Integer targetFeed, Integer page, Integer perPage, Response.Listener<JSONObject> listener);
}
