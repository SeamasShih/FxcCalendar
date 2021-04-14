package com.honhai.foxconn.fxccalendar.month;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.interfaces.CalendarCursor;
import com.honhai.foxconn.fxccalendar.data.interfaces.Rollback;
import com.honhai.foxconn.fxccalendar.month.MonthViewPageAdapter;
import com.honhai.foxconn.fxccalendar.weekly.data.CalendarDay;

import java.util.Calendar;

public class CalendarMonthFragment extends Fragment implements Rollback,CalendarCursor {

    private ViewPager viewPager;
    private MonthViewPageAdapter pageAdapter;
    private boolean isRollback;
    private OnMonthChangeListener onMonthChangeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_month_layout, container, false);
        viewPager = layout.findViewById(R.id.monthViewPager);
        pageAdapter = new MonthViewPageAdapter(getContext());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new CalendarMonthFragment.PageChangeListener());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        return layout;
    }

    public void rollback() {
        int current = viewPager.getCurrentItem();
        viewPager.setOffscreenPageLimit(Math.abs(Integer.MAX_VALUE / 2 - current));
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2, true);
    }

    public void setOnMonthChangeListener(OnMonthChangeListener onMonthChangeListener) {
        this.onMonthChangeListener = onMonthChangeListener;
    }
    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, viewPager.getCurrentItem() - Integer.MAX_VALUE / 2);
        return calendar;
    }

    @Override
    public Calendar getCalendarCursor() {
        CalendarDay cursor = pageAdapter.getToday();
        Calendar calendar = Calendar.getInstance();
        calendar.set(cursor.year,cursor.month-1,cursor.dayOfMonth);
        return calendar;
    }


    public class PageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {

        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if (i == 0 && isRollback && viewPager.getCurrentItem() == Integer.MAX_VALUE / 2) {
                isRollback = false;
                viewPager.setOffscreenPageLimit(1);
            }
            if ( onMonthChangeListener!= null)
            onMonthChangeListener.onMonthChange(getCalendar());
        }
    }
    public interface OnMonthChangeListener {
        void onMonthChange(Calendar calendar);
    }
}


