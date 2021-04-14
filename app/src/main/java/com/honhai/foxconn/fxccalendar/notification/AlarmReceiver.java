package com.honhai.foxconn.fxccalendar.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.welcome.WelcomeActivity;

public class AlarmReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        String groupName = intent.getStringExtra("groupName");
        String message = intent.getStringExtra("message");
        int num = intent.getIntExtra("num", 0);
        int color = intent.getIntExtra("color", 0);
        Intent it = new Intent(context, WelcomeActivity.class);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, it, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder builder;
            builder =
                    new NotificationCompat.Builder(context, "CHANNEL")
                            .setSmallIcon(R.mipmap.app_logo_notification)
                            .setContentTitle(groupName)
                            .setContentText(message)
                            .setChannelId("CHANNEL")
                            .setGroupSummary(true).setColor(color)
                            .setWhen(System.currentTimeMillis())
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

            CharSequence name = "FxcCalendar";
            String description = "FxcCalendarNotification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL", name, importance);
            channel.setDescription(description);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
            notificationManager.notify(num, builder.build());


        } else {
            Notification.Builder builder;
            builder =
                    new Notification.Builder(context)
                            .setSmallIcon(R.mipmap.app_logo_notification)
                            .setContentTitle(groupName)
                            .setContentText(message)
                            .setWhen(System.currentTimeMillis())
                            .setColor(color)
                            .setGroupSummary(true)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
            assert notificationManager != null;
            notificationManager.notify(num, builder.build());
        }

    }
}
