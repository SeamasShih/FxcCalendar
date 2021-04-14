package com.honhai.foxconn.fxccalendar.weekly.data;

import com.honhai.foxconn.fxccalendar.month.MonthDay;

import java.util.Calendar;

public class CalendarDay {
    public int year;
    public int month;
    public int dayOfMonth;

    public CalendarDay(){
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void set(Day day){
        year = day.year;
        month = day.month;
        dayOfMonth = day.dayOfMonth;
    }
    public void set(MonthDay day){
        year = day.year;
        month = day.month;
        dayOfMonth = day.dayOfMonth;
    }
}
