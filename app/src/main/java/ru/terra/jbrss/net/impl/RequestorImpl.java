package ru.terra.jbrss.net.impl;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.terra.jbrss.R;
import ru.terra.jbrss.net.Constants;
import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.FeedDto;
import ru.terra.jbrss.net.dto.FeedPostDto;

public class RequestorImpl implements Requestor {

    private Context context;
    private RequestQueue mRequestQueue;

    public RequestorImpl(Context context) {
        this.context = context;

// Instantiate the cache
        Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

// Start the queue
        mRequestQueue.start();

    }

    @Override
    public void login(String user, String pass, Response.Listener<JSONObject> listener) {
        String url = context.getString(R.string.authserver) + Constants.Account.TOKEN;
        Map<String, String> request = new HashMap<>();
        request.put("grant_type", "client_credentials");
        request.put("scope", "rest");
        request.put("client_id", user);
        request.put("client_secret", pass);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        VolleyRequest<JSONObject> vr = new VolleyRequest<>(Request.Method.POST, url, JSONObject.class, headers, request, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(RequestorImpl.class.getName(), "Error while login", error);
            }
        });
        vr.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        mRequestQueue.add(vr);
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
