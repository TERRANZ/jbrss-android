package ru.terra.jbrss.activity;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;

import ru.terra.jbrss.R;
import ru.terra.jbrss.core.JBRssAccount;
import ru.terra.jbrss.fragment.LoginFragment;

public class LoginActivity extends AccountAuthenticatorActivity {

    public static String EXTRA_TOKEN_TYPE = "jbrss_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment);
        if (savedInstanceState == null) {
            final AccountManager am = AccountManager.get(this);
            final Account availableAccounts[] = am.getAccountsByType(JBRssAccount.TYPE);
            if (availableAccounts.length > 0) {
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
