package com.honhai.foxconn.fxccalendar.data.local.usersql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.honhai.foxconn.fxccalendar.data.User;

import java.util.ArrayList;

public class UserDAO {

    static final String TABLE_NAME = "user";

    private static final String KEY_ID = "_id";

    private static final String WORK_ID_COLUMN = "workId";
    private static final String PASSWORD_COLUMN = "password";
    private static final String NAME_COLUMN = "name";
    private static final String ENGLISH_NAME_COLUMN = "englishName";
    private static final String OBJECT_ID_COLUMN = "objectId";

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORK_ID_COLUMN + " TEXT NOT NULL, " +
                    PASSWORD_COLUMN + " TEXT NOT NULL, " +
                    NAME_COLUMN + " TEXT NOT NULL, " +
                    ENGLISH_NAME_COLUMN + " TEXT NOT NULL, " +
                    OBJECT_ID_COLUMN + " TEXT NOT NULL) ";

    private SQLiteDatabase db;

    public UserDAO(Context context) {
        db = LocalUserOpenHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public User insert(User user) {
        ContentValues cv = new ContentValues();

        cv.put(WORK_ID_COLUMN, user.getWorkId());
        cv.put(PASSWORD_COLUMN, user.getPassword());
        cv.put(NAME_COLUMN, user.getName());
        cv.put(ENGLISH_NAME_COLUMN, user.getEnglishName());
        cv.put(OBJECT_ID_COLUMN, user.getObjectId());

        long id = db.insert(TABLE_NAME, null, cv);

        user.setLocalId(id);

        return user;
    }

    public boolean update(User user) {
        ContentValues cv = new ContentValues();

        cv.put(WORK_ID_COLUMN, user.getWorkId());
        cv.put(PASSWORD_COLUMN, user.getPassword());
        cv.put(NAME_COLUMN, user.getName());
        cv.put(ENGLISH_NAME_COLUMN, user.getEnglishName());
        cv.put(OBJECT_ID_COLUMN, user.getObjectId());

        String where = KEY_ID + "=" + user.getLocalId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public void removeAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<User> getAll() {
        ArrayList<User> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public User get(long id) {
        User item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }

    private User getRecord(Cursor cursor) {
        User result = new User();

        result.setLocalId(cursor.getLong(0));
        result.setWorkId(cursor.getString(1));
        result.setPassword(cursor.getString(2));
        result.setName(cursor.getString(3));
        result.setEnglishName(cursor.getString(4));

        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        cursor.close();
        return result;
    }
}
