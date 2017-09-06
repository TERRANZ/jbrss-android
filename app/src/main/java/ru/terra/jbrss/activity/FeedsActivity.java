package ru.terra.jbrss.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.android.volley.Response;

import java.io.IOException;

import ru.terra.jbrss.R;
import ru.terra.jbrss.core.JBRssAccount;
import ru.terra.jbrss.fragment.FeedsFragment;
import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.FeedListDto;
import ru.terra.jbrss.net.impl.RequestorImpl;

public class FeedsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame1, new FeedsFragment())
                    .commit();
        }

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Requestor requestor = new RequestorImpl(FeedsActivity.this);
                AccountManager am = AccountManager.get(FeedsActivity.this);
                final Account availableAccounts[] = am.getAccountsByType(JBRssAccount.TYPE);
                Account account = availableAccounts[0];
                AccountManagerFuture<Bundle> accountManagerFuture = am.getAuthToken(account, JBRssAccount.TYPE, null, FeedsActivity.this, null, null);
                String authToken = null;
                try {
                    Bundle authTokenBundle = accountManagerFuture.getResult();
                    authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
                    Log.i(this.getClass().getName(), "Authtoken " + authToken);
                } catch (OperationCanceledException | IOException | AuthenticatorException e) {
                    e.printStackTrace();
                }
                if (authToken != null) {
                    requestor.getFeeds(authToken, new Response.Listener<FeedListDto>() {
                        @Override
                        public void onResponse(FeedListDto response) {
                            Log.i(this.getClass().getName(), "Size: " + response.getData().size());
                        }
                    });
                }
                return null;
            }
        }.execute();


    }

}
