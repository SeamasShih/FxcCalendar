package com.honhai.foxconn.fxccalendar.data.local.remindersql;

import android.content.Context;
import android.util.Log;

import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;

import java.util.ArrayList;
import java.util.Calendar;

import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_FIFTEEN_BEFORE;
import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_NONE;
import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_ONE_DAY_BEFORE;
import static com.honhai.foxconn.fxccalendar.addevent.AddEventActivity.REMINDER_ONE_HOUR_BEFORE;

public class ReminderUtils {
    private static OnRefreshReminderCompleteCallBack onRefreshReminderCompleteCallBack;

    public static void refreshReminderTable(Context context) {
        ReminderDAO reminderDAO = new ReminderDAO(context);
        ArrayList<Event> events = Data.getInstance().getEvents();
        ArrayList<Reminder> reminders = Data.getInstance().getReminders();
        int lastSize = reminders.size();

        boolean[] isConFirm = new boolean[reminders.size()];
        for (int i = 0; i < isConFirm.length; i++)
            isConFirm[i] = false;

        int[] groupIds = new int[Data.getInstance().getGroups().size()];
        for (int i = 0 ; i < groupIds.length ; i++){
            groupIds[i] = Data.getInstance().getGroups().get(i).getGroupId();
        }

        for (int i = 0; i < events.size(); i++) {
            String objectId = events.get(i).getObjectId();
            if (events.get(i).isNoTime() || events.get(i).isComplete())
                continue;

            boolean isUserGroup = false;
            for (int id : groupIds) {
                int groupId = events.get(i).getGroupId();
                if (groupId == id) {
                    isUserGroup = true;
                    break;
                }
            }
            if (!isUserGroup)
                continue;

            for (int j = 0; j < reminders.size(); j++) {
                if (isConFirm[j])
                    continue;
                Reminder reminder = reminders.get(j);
                if (objectId.equals(reminder.getEventObjectId())) {
                    isConFirm[j] = true;
                    reminder.setReminderTime(getReminderTime(events.get(i), reminder.getReminderType()));
                    reminderDAO.update(reminder);
                    break;
                }
            }
        }

        for (int i = 0; i < isConFirm.length; i++) {
            if (!isConFirm[i]) {
                reminderDAO.delete(reminders.remove(i).getLocalId());
            }
        }

        if (onRefreshReminderCompleteCallBack != null)
            onRefreshReminderCompleteCallBack.onComplete(getWillHappenReminders(reminders), lastSize);
    }

    public static void updateEventReminder(Event event, int reminderType, Context context) {
        String eventObjectId = event.getObjectId();
        ReminderDAO reminderDAO = new ReminderDAO(context);
        ArrayList<Reminder> reminders = Data.getInstance().getReminders();
        int lastSize = reminders.size();
        for (int i = 0; i < reminders.size(); i++) {
            Reminder reminder = reminders.get(i);
            if (eventObjectId.equals(reminder.getEventObjectId())) {
                if (event.isNoTime() || event.isComplete()){
                    reminderDAO.delete(reminder.getLocalId());
                    reminders.remove(reminder);
                }else {
                    reminder.set(event, reminderType);
                    reminderDAO.update(reminder);
                    Data.getInstance().setReminders(reminderDAO.getAll());
                }
                if (onRefreshReminderCompleteCallBack != null)
                    onRefreshReminderCompleteCallBack.onComplete(getWillHappenReminders(Data.getInstance().getReminders()), lastSize);
                return;
            }
        }
        if (reminderType == REMINDER_NONE || event.isNoTime() || event.isComplete())
            return;
        Reminder reminder = new Reminder();
        for (Group group : Data.getInstance().getGroups()){
            if (group.getGroupId() == event.getGroupId()) {
                reminder.setGroupName(group.getGroupName());
            }
        }
        reminder.setEventTile(event.getTitle());
        reminder.setReminderType(reminderType);
        reminder.setReminderTime(getReminderTime(event, reminderType));
        reminder.setEventColor(event.getColor());
        reminder.setEventObjectId(event.getObjectId());
        reminderDAO.insert(reminder);
        Data.getInstance().setReminders(reminderDAO.getAll());
        if (onRefreshReminderCompleteCallBack != null)
            onRefreshReminderCompleteCallBack.onComplete(getWillHappenReminders(Data.getInstance().getReminders()), lastSize);
    }

    private static ArrayList<Reminder> getWillHappenReminders(ArrayList<Reminder> reminders){
        ArrayList<Reminder> willHappenReminders = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        Calendar remindTime = Calendar.getInstance();
        for (Reminder reminder : reminders){
            remindTime.setTimeInMillis(reminder.getReminderTime());
            if (remindTime.after(now))
                willHappenReminders.add(reminder);
        }
        return willHappenReminders;
    }

    private static long getReminderTime(Event event, int reminderType) {
        Calendar calendar = Calendar.getInstance();
        if (event.isAllDay())
            calendar.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth(), 0, 0,0);
        else
            calendar.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth(), event.getStartHour(), event.getStartMinute(),0);
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
        return calendar.getTimeInMillis();
    }

    public static void setOnRefreshReminderCompleteCallBack(OnRefreshReminderCompleteCallBack onRefreshReminderCompleteCallBack) {
        ReminderUtils.onRefreshReminderCompleteCallBack = onRefreshReminderCompleteCallBack;
    }

    public interface OnRefreshReminderCompleteCallBack {
        void onComplete(ArrayList<Reminder> reminders, int lastSize);
    }
}
