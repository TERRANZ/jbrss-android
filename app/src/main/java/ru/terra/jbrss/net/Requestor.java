package ru.terra.jbrss.net;


import com.android.volley.Response;

import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.dto.FeedPostsPageableDto;
import ru.terra.jbrss.net.dto.LoginDTO;

public interface Requestor {
    void login(String user, String pass, Response.Listener<LoginDTO> listener);

    void getFeeds(String authToken, Response.Listener<FeedListDto> listener, Response.ErrorListener errorListener);

    void getFeedPosts(String authToken, Integer targetFeed, Integer page, Integer perPage, Response.Listener<FeedPostsPageableDto> listener, Response.ErrorListener errorListener);
}
