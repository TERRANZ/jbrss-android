package ru.terra.jbrss.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.terra.jbrss.R;
import ru.terra.jbrss.net.Requestor;

public class FeedsFragment extends Fragment {

    public FeedsFragment() {
    }

    public static FeedsFragment newInstance(int columnCount) {
        return new FeedsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_feeds_list, container, false);
    }


}
