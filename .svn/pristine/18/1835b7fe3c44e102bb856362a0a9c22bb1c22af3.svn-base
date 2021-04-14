package com.honhai.foxconn.fxccalendar.main;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.CALENDAR_MONTHLY;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.CALENDAR_SUMMARY;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.CALENDAR_WEEKLY;

public class CalendarPopupWindow extends PopupWindow {
    private Drawable monthlySelect;
    private Drawable monthlyFree;
    private Drawable weeklySelect;
    private Drawable weeklyFree;
    private Drawable summarySelect;
    private Drawable summaryFree;

    private PopupCalendarImageView summary, weekly, monthly;
    private ImageView summaryConfirm, weeklyConfirm, monthlyConfirm;
    private TextView summaryText, weeklyText, monthlyText, title;

    private int gray;

    private MainActivity activity;

    CalendarPopupWindow(MainActivity activity, View popupLayout, int width, int height) {
        super(popupLayout, width, height);
        this.activity = activity;

        monthlySelect = popupLayout.getResources().getDrawable(R.drawable.drawable_list_monthly_selected);
        monthlyFree = popupLayout.getResources().getDrawable(R.drawable.drawable_list_monthly_free);
        weeklySelect = popupLayout.getResources().getDrawable(R.drawable.drawable_list_weekly_selected);
        weeklyFree = popupLayout.getResources().getDrawable(R.drawable.drawable_list_weekly_free);
        summarySelect = popupLayout.getResources().getDrawable(R.drawable.drawable_list_summary_selected);
        summaryFree = popupLayout.getResources().getDrawable(R.drawable.drawable_list_summary_free);

        summary = popupLayout.findViewById(R.id.imgPupSwitchCalendarSummary);
        weekly = popupLayout.findViewById(R.id.imgPupSwitchCalendarWeekly);
        monthly = popupLayout.findViewById(R.id.imgPupSwitchCalendarMonthly);
        summaryConfirm = popupLayout.findViewById(R.id.imgPupSwitchCalendarSummaryConfirm);
        weeklyConfirm = popupLayout.findViewById(R.id.imgPupSwitchCalendarWeeklyConfirm);
        monthlyConfirm = popupLayout.findViewById(R.id.imgPupSwitchCalendarMonthlyConfirm);
        summaryText = popupLayout.findViewById(R.id.tvPupSwitchCalendarSummary);
        weeklyText = popupLayout.findViewById(R.id.tvPupSwitchCalendarWeekly);
        monthlyText = popupLayout.findViewById(R.id.tvPupSwitchCalendarMonthly);

        Drawable drawable = monthlyConfirm.getBackground();
        drawable.setAlpha(255);
        drawable.setTintList(ColorStateList.valueOf(Data.getInstance().getThemeColor()));
        monthlyConfirm.setBackground(drawable);
        weeklyConfirm.setBackground(drawable);
        summaryConfirm.setBackground(drawable);

        monthly.setScaleX(.8f);
        monthly.setScaleY(.8f);
        weekly.setScaleX(.8f);
        weekly.setScaleY(.8f);
        summary.setScaleX(.8f);
        summary.setScaleY(.8f);

        gray = popupLayout.getResources().getColor(R.color.colorPopupSwitchCalendarText);

        title = popupLayout.findViewById(R.id.tvPupSwitchCalendarTitle);

        setOnClickListener();
    }

    private void setOnClickListener() {
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        monthly.setOnClickListener(monthlyClickListener);
        monthlyConfirm.setOnClickListener(monthlyClickListener);
        monthlyText.setOnClickListener(monthlyClickListener);

        weekly.setOnClickListener(weeklyClickListener);
        weeklyConfirm.setOnClickListener(weeklyClickListener);
        weeklyText.setOnClickListener(weeklyClickListener);

        summary.setOnClickListener(summarylyClickListener);
        summaryConfirm.setOnClickListener(summarylyClickListener);
        summaryText.setOnClickListener(summarylyClickListener);
    }


