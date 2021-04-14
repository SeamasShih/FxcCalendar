package com.honhai.foxconn.fxccalendar.data.local.eventsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalEventOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "local_event_db";
    private static final int VERSION = 3;
    private static SQLiteDatabase database;

    private LocalEventOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new LocalEventOpenHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(EventDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EventDAO.TABLE_NAME);
        onCreate(db);
    }
}
