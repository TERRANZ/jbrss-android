package ru.terra.jbrss.net.impl;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import java.util.HashMap;
import java.util.Map;

import ru.terra.jbrss.R;
import ru.terra.jbrss.net.Constants;
import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.dto.FeedPostsPageableDto;
import ru.terra.jbrss.net.dto.LoginDTO;

public class RequestorImpl implements Requestor {

    private Context context;
    private RequestQueue mRequestQueue;

    public RequestorImpl(Context context) {
        this.context = context;
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();

    }

    @Override
    public void login(String user, String pass, Response.Listener<LoginDTO> listener) {
        String url = context.getString(R.string.authserver) + Constants.Account.TOKEN;
        Map<String, String> request = new HashMap<>();
        request.put("grant_type", "client_credentials");
        request.put("scope", "read");
        request.put("client_id", user);
        request.put("client_secret", pass);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        VolleyRequest<LoginDTO> vr = new VolleyRequest<>(Request.Method.POST, url, LoginDTO.class, headers, request, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(RequestorImpl.class.getName(), "Error while login", error);
            }
        });
        vr.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        mRequestQueue.add(vr);
    }

    @Override
    public void getFeeds(String authToken, Response.Listener<FeedListDto> listener, Response.ErrorListener errorListener) {
        String myId = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.myuid),
                ""
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authToken);
        headers.put("Content-Type", "application/json");
        String url = context.getString(R.string.rssservice) + myId + Constants.Rss.FEED;
        VolleyRequest<FeedListDto> vr = new VolleyRequest<>(
                Request.Method.GET,
                url,
                FeedListDto.class,
                headers,
                new HashMap<String, String>(),
                listener,
                errorListener);
        vr.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                4,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(vr);
    }

    @Override
    public void getFeedPosts(String authToken, Integer targetFeed, Integer page, Integer perPage, Response.Listener<FeedPostsPageableDto> listener, Response.ErrorListener errorListener) {
        String myId = PreferenceManager.getDefaultSharedPreferences(context).getString(
                context.getString(R.string.myuid),
                ""
        );
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", authToken);
        headers.put("Content-Type", "application/json");
        String url = context.getString(R.string.rssservice) + myId + Constants.Rss.FEED_POSTS;
        url = url.replace("{fid}", targetFeed.toString());
        url += "?page=";
        url += page;
        url += "&limit=";
        url += perPage;
        VolleyRequest<FeedPostsPageableDto> vr = new VolleyRequest<>(
                Request.Method.GET,
                url,
                FeedPostsPageableDto.class,
                headers,
                new HashMap<String, String>(),
                listener,
                errorListener);
        vr.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                4,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(vr);
    }

}
