package com.honhai.foxconn.fxccalendar.data;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

public class CloudApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AVOSCloud.initialize(this,"dUPHI2ed0PT4CpH3DqOYImAg-gzGzoHsz","CSDwOB7i3qCq5upwMa355GAo");
    }
}