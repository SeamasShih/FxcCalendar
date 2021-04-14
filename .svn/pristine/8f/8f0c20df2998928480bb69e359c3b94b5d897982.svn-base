package com.honhai.foxconn.fxccalendar.weekly.data;

import android.content.Context;

import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.main.MainActivity;
import com.honhai.foxconn.fxccalendar.weekly.view.CalendarWeeklyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Day {
    public int year;
    public int month;
    public int weekOfYear;
    public int dayOfMonth;
    public int dayOfWeek;
    public boolean isToday;
    public ArrayList<CalendarWeeklyView.DayEvent> events = new ArrayList<>();

    public Day() {
        year = -1;
        month = -1;
        dayOfMonth = -1;
        dayOfWeek = -1;
        isToday = false;
    }

    public Day(Calendar calendar) {
        set(calendar);
    }

    public Day(Event event) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth());
        set(calendar);
    }

    public String getWeekString(Context context, int style) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, style, context.getResources().getConfiguration().locale);
    }

    public void set(Calendar calendar) {
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Calendar now = Calendar.getInstance();
        isToday = (year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH) && dayOfMonth == now.get(Calendar.DAY_OF_MONTH));
    }

    public void set(Day day) {
        year = day.year;
        month = day.month;
        dayOfMonth = day.dayOfMonth;
        dayOfWeek = day.dayOfWeek;
        isToday = day.isToday;
    }

    public boolean equal(CalendarDay cD) {
        return (this.year == cD.year && this.month == cD.month && this.dayOfMonth == cD.dayOfMonth);
    }

    public boolean equal(Calendar calendar) {
        return (this.year == calendar.get(Calendar.YEAR) &&
                this.month == calendar.get(Calendar.MONTH) &&
                this.dayOfMonth == calendar.get(Calendar.DAY_OF_MONTH));
    }

    public boolean equal(int year, int month, int dayOfMonth) {
        return (this.year == year && this.month == month && this.dayOfMonth == dayOfMonth);
    }
}
