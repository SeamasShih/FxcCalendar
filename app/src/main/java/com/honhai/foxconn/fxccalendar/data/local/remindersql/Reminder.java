package com.honhai.foxconn.fxccalendar.data.local.remindersql;

import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;

import java.util.Calendar;

import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_FIFTEEN_BEFORE;
import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_ONE_DAY_BEFORE;
import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_ONE_HOUR_BEFORE;

public class Reminder {
    private String groupName = "";
    private String eventTile = "";
    private long reminderTime;
    private int eventColor;
    private String eventObjectId = "";
    private int reminderType = 0;

    private long localId;

    public void set(Event event, int reminderType) {
        for (Group group : Data.getInstance().getGroups()) {
            if (group.getGroupId() == event.getGroupId()) {
                this.groupName = group.getGroupName();
            }
        }
        this.eventTile = event.getTitle();
        Calendar calendar = Calendar.getInstance();
        if (event.isAllDay())
            calendar.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth(), 0, 0, 0);
        else
            calendar.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth(), event.getStartHour(), event.getStartMinute(), 0);
        switch (reminderType) {
            case REMINDER_FIFTEEN_BEFORE:
                calendar.add(Calendar.MINUTE, -15);
                break;
            case REMINDER_ONE_HOUR_BEFORE:
                calendar.add(Calendar.HOUR, -1);
                break;
            case REMINDER_ONE_DAY_BEFORE:
                calendar.add(Calendar.DAY_OF_YEAR, -1);
                break;
        }
        this.reminderTime = calendar.getTimeInMillis();
        this.eventColor = event.getColor();
        this.eventObjectId = event.getObjectId();
        this.reminderType = reminderType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getEventTile() {
        return eventTile;
    }

    public void setEventTile(String eventTile) {
        this.eventTile = eventTile;
    }

    public long getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(long reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getEventColor() {
        return eventColor;
    }

    public void setEventColor(int eventColor) {
        this.eventColor = eventColor;
    }

    public long getLocalId() {
        return localId;
    }

    public void setLocalId(long localId) {
        this.localId = localId;
    }

    public String getEventObjectId() {
        return eventObjectId;
    }

    public void setEventObjectId(String eventObjectId) {
        this.eventObjectId = eventObjectId;
    }

    public int getReminderType() {
        return reminderType;
    }

    public void setReminderType(int reminderType) {
        this.reminderType = reminderType;
    }
}
