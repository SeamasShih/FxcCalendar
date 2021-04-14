package com.honhai.foxconn.fxccalendar.weekly.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.weekly.data.CalendarDay;
import com.honhai.foxconn.fxccalendar.weekly.view.CalendarWeeklyView;

import java.util.Calendar;

public class WeeklyViewPagerAdapter extends android.support.v4.view.PagerAdapter {

    private final int CENTER = Integer.MAX_VALUE / 2;
    private Context context;
    private CalendarDay today;

    public WeeklyViewPagerAdapter(Context context) {
        this.context = context;
        today = new CalendarDay();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int diff = position - CENTER;
        View parent = LayoutInflater.from(context).inflate(R.layout.weekly_view_pager_item, container, false);
        CalendarWeeklyView view = parent.findViewById(R.id.calendarWeeklyView);
        view.setCursor(today);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, diff);
        view.setWeekday(calendar);
        container.addView(parent);
        return parent;
    }

    public CalendarDay getToday(){
        return today;
    }

    public int getCenter() {
        return CENTER;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