    void setPopupCalendarListType(int calendarType) {
        switch (calendarType) {
            case CALENDAR_MONTHLY:
                setMonthlyHighLight();
                setWeeklyGrayOff();
                setSummaryGrayOff();
                break;
            case CALENDAR_WEEKLY:
                setMonthlyGrayOff();
                setWeeklyHighLight();
                setSummaryGrayOff();
                break;
            case CALENDAR_SUMMARY:
                setMonthlyGrayOff();
                setWeeklyGrayOff();
                setSummaryHighLight();
                break;
        }
    }

    private void setMonthlyHighLight() {
        monthly.setBackground(monthlySelect);
        monthly.setBackgroundTintList(ColorStateList.valueOf(Data.getInstance().getThemeColor()));
        monthlyText.setTextColor(Data.getInstance().getThemeColor());
        monthlyConfirm.setVisibility(View.VISIBLE);
    }

    private void setMonthlyGrayOff() {
        monthly.setBackground(monthlyFree);
        monthly.setBackgroundTintList(ColorStateList.valueOf(gray));
        monthlyText.setTextColor(gray);
        monthlyConfirm.setVisibility(View.INVISIBLE);
    }

    private void setWeeklyHighLight() {
        weekly.setBackground(monthlySelect);
        weekly.setBackgroundTintList(ColorStateList.valueOf(Data.getInstance().getThemeColor()));
        weeklyText.setTextColor(Data.getInstance().getThemeColor());
        weeklyConfirm.setVisibility(View.VISIBLE);
    }

    private void setWeeklyGrayOff() {
        weekly.setBackground(monthlyFree);
        weekly.setBackgroundTintList(ColorStateList.valueOf(gray));
        weeklyText.setTextColor(gray);
        weeklyConfirm.setVisibility(View.INVISIBLE);
    }

    private void setSummaryHighLight() {
        summary.setBackground(monthlySelect);
        summary.setBackgroundTintList(ColorStateList.valueOf(Data.getInstance().getThemeColor()));
        summaryText.setTextColor(Data.getInstance().getThemeColor());
        summaryConfirm.setVisibility(View.VISIBLE);
    }

    private void setSummaryGrayOff() {
        summary.setBackground(monthlyFree);
        summary.setBackgroundTintList(ColorStateList.valueOf(gray));
        summaryText.setTextColor(gray);
        summaryConfirm.setVisibility(View.INVISIBLE);
    }

    @SuppressLint("ClickableViewAccessibility")
    void setPopupCalendarListListener(int calendarType) {
        switch (calendarType) {
            case CALENDAR_MONTHLY:
                setWeeklyListener();
                setSummaryListener();
                break;
            case CALENDAR_WEEKLY:
                setMonthlyListener();
                setSummaryListener();
                break;
            case CALENDAR_SUMMARY:
                setMonthlyListener();
                setWeeklyListener();
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setMonthlyListener() {
        monthly.setOnTouchListener(monthlyTouchListener);
        monthlyText.setOnTouchListener(monthlyTouchListener);
        monthlyConfirm.setOnTouchListener(monthlyTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setWeeklyListener() {
        weekly.setOnTouchListener(weeklyTouchListener);
        weeklyText.setOnTouchListener(weeklyTouchListener);
        weeklyConfirm.setOnTouchListener(weeklyTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setSummaryListener() {
        summary.setOnTouchListener(summaryTouchListener);
        summaryText.setOnTouchListener(summaryTouchListener);
        summaryConfirm.setOnTouchListener(summaryTouchListener);
    }


    private View.OnTouchListener monthlyTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    setMonthlyHighLight();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setMonthlyGrayOff();
                    break;
            }
            return false;
        }
    };

    private View.OnTouchListener weeklyTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    setWeeklyHighLight();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setWeeklyGrayOff();
                    break;
            }
            return false;
        }
    };

    private View.OnTouchListener summaryTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    setSummaryHighLight();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setSummaryGrayOff();
                    break;
            }
            return false;
        }
    };

    private View.OnClickListener monthlyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setCalendarType(CALENDAR_MONTHLY);
            dismiss();
        }
    };

    private View.OnClickListener weeklyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setCalendarType(CALENDAR_WEEKLY);
            dismiss();
        }
    };

    private View.OnClickListener summarylyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            activity.setCalendarType(CALENDAR_SUMMARY);
            dismiss();
        }
    };
}
