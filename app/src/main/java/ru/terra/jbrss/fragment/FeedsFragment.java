package ru.terra.jbrss.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import ru.terra.jbrss.R;
import ru.terra.jbrss.adapter.FeedsCursorAdapter;
import ru.terra.jbrss.storage.entity.FeedContract;

public class FeedsFragment extends Fragment {

    public FeedsFragment() {
    }

    public static FeedsFragment newInstance(int columnCount) {
        return new FeedsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f_feeds_list, container, false);
        Cursor c = getActivity().getContentResolver().query(
                FeedContract.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        ((ListView) v).setAdapter(new FeedsCursorAdapter(getActivity(), c));
        return v;
    }


}
