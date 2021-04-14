package com.honhai.foxconn.fxccalendar.data.local.remindersql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.honhai.foxconn.fxccalendar.data.Data;

import java.util.ArrayList;

public class ReminderDAO {

    static final String TABLE_NAME = "reminder";

    private static final String KEY_ID = "_id";

    private static final String GROUP_NAME_COLUMN = "groupName";
    private static final String EVENT_TITLE_COLUMN = "eventTile";
    private static final String REMINDER_TIME_COLUMN = "reminderTime";
    private static final String EVENT_COLOR_COLUMN = "eventColor";
    private static final String EVENT_OBJECT_ID_COLUMN = "eventObjectId";
    private static final String REMINDER_TYPE_COLUMN = "reminderType";

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    GROUP_NAME_COLUMN + " TEXT NOT NULL, " +
                    EVENT_TITLE_COLUMN + " TEXT NOT NULL, " +
                    REMINDER_TIME_COLUMN + " INTEGER NOT NULL, " +
                    EVENT_COLOR_COLUMN + " INTEGER NOT NULL, " +
                    EVENT_OBJECT_ID_COLUMN + " TEXT NOT NULL, " +
                    REMINDER_TYPE_COLUMN + " INTEGER NOT NULL) ";

    private SQLiteDatabase db;

    public ReminderDAO(Context context) {
        db = LocalReminderOpenHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public Reminder insert(Reminder reminder) {
        ContentValues cv = new ContentValues();

        cv.put(GROUP_NAME_COLUMN, reminder.getGroupName());
        cv.put(EVENT_TITLE_COLUMN, reminder.getEventTile());
        cv.put(REMINDER_TIME_COLUMN, reminder.getReminderTime());
        cv.put(EVENT_COLOR_COLUMN, reminder.getEventColor());
        cv.put(EVENT_OBJECT_ID_COLUMN, reminder.getEventObjectId());
        cv.put(REMINDER_TYPE_COLUMN, reminder.getReminderType());

        long id = db.insert(TABLE_NAME, null, cv);

        reminder.setLocalId(id);

        return reminder;
    }

    public boolean update(Reminder reminder) {
        ContentValues cv = new ContentValues();

        cv.put(GROUP_NAME_COLUMN, reminder.getGroupName());
        cv.put(EVENT_TITLE_COLUMN, reminder.getEventTile());
        cv.put(REMINDER_TIME_COLUMN, reminder.getReminderTime());
        cv.put(EVENT_COLOR_COLUMN, reminder.getEventColor());
        cv.put(EVENT_OBJECT_ID_COLUMN, reminder.getEventObjectId());
        cv.put(REMINDER_TYPE_COLUMN, reminder.getReminderType());

        String where = KEY_ID + "=" + reminder.getLocalId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public void removeAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Reminder> getAll() {
        ArrayList<Reminder> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public Reminder get(String eventObjectId) {
        Reminder item = null;
        for (Reminder reminder : Data.getInstance().getReminders()) {
            if (reminder.getEventObjectId().equals(eventObjectId)) {
                item = reminder;
                break;
            }
        }
        return item;
    }

    public Reminder get(long id) {
        Reminder item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }

    private Reminder getRecord(Cursor cursor) {
        Reminder result = new Reminder();

        result.setLocalId(cursor.getLong(0));
        result.setGroupName(cursor.getString(1));
        result.setEventTile(cursor.getString(2));
        result.setReminderTime(cursor.getLong(3));
        result.setEventColor(cursor.getInt(4));
        result.setEventObjectId(cursor.getString(5));
        result.setReminderType(cursor.getInt(6));

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
