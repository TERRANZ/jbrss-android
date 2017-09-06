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
import ru.terra.jbrss.activity.PostsActivity;
import ru.terra.jbrss.adapter.FeedsCursorAdapter;
import ru.terra.jbrss.storage.entity.FeedContract;

public class FeedsFragment extends Fragment {

    private Cursor c;

    public FeedsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.f_feeds_list, container, false);
        c = getActivity().getContentResolver().query(
                FeedContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        ((ListView) v).setAdapter(new FeedsCursorAdapter(getActivity(), c));
        ((ListView) v).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Integer fid = (Integer) view.getTag();
                Intent startIntent = new Intent(getActivity(), PostsActivity.class);
                startIntent.putExtra("fid", fid);
                startActivity(startIntent);
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        c.close();
    }
}
