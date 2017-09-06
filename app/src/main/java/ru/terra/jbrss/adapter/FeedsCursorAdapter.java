package ru.terra.jbrss.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.terra.jbrss.R;
import ru.terra.jbrss.storage.entity.FeedContract;

public class FeedsCursorAdapter extends CursorAdapter {

    public FeedsCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return View.inflate(context, R.layout.i_feeds, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.tv_feed_name)).setText(cursor.getString(cursor.getColumnIndex(FeedContract.FeedEntry.NAME)));
        view.setTag(cursor.getInt(cursor.getColumnIndex(FeedContract.FeedEntry.EXTERNAL_ID)));
    }
}
