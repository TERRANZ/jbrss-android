package ru.terra.jbrss.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.terra.jbrss.R;
import ru.terra.jbrss.activity.parts.FeedsFragment;
import ru.terra.jbrss.activity.parts.PostsFragment;
import ru.terra.jbrss.net.dto.FeedPostDto;

public class PostsActivity extends FragmentActivity implements PostsFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame1, new PostsFragment())
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(FeedPostDto item) {

    }
}
