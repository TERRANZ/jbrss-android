package ru.terra.jbrss.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import ru.terra.jbrss.R;
import ru.terra.jbrss.activity.ShowPostActivity;
import ru.terra.jbrss.adapter.PostsCursorAdapter;
import ru.terra.jbrss.core.CursorsCache;
import ru.terra.jbrss.storage.entity.PostContract;

public class PostsFragment extends Fragment {
    private int fid;
    private Cursor c;

    public PostsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView view = (ListView) inflater.inflate(R.layout.f_posts_list, container, false);
        c = getActivity().getContentResolver().query(
                PostContract.CONTENT_URI,
                null,
                PostContract.PostEntity.FEED_ID + " = ?",
                new String[]{getArguments().getString("fid")},
                PostContract.PostEntity.DATE + " DESC"
        );
        view.setAdapter(new PostsCursorAdapter(getActivity(), c));
        CursorsCache.getInstance().setPostCursor(c);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getActivity().startActivity(new Intent(getActivity(), ShowPostActivity.class).putExtra("pos", position));
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        c.close();
    }
}
