package com.honhai.foxconn.fxccalendar.agenda;

import android.content.Context;
import android.util.AttributeSet;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.weekly.data.Day;
import com.honhai.foxconn.fxccalendar.weekly.view.CalendarWeeklyView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class AgendaManager {

    private static AgendaManager mInstance = new AgendaManager();//单例模型

    private Context mContext;
    private Locale mLocale;
    private Calendar mToday = Calendar.getInstance();
    private AgendaDayEvent mAgendaDayEvent;

    //构造方法
    private AgendaManager() {
    }

    //单子例模型
    public void setContext(Context context) {
        mContext = context;
    }

    public static AgendaManager getInstance() {
        return mInstance;
    }

    public ArrayList<AgendaDayEvent> mAgendaDayEvents;

    public ArrayList<AgendaDayEvent> getmAgendaDayEvents() {
        return mAgendaDayEvents;
    }


    public void sortOutbyDay(ArrayList<Event> events) {
        events = Data.getInstance().getEvents();
//        private Context mContext;
        //初始化mday
        Day mday = null;
        if (events != null && events.size() > 0) {
            for (Event event: events) {
                //先判断这个event在不在这个日期下
                //属于这个日期的，把该event添加到list集合中
                int year1 = event.getStartYear();
                int month1 = event.getStartMonth();
                int day1 = event.getStartDayOfMonth();
                if (mAgendaDayEvent == null) {
                    mday = new Day(event);
                    mAgendaDayEvent = new AgendaDayEvent(mContext, mday);
                    mAgendaDayEvent.addEvent(event);
                } else {
                    if (mday.equal(year1, month1, day1)) {
                        mAgendaDayEvent.addEvent(event);
                    } else {
                        mday = new Day(event);
                        mAgendaDayEvent = new AgendaDayEvent(mContext, mday);
                        mAgendaDayEvent.addEvent(event);
                    }
                }
                mAgendaDayEvents.add(mAgendaDayEvent);

            }

        }

        //return mAgendaDayEvents;
    }


}