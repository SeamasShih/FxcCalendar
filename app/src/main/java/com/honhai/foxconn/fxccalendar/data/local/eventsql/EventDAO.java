package com.honhai.foxconn.fxccalendar.data.local.eventsql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;

import java.util.ArrayList;

import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_COMPLETED;
import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_UNCOMPLETED;

public class EventDAO {

    static final String TABLE_NAME = "event";

    private static final String KEY_ID = "_id";

    private static final String TITLE_COLUMN = "title";
    private static final String START_YEAR_COLUMN = "startYear";
    private static final String START_MONTH_COLUMN = "startMonth";
    private static final String START_DAY_OF_MONTH_COLUMN = "startDayOfMonth";
    private static final String START_HOUR_COLUMN = "startHour";
    private static final String START_MINUTE_COLUMN = "startMinute";
    private static final String END_YEAR_COLUMN = "endYear";
    private static final String END_MONTH_COLUMN = "endMonth";
    private static final String END_DAY_OF_MONTH_COLUMN = "endDayOfMonth";
    private static final String END_HOUR_COLUMN = "endHour";
    private static final String END_MINUTE_COLUMN = "endMinute";
    private static final String IS_ALL_DAY_COLUMN = "isAllDay";
    private static final String COLOR_COLUMN = "color";
    private static final String REPEAT_COLUMN = "repeat";
    private static final String IS_COMPLETE_COLUMN = "isComplete";
    private static final String GROUP_ID_COLUMN = "groupId";
    private static final String NOTE_COLUMN = "note";
    private static final String MAP_COLUMN = "map";
    private static final String URL_COLUMN = "url";
    private static final String IS_NO_TIME_COLUMN = "isNoTime";
    private static final String OBJECT_ID_COLUMN = "objectId";

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE_COLUMN + " INTEGER NOT NULL, " +
                    START_YEAR_COLUMN + " INTEGER NOT NULL, " +
                    START_MONTH_COLUMN + " INTEGER NOT NULL, " +
                    START_DAY_OF_MONTH_COLUMN + " INTEGER NOT NULL, " +
                    START_HOUR_COLUMN + " INTEGER NOT NULL, " +
                    START_MINUTE_COLUMN + " INTEGER NOT NULL, " +
                    END_YEAR_COLUMN + " INTEGER NOT NULL, " +
                    END_MONTH_COLUMN + " INTEGER NOT NULL, " +
                    END_DAY_OF_MONTH_COLUMN + " INTEGER NOT NULL, " +
                    END_HOUR_COLUMN + " INTEGER NOT NULL, " +
                    END_MINUTE_COLUMN + " INTEGER NOT NULL, " +
                    IS_ALL_DAY_COLUMN + " TEXT NOT NULL, " +
                    COLOR_COLUMN + " INTEGER NOT NULL, " +
                    REPEAT_COLUMN + " INTEGER NOT NULL, " +
                    IS_COMPLETE_COLUMN + " TEXT NOT NULL, " +
                    GROUP_ID_COLUMN + " INTEGER NOT NULL, " +
                    NOTE_COLUMN + " TEXT, " +
                    MAP_COLUMN + " TEXT, " +
                    URL_COLUMN + " TEXT, " +
                    IS_NO_TIME_COLUMN + " TEXT NOT NULL, " +
                    OBJECT_ID_COLUMN + " TEXT NOT NULL) ";

    private SQLiteDatabase db;

    public EventDAO(Context context) {
        db = LocalEventOpenHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public Event insert(Event event) {
        ContentValues cv = new ContentValues();

        cv.put(TITLE_COLUMN, event.getTitle());
        cv.put(START_YEAR_COLUMN, event.getStartYear());
        cv.put(START_MONTH_COLUMN, event.getStartMonth());
        cv.put(START_DAY_OF_MONTH_COLUMN, event.getStartDayOfMonth());
        cv.put(START_HOUR_COLUMN, event.getStartHour());
        cv.put(START_MINUTE_COLUMN, event.getStartMinute());
        cv.put(END_YEAR_COLUMN, event.getEndYear());
        cv.put(END_MONTH_COLUMN, event.getEndMonth());
        cv.put(END_DAY_OF_MONTH_COLUMN, event.getEndDayOfMonth());
        cv.put(END_HOUR_COLUMN, event.getEndHour());
        cv.put(END_MINUTE_COLUMN, event.getEndMinute());
        cv.put(IS_ALL_DAY_COLUMN, event.isAllDay() ? TRUE : FALSE);
        cv.put(COLOR_COLUMN, event.getColor());
        cv.put(REPEAT_COLUMN, event.getRepeat());
        cv.put(IS_COMPLETE_COLUMN, event.isComplete() ? TRUE : FALSE);
        cv.put(GROUP_ID_COLUMN, event.getGroupId());
        cv.put(NOTE_COLUMN, event.getNote());
        cv.put(MAP_COLUMN, event.getMap());
        cv.put(URL_COLUMN, event.getUrl());
        cv.put(IS_NO_TIME_COLUMN, event.isNoTime() ? TRUE : FALSE);
        cv.put(OBJECT_ID_COLUMN, event.getObjectId());

        long id = db.insert(TABLE_NAME, null, cv);

        event.setLocalId(id);

        return event;
    }

    public boolean update(Event event) {
        ContentValues cv = new ContentValues();

        cv.put(TITLE_COLUMN, event.getTitle());
        cv.put(START_YEAR_COLUMN, event.getStartYear());
        cv.put(START_MONTH_COLUMN, event.getStartMonth());
        cv.put(START_DAY_OF_MONTH_COLUMN, event.getStartDayOfMonth());
        cv.put(START_HOUR_COLUMN, event.getStartHour());
        cv.put(START_MINUTE_COLUMN, event.getStartMinute());
        cv.put(END_YEAR_COLUMN, event.getEndYear());
        cv.put(END_MONTH_COLUMN, event.getEndMonth());
        cv.put(END_DAY_OF_MONTH_COLUMN, event.getEndDayOfMonth());
        cv.put(END_HOUR_COLUMN, event.getEndHour());
        cv.put(END_MINUTE_COLUMN, event.getEndMinute());
        cv.put(IS_ALL_DAY_COLUMN, event.isAllDay() ? TRUE : FALSE);
        cv.put(COLOR_COLUMN, event.getColor());
        cv.put(REPEAT_COLUMN, event.getRepeat());
        cv.put(IS_COMPLETE_COLUMN, event.isComplete() ? TRUE : FALSE);
        cv.put(GROUP_ID_COLUMN, event.getGroupId());
        cv.put(NOTE_COLUMN, event.getNote());
        cv.put(MAP_COLUMN, event.getMap());
        cv.put(URL_COLUMN, event.getUrl());
        cv.put(IS_NO_TIME_COLUMN, event.isNoTime() ? TRUE : FALSE);
        cv.put(OBJECT_ID_COLUMN, event.getObjectId());

        String where = KEY_ID + "=" + event.getLocalId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public void removeAll() {
        db.delete(TABLE_NAME, null, null);
    }

    public ArrayList<Event> getAll() {
        ArrayList<Event> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public ArrayList<Event> getEventsByGroups(ArrayList<Group> groups, int eventType) {
        ArrayList<Event> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);
        switch (eventType) {
            case EVENTS_COMPLETED:
                while (cursor.moveToNext()) {
                    for (int i = 0; i < groups.size(); i++) {
                        if (cursor.getInt(16) == groups.get(i).getGroupId() &&
                                cursor.getString(15).equals(TRUE)) {
                            result.add(getRecord(cursor));
                            break;
                        }
                    }
                }
                break;
            case EVENTS_UNCOMPLETED:
                while (cursor.moveToNext()) {
                    for (int i = 0; i < groups.size(); i++) {
                        if (cursor.getInt(16) == groups.get(i).getGroupId() &&
                                cursor.getString(15).equals(FALSE)) {
                            result.add(getRecord(cursor));
                            break;
                        }
                    }
                }
                break;
            default:
                while (cursor.moveToNext()) {
                    for (int i = 0; i < groups.size(); i++) {
                        if (cursor.getInt(16) == groups.get(i).getGroupId()) {
                            result.add(getRecord(cursor));
                            break;
                        }
                    }
                }
                break;
        }

        cursor.close();
        return result;
    }

    public Event get(long id) {
        Event item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        if (result.moveToFirst()) {
            item = getRecord(result);
        }

        result.close();
        return item;
    }

    private Event getRecord(Cursor cursor) {
        Event result = new Event();

        result.setLocalId(cursor.getLong(0));
        result.setTitle(cursor.getString(1));
        result.setStartYear(cursor.getInt(2));
        result.setStartMonth(cursor.getInt(3));
        result.setStartDayOfMonth(cursor.getInt(4));
        result.setStartHour(cursor.getInt(5));
        result.setStartMinute(cursor.getInt(6));
        result.setEndYear(cursor.getInt(7));
        result.setEndMonth(cursor.getInt(8));
        result.setEndDayOfMonth(cursor.getInt(9));
        result.setEndHour(cursor.getInt(10));
        result.setEndMinute(cursor.getInt(11));
        result.setAllDay(cursor.getString(12).equals(TRUE));
        result.setColor(cursor.getInt(13));
        result.setRepeat(cursor.getInt(14));
        result.setComplete(cursor.getString(15).equals(TRUE));
        result.setGroupId(cursor.getInt(16));
        result.setNote(cursor.getString(17));
        result.setMap(cursor.getString(18));
        result.setUrl(cursor.getString(19));
        result.setNoTime(cursor.getString(20).equals(TRUE));
        result.setObjectId(cursor.getString(21));

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
