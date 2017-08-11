package ru.terra.jbrss.core;

import android.accounts.Account;

public class JBRssAccount extends Account {
    public static final String TYPE = "ru.terra.jbrss.core.JBRssAccount";

    public static final String TOKEN_FULL_ACCESS = "ru.terra.jbrss.core.TOKEN_FULL_ACCESS";

    public static final String KEY_PASSWORD = "ru.terra.jbrss.core.KEY_PASSWORD";

    public JBRssAccount(String name) {
        super(name, TYPE);
    }
}
