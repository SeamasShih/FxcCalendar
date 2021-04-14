package com.honhai.foxconn.fxccalendar.welcome;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.User;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.Reminder;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.ReminderDAO;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.ReminderUtils;
import com.honhai.foxconn.fxccalendar.data.local.usersql.UserDAO;
import com.honhai.foxconn.fxccalendar.login.Register;
import com.honhai.foxconn.fxccalendar.notification.AlarmReceiver;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Data.getInstance().clearRefreshView();
        refreshUser();
        refreshReminder();
        setReminder();
//        Data.getInstance().createUser();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    startActivity(new Intent().setClass(WelcomeActivity.this, Register.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void refreshReminder() {
        ReminderDAO reminderDAO = new ReminderDAO(this);
        Data.getInstance().setReminders(reminderDAO.getAll());
    }

    private void refreshUser() {
        final UserDAO userDAO = new UserDAO(this);
        ArrayList<User> users = Data.getInstance().getUsers();
        users.clear();
        users.addAll(userDAO.getAll());

        Data.getInstance().refreshUsersForCloud();
        Data.getInstance().setOnDownLoadUserCompleteCallBack(new Data.OnDownLoadUserCompleteCallBack() {
            @Override
            public void onComplete(ArrayList<User> users) {
                userDAO.removeAll();
                for (int i = 0; i < users.size(); i++)
                    userDAO.insert(users.get(i));
            }
        });
    }

    private void setReminder() {
        ReminderUtils.setOnRefreshReminderCompleteCallBack(new ReminderUtils.OnRefreshReminderCompleteCallBack() {
            @Override
            public void onComplete(ArrayList<Reminder> reminders, int lastSize) {
                if (alarmMgr != null) {
                    for (int c = 0; c < lastSize; c++) {
                        Intent it = new Intent(WelcomeActivity.this, AlarmReceiver.class);
                        alarmIntent = PendingIntent.getBroadcast(WelcomeActivity.this, c, it, 0);
                        alarmMgr.cancel(alarmIntent);
                    }
                }
                int i = 0;
                for (Reminder reminder : reminders) {
                    String reminderTypeString = "";
                    switch (reminder.getReminderType()) {
                        case 1:
                            reminderTypeString = getString(R.string.reminderOnTime);
                            break;
                        case 2:
                            reminderTypeString = getString(R.string.reminderFifteenAfter);
                            break;
                        case 3:
                            reminderTypeString = getString(R.string.reminderOneHourAfter);
                            break;
                        case 4:
                            reminderTypeString = getString(R.string.reminderOneDayAfter);
                            break;
                    }
                    alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Intent it = new Intent(WelcomeActivity.this, AlarmReceiver.class);
                    it.putExtra("groupName", reminder.getGroupName());
                    it.putExtra("message", reminder.getEventTile() + "  -  " + reminderTypeString);
                    it.putExtra("num", i);
                    it.putExtra("color", reminder.getEventColor());
                    alarmIntent = PendingIntent.getBroadcast(WelcomeActivity.this, i, it, 0);
                    alarmMgr.set(AlarmManager.RTC_WAKEUP, reminder.getReminderTime(), alarmIntent);
                    i++;
                }
            }

        });
    }
}
