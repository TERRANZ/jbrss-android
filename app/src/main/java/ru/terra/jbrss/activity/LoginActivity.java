package ru.terra.jbrss.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;

import ru.terra.jbrss.R;
import ru.terra.jbrss.core.JBRssAccount;
import ru.terra.jbrss.fragment.LoginFragment;
import ru.terra.jbrss.net.Constants;

public class LoginActivity extends AccountAuthenticatorActivity {

    public static String EXTRA_TOKEN_TYPE = "jbrss_login";
    public static final long SECONDS_PER_MINUTE = 60L;
    public static final long SYNC_INTERVAL_IN_MINUTES = 15L;
    public static final long SYNC_INTERVAL =
            SYNC_INTERVAL_IN_MINUTES *
                    SECONDS_PER_MINUTE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment);
        if (savedInstanceState == null) {
            final AccountManager am = AccountManager.get(this);
            final Account availableAccounts[] = am.getAccountsByType(JBRssAccount.TYPE);
            if (availableAccounts.length > 0) {
                Bundle settings = new Bundle();
                settings.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settings.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
                ContentResolver.requestSync(availableAccounts[0], Constants.AUTHORITY, settings);
                ContentResolver.addPeriodicSync(
                        availableAccounts[0],
                        Constants.AUTHORITY,
                        Bundle.EMPTY,
                        SYNC_INTERVAL);
                startActivity(new Intent(this, FeedsActivity.class));
                finish();
            } else {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame1, new LoginFragment())
                        .commit();
            }
        }
    }

    public void onTokenReceived(JBRssAccount account, String password, String token) {
        final AccountManager am = AccountManager.get(this);
        final Bundle result = new Bundle();
        if (am.addAccountExplicitly(account, password, new Bundle())) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, token);
            am.setAuthToken(account, account.type, token);
        } else {
            result.putString(AccountManager.KEY_ERROR_MESSAGE, getString(R.string.account_already_exists));
        }
        setAccountAuthenticatorResult(result);
        setResult(RESULT_OK);
        finish();
    }
}
