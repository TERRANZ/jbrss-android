package ru.terra.jbrss.core;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;

import ru.terra.jbrss.R;
import ru.terra.jbrss.activity.LoginActivity;
import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.LoginDTO;
import ru.terra.jbrss.net.impl.RequestorImpl;

public class Authenticator extends AbstractAccountAuthenticator {
    Context mContext;

    public Authenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, accountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        if (options != null) {
            bundle.putAll(options);
        }
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(final AccountAuthenticatorResponse response, final Account account, final String authTokenType, Bundle options) throws NetworkErrorException {
        final Bundle result = new Bundle();
        final AccountManager am = AccountManager.get(mContext.getApplicationContext());
        final String[] authToken = {am.peekAuthToken(account, authTokenType)};
        if (TextUtils.isEmpty(authToken[0])) {
            final String password = am.getPassword(account);
            if (!TextUtils.isEmpty(password)) {
                final Requestor requestor = new RequestorImpl(mContext);
                requestor.login(account.name, password, new Response.Listener<LoginDTO>() {
                    @Override
                    public void onResponse(LoginDTO loginDTO) {
                        authToken[0] = loginDTO.token_type + " " + loginDTO.access_token;
                        PreferenceManager.getDefaultSharedPreferences(mContext).edit().putString(
                                mContext.getString(R.string.myuid),
                                loginDTO.uid
                        ).apply();
                        Log.i(this.getClass().getName(), "Auth ok, token: " + authToken[0]);
                        if (!TextUtils.isEmpty(authToken[0])) {
                            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                            result.putString(AccountManager.KEY_AUTHTOKEN, authToken[0]);
                            am.setAuthToken(account, account.type, authToken[0]);
                            Log.i(this.getClass().getName(), "setAuthToken " + authToken[0]);
                        } else {
                            final Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
                            intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, authTokenType);
                            final Bundle bundle = new Bundle();
                            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
                            Log.i(this.getClass().getName(), "AccountManager.KEY_INTENT");
                        }
                    }
                });
            }
        }

        return result;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
