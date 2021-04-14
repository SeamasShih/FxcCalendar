package com.honhai.foxconn.fxccalendar.addevent;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.honhai.foxconn.fxccalendar.R;

public class DeleteMessagePopupWindow extends PopupWindow {
    private AppCompatActivity activity;

    public DeleteMessagePopupWindow(AppCompatActivity context, View layout, int w, int h) {
        super(layout, w, h);
        this.activity = context;
        setOutsideTouchable(true);
        setFocusable(true);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = .5f;
        activity.getWindow().setAttributes(lp);
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });
    }
}
