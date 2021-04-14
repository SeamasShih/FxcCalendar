package com.honhai.foxconn.fxccalendar.eventpreview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;

import java.util.ArrayList;
import java.util.Calendar;


public class MyDialog2 extends Dialog  {

    private ViewPager mViewPager;
    private int[] monthdata;
    private Calendar calendar = Calendar.getInstance();
    private ArrayList<Event> times;//获取所有事件
    private Context context;
    NoticeAdapter noticeAdapter;

    public MyDialog2(Context context, int[] data) {
        //定义Dialog的主题风格
        super(context, R.style.selfDefDialog2);
        this.context = context;
        this.monthdata = data;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setWindowAnimations(R.style.popupAnimation);
        times = Data.getInstance().getEvents();//获取所有事件

        setContentView(R.layout.dialog_layout);
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(createAdapter());
        mViewPager.setPageMargin(40);
        mViewPager.setPadding(60, 0, 60, 0);
        mViewPager.setClipToPadding(false);
        mViewPager.setOffscreenPageLimit(1);
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2);

    }

    private PagerAdapter createAdapter() {
        calendar.set(monthdata[0], monthdata[1]-1, monthdata[2]);
        noticeAdapter = new NoticeAdapter(getContext(), calendar);
        noticeAdapter.getEventOfDay(times);
        return noticeAdapter;
    }
}
