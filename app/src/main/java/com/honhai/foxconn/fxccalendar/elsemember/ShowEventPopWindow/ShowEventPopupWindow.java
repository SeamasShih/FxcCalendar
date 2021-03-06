package com.honhai.foxconn.fxccalendar.elsemember.ShowEventPopWindow;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity;

import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_ALL;
import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_COMPLETED;
import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_UNCOMPLETED;

public class ShowEventPopupWindow extends PopupWindow {
    private TextView tv_allEvents, tv_completedEvents, tv_uncompletedEvents;
    private int gray;
    private final TextView title;
    private ImageView allEventsConfirm, completedEventsConfirm, uncompletedEventsConfirm;
    private SettingsActivity activity;


    public ShowEventPopupWindow(SettingsActivity activity, View popupLayout, int width, int height) {
        super(popupLayout, width, height);
        this.activity = activity;
        allEventsConfirm = popupLayout.findViewById(R.id.imgPupSwitchCompletedEventBothConfirm);
        completedEventsConfirm = popupLayout.findViewById(R.id.imgPupSwitchCompletedEventConfirm);
        uncompletedEventsConfirm = popupLayout.findViewById(R.id.tvPupSwitchUncompletedEventConfirm);
        tv_allEvents = popupLayout.findViewById(R.id.tvPupSwitchCompletedEventBoth);
        tv_completedEvents = popupLayout.findViewById(R.id.tvPupSwitchCompletedEvent);
        tv_uncompletedEvents = popupLayout.findViewById(R.id.tvPupSwitchUncompletedEvent);

        Drawable drawable = allEventsConfirm.getBackground();
        drawable.setAlpha(255);
        drawable.setTintList(ColorStateList.valueOf(Data.getInstance().getThemeColor()));
        allEventsConfirm.setBackground(drawable);
        completedEventsConfirm.setBackground(drawable);
        uncompletedEventsConfirm.setBackground(drawable);

        gray = popupLayout.getResources().getColor(R.color.colorPopupSwitchCalendarText);
        title = popupLayout.findViewById(R.id.tvPupSwitchCompletedEventsTitle);

        setOnClickListener();


    }

    private void setOnClickListener() {
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        allEventsConfirm.setOnClickListener(monthlyClickListener);
        tv_allEvents.setOnClickListener(monthlyClickListener);


        completedEventsConfirm.setOnClickListener(completedClickListener);
        tv_completedEvents.setOnClickListener(completedClickListener);


        uncompletedEventsConfirm.setOnClickListener(uncompletedClickListener);
        tv_uncompletedEvents.setOnClickListener(uncompletedClickListener);
    }

    private View.OnClickListener monthlyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setEventsType(EVENTS_ALL);
            dismiss();
        }
    };

    private View.OnClickListener completedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setEventsType(EVENTS_COMPLETED);
            dismiss();
        }
    };

    private View.OnClickListener uncompletedClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setEventsType(EVENTS_UNCOMPLETED);
            dismiss();
        }
    };

    public void setPopupCompletedEventsListType(int eventType) {
        switch (eventType) {

            case EVENTS_ALL:
                setAllEventsHighLight();
                setCompletedGrayOff();
                setUncompletedGrayOff();
                break;
            case EVENTS_COMPLETED:
                setAllEventsGrayOff();
                setCompletedHighLight();
                setUncompletedGrayOff();
                break;
            case EVENTS_UNCOMPLETED:
                setAllEventsGrayOff();
                setCompletedGrayOff();
                setUncompletedHighLight();
                break;


        }


    }

    private void setUncompletedHighLight() {

        tv_uncompletedEvents.setTextColor(Data.getInstance().getThemeColor());
        uncompletedEventsConfirm.setVisibility(View.VISIBLE);
    }

    private void setUncompletedGrayOff() {
        tv_uncompletedEvents.setTextColor(gray);
        uncompletedEventsConfirm.setVisibility(View.INVISIBLE);
    }

    private void setCompletedHighLight() {
        tv_completedEvents.setTextColor(Data.getInstance().getThemeColor());
        completedEventsConfirm.setVisibility(View.VISIBLE);
    }

    private void setCompletedGrayOff() {
        tv_completedEvents.setTextColor(gray);
        completedEventsConfirm.setVisibility(View.INVISIBLE);
    }

    private void setAllEventsGrayOff() {
        tv_allEvents.setTextColor(gray);
        allEventsConfirm.setVisibility(View.INVISIBLE);

    }

    private void setAllEventsHighLight() {
        tv_allEvents.setTextColor(Data.getInstance().getThemeColor());
        allEventsConfirm.setVisibility(View.VISIBLE);
    }

}
