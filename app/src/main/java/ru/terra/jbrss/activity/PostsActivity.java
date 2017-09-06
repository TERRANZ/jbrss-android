package ru.terra.jbrss.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import ru.terra.jbrss.R;
import ru.terra.jbrss.fragment.PostsFragment;

public class PostsActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_fragment);
        if (savedInstanceState == null) {
            PostsFragment postsFragment = new PostsFragment();
            Bundle args = new Bundle();
            args.putString("fid", String.valueOf(getIntent().getIntExtra("fid", 0)));
            postsFragment.setArguments(args);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame1, postsFragment)
                    .commit();
        }
    }
}
