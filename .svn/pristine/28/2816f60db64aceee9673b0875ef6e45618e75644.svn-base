package com.honhai.foxconn.fxccalendar.notification;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.util.Calendar;

public class NotificationActivity extends AppCompatActivity {

    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    public void setNotification(long date, String groupName, String message, int num, int color) {
        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent it = new Intent(this, AlarmReceiver.class);
        it.putExtra("groupName", groupName);
        it.putExtra("message", message);
        it.putExtra("num", num);
        it.putExtra("color", color);
        alarmIntent = PendingIntent.getBroadcast(this, num, it, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);

    }
}
