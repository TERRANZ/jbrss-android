package ru.terra.jbrss.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ru.terra.jbrss.R;
import ru.terra.jbrss.core.IOHelper;

/**
 * Created by Vadim_Korostelev on 8/14/2017.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "jbrss";
    Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("DbHelper", "onCreate()");
        String sql = IOHelper.readResourceAsString(context, R.raw.create_db);
        String[] strings = sql.split(";");
        executeStatements(strings, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void executeStatements(String[] strings, SQLiteDatabase db) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i].replace("\n", "").trim().replace("\t", "");
            if (str.length() > 0) {
                db.execSQL(strings[i]);
            }
        }
    }
}
