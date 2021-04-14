package com.honhai.foxconn.fxccalendar.weekly.fragment;

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
import com.honhai.foxconn.fxccalendar.weekly.adapter.WeeklyViewPagerAdapter;
import com.honhai.foxconn.fxccalendar.weekly.data.CalendarDay;

import java.util.Calendar;

public class CalendarWeeklyFragment extends Fragment implements Rollback,CalendarCursor {

    private ViewPager viewPager;
    private WeeklyViewPagerAdapter pagerAdapter;
    private boolean isRollback;
    private OnWeekChangeListener onWeekChangeListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_weekly_layout, container, false);
        viewPager = layout.findViewById(R.id.weeklyViewPager);
        pagerAdapter = new WeeklyViewPagerAdapter(getContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new PageChangeListener());
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        return layout;
    }

    public void rollback() {
        int current = viewPager.getCurrentItem();
        viewPager.setOffscreenPageLimit(Math.abs(Integer.MAX_VALUE / 2 - current));
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2, true);
    }

    public void setOnWeekChangeListener(OnWeekChangeListener onWeekChangeListener) {
        this.onWeekChangeListener = onWeekChangeListener;
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, viewPager.getCurrentItem() - Integer.MAX_VALUE / 2);
        return calendar;
    }

    @Override
    public Calendar getCalendarCursor() {
        CalendarDay cursor = pagerAdapter.getToday();
        Calendar calendar = Calendar.getInstance();
        calendar.set(cursor.year,cursor.month,cursor.dayOfMonth);
        return calendar;
    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener {

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
            if (onWeekChangeListener != null)
                onWeekChangeListener.onWeekChange(getCalendar());
        }
    }

    public interface OnWeekChangeListener {
        void onWeekChange(Calendar calendar);
    }
}
