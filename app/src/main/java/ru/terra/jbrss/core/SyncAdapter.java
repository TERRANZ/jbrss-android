package ru.terra.jbrss.core;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.android.volley.Response;

import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.FeedDto;
import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.impl.RequestorImpl;
import ru.terra.jbrss.storage.entity.FeedContract;
import ru.terra.jbrss.storage.entity.PostContract;


public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private ContentResolver mContentResolver;
    private Requestor requestor;
    private AccountManager mAccountManager;


    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs, ContentResolver mContentResolver) {
        super(context, autoInitialize, allowParallelSyncs);
        this.mContentResolver = mContentResolver;
        this.requestor = new RequestorImpl(context);
        mAccountManager = AccountManager.get(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
        this.requestor = new RequestorImpl(context);
        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, final ContentProviderClient provider, final SyncResult syncResult) {
        try {
            String authToken = mAccountManager.blockingGetAuthToken(account, JBRssAccount.TYPE, true);
            requestor.getFeeds(authToken, new Response.Listener<FeedListDto>() {
                @Override
                public void onResponse(FeedListDto response) {
                    for (FeedDto feed : response.getData()) {
                        try {
                            if (!checkFeed(feed.id, provider)) {
                                ContentValues cv = new ContentValues();
                                cv.put(FeedContract.FeedEntry.EXTERNAL_ID, feed.id);
                                cv.put(FeedContract.FeedEntry.NAME, feed.feedname);
                                cv.put(FeedContract.FeedEntry.UPDATE_TIME, feed.updateTime);
                                cv.put(FeedContract.FeedEntry.URL, feed.feedurl);
                                provider.insert(FeedContract.CONTENT_URI, cv);
                                Log.i(this.getClass().getName(), "Loaded feed: " + feed.feedname);
                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkFeed(Integer id, final ContentProviderClient provider) throws RemoteException {
        return checkEntity(id, FeedContract.CONTENT_URI, provider);
    }

    private boolean checkPost(Integer id, final ContentProviderClient provider) throws RemoteException {
        return checkEntity(id, PostContract.CONTENT_URI, provider);
    }

    private boolean checkEntity(Integer id, Uri uri, final ContentProviderClient provider) throws RemoteException {
        Cursor c = null;
        try {
            c = provider.query(
                    uri,
                    new String[]{"ext_id"},
                    "ext_id = ?",
                    new String[]{id.toString()},
                    null
            );
            if (c == null)
                return false;
            if (c.getCount() == 1) {
                return true;
            }
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
        return false;
    }
}
