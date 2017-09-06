package ru.terra.jbrss.fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import ru.terra.jbrss.R;
import ru.terra.jbrss.activity.LoginActivity;
import ru.terra.jbrss.core.JBRssAccount;
import ru.terra.jbrss.net.Requestor;
import ru.terra.jbrss.net.dto.LoginDTO;
import ru.terra.jbrss.net.impl.RequestorImpl;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private EditText mLogin;

    private EditText mPassword;

    private Button mSignInButton;

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.f_login, container, false);
        mLogin = (EditText) view.findViewById(R.id.login);
        mPassword = (EditText) view.findViewById(R.id.password);
        mSignInButton = (Button) view.findViewById(R.id.btn_sign_in);
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == mSignInButton) {
            if (TextUtils.isEmpty(mLogin.getText())) {
                mLogin.setError(getString(R.string.login));
            } else if (TextUtils.isEmpty(mPassword.getText())) {
                mPassword.setError(getString(R.string.password));
            } else {
                final String login = mLogin.getText().toString();
                final String pass = mPassword.getText().toString();
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        final Requestor requestor = new RequestorImpl(getActivity());
                        requestor.login(login, pass, new Response.Listener<LoginDTO>() {
                            @Override
                            public void onResponse(LoginDTO response) {
                                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString(
                                        getActivity().getString(R.string.myuid),
                                        response.uid
                                ).apply();
                                ((LoginActivity) getActivity()).onTokenReceived(
                                        new JBRssAccount(mLogin.getText().toString()),
                                        mPassword.getText().toString(),
                                        response.token_type + " " + response.access_token
                                );
                            }
                        });
                        return null;
                    }
                }.execute();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSignInButton.setOnClickListener(this);
    }

    @Override
    public void onPause() {
        mSignInButton.setOnClickListener(null);
        super.onPause();
    }
}
