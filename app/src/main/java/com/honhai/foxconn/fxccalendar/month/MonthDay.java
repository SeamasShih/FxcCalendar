package com.honhai.foxconn.fxccalendar.month;

import com.honhai.foxconn.fxccalendar.weekly.view.CalendarWeeklyView;

import java.util.ArrayList;
import java.util.Calendar;

public class MonthDay {
    public int year;
    public int month;
    public int dayOfMonth;
    public int maxItems;
    public Boolean isHoliday;
    public boolean isLongEvent[];
    public int eventNum;
    public int count;
    public int daysCount;
    public boolean isSigner;
    public ArrayList<CalendarMonthView.MonthDayEvent> events = new ArrayList<>();
    MonthDay(){
        maxItems = -1;
        year = -1;
        month = -1;
        dayOfMonth = -1;
        isHoliday = false;
        isLongEvent = new boolean[10];
        eventNum = 0;
        count = 0;
        daysCount = 0;
        isSigner = false;
    }

    public void set(int year,int month,int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.dayOfMonth = dayOfMonth;
    }

    public boolean equal(int year, int month, int dayOfMonth) {
        return (this.year == year && this.month == month && this.dayOfMonth == dayOfMonth);
    }

}
