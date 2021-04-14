package com.honhai.foxconn.fxccalendar.data.local.usersql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalUserOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "local_user_db";
    private static final int VERSION = 1;
    private static SQLiteDatabase database;

    private LocalUserOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new LocalUserOpenHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserDAO.TABLE_NAME);
        onCreate(db);
    }
}
