package ru.terra.jbrss.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import ru.terra.jbrss.R;
import ru.terra.jbrss.fragment.FeedsFragment;

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
    }

}
