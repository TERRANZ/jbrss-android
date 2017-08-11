package ru.terra.jbrss.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import ru.terra.jbrss.R;
import ru.terra.jbrss.activity.parts.FeedsFragment;
import ru.terra.jbrss.net.dto.FeedDto;

public class FeedsActivity extends FragmentActivity implements FeedsFragment.OnListFragmentInteractionListener {

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


    @Override
    public void onListFragmentInteraction(FeedDto item) {

    }
}
