package com.honhai.foxconn.fxccalendar.month;



import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.weekly.data.CalendarDay;

import java.util.Calendar;


public class MonthViewPageAdapter extends PagerAdapter {

    public final int CENTER = Integer.MAX_VALUE / 2;
    private Context context;
    private CalendarDay today;
    public MonthViewPageAdapter(Context context) {

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
        int tmp = position - CENTER;
        View v = LayoutInflater.from(context).inflate(R.layout.month_view_pager_item, container, false);
        CalendarMonthView view = v.findViewById(R.id.calendarMonthView);
        view.setCursor(today);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(Calendar.MONTH, tmp);
        view.setYearMonth(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH )+ 1);
        container.addView(v);
        return v;
    }

    public int getCenter(){
        return CENTER;
    }

    public CalendarDay getToday(){
        return today;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
