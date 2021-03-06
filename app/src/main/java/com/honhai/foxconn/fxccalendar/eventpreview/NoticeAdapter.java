package com.honhai.foxconn.fxccalendar.eventpreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;
import com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView.GroupItem;
import com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView.GroupItemClickListener;
import com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView.GroupItemDecoration;
import com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView.GroupTestAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_DAY_OF_MONTH;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_MONTH;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_YEAR;

public class NoticeAdapter extends PagerAdapter implements RefreshData{

    private final int CENTER = Integer.MAX_VALUE/2;
    private ArrayList<Event> times ;
    private Context context;
    int isallday = 0;
    int d1 = 0;
    int d2 = 0;
    int d3 = 0;
    int d4 = 0;
    private Calendar calendar;
    private Calendar now = Calendar.getInstance();
    private int n = -1;
    private int rightOrLeft;
    boolean it;
    boolean it1;
    GroupTestAdapter groupTestAdapter;
    ArrayList<Event> eventsOfDay;
    int week = R.array.weeks;


    public NoticeAdapter(Context context, Calendar calendar) {
        this.context = context;
        this.calendar = calendar;
        Data.getInstance().addRefreshView(this);
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

        //?????????0???-1???1
        now.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        now.add(Calendar.DAY_OF_YEAR, position - CENTER);
        if(position - CENTER == 1 && n == -1 ){
            rightOrLeft = -1;
        }else if(position - CENTER == -2 && n ==1){
            rightOrLeft = 1;
        }else if(position - CENTER == 2 && n == 1){
            rightOrLeft = -1;
        }else if(n > (position - CENTER)){
            rightOrLeft = 1;
        }else if(n < (position - CENTER)){
            rightOrLeft = -1;
        }

        eventsOfDay = getDayEvent(times,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
        View layout;
        if (eventsOfDay == null || eventsOfDay.size() == 0) {
            layout = setNoEventViews();
        } else {
            eventsOfDay = sort(eventsOfDay);
            layout = setEventViews(eventsOfDay);
        }
        //???n??????????????????
        n = position - CENTER;
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void getEventOfDay(ArrayList<Event> events) {
        this.times = events;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    private String currentTime(Calendar calendar) {

        String time = calendar.get(Calendar.YEAR) +
                "." + (calendar.get(Calendar.MONTH)+1) +
                "." + calendar.get(Calendar.DAY_OF_MONTH) + " "
                +context.getResources().getStringArray(week)[calendar.get(Calendar.DAY_OF_WEEK)-1];


        return time;
    }

    private View setEventViews(ArrayList<Event> events) {
        View layout = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout_recycview, null);
        TextView tvTitle = layout.findViewById(R.id.rv_adapter_title);
        ImageView littleAdd = layout.findViewById(R.id.little_add);
        tvTitle.setText(currentTime(now));
        it1 = true;
        it = true;
        final int y = now.get(Calendar.YEAR);
        final int m = now.get(Calendar.MONTH);
        final int d = now.get(Calendar.DAY_OF_MONTH);
        littleAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(it1){
                    now.add(Calendar.DAY_OF_YEAR,rightOrLeft);
                    it1 = false;
                }
                Intent intent = new Intent();
                intent.setClass(context, AddEventActivity.class);
                intent.putExtra(INTENT_YEAR,y);
                intent.putExtra(INTENT_MONTH, m);
                intent.putExtra(INTENT_DAY_OF_MONTH, d);
                context.startActivity(intent);
            }
        });
        Drawable drawable2 = context.getDrawable(R.drawable.ic_add_black_24dp);
        littleAdd.setImageDrawable(drawable2);
        drawable2.setTint(Data.getInstance().getThemeColor());

        tvTitle.setTextColor(Data.getInstance().getThemeColor());
        RecyclerView recyclerView = layout.findViewById(R.id.recyclerView_2);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initData(recyclerView, events);
        return layout;
    }

    private View setNoEventViews() {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_view_layout2, null);
        TextView tvTitle = view.findViewById(R.id.tv_adapter_title);
        ImageView littleAdd_1 = view.findViewById(R.id.addevent_1);

        final int y = now.get(Calendar.YEAR);
        final int m = now.get(Calendar.MONTH);
        final int d = now.get(Calendar.DAY_OF_MONTH);
        littleAdd_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(it){
                    now.add(Calendar.DAY_OF_YEAR,rightOrLeft);
                    it = false;
                }
                Intent intent = new Intent();
                intent.setClass(context, AddEventActivity.class);
                intent.putExtra(INTENT_YEAR,y);
                intent.putExtra(INTENT_MONTH, m);
                intent.putExtra(INTENT_DAY_OF_MONTH,d);
                context.startActivity(intent);
            }
        });
        it1 = true;
        it = true;
        Button bigAdd = view.findViewById(R.id.addEvent);
        bigAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(it){
                    now.add(Calendar.DAY_OF_YEAR,rightOrLeft);
                    it = false;
                    }

                Intent intent = new Intent();
                intent.setClass(context, AddEventActivity.class);
                intent.putExtra(INTENT_YEAR,y);
                intent.putExtra(INTENT_MONTH, m);
                intent.putExtra(INTENT_DAY_OF_MONTH, d);
                context.startActivity(intent);
            }
        });

        tvTitle.setText(currentTime(now));
        tvTitle.setTextColor(Data.getInstance().getThemeColor());



        Button button = view.findViewById(R.id.addEvent);
        Drawable addevent = context.getDrawable(R.drawable.add_event);
        addevent.setTint(Data.getInstance().getThemeColor());
        addevent.setAlpha(255);
        button.setBackground(addevent);

        Drawable drawable2 = context.getDrawable(R.drawable.ic_add_black_24dp);
        ImageView imageView = view.findViewById(R.id.addevent_1);
        imageView.setImageDrawable(drawable2);
        drawable2.setTint(Data.getInstance().getThemeColor());

        return view;
    }

    private void initData(RecyclerView recyclerView, final ArrayList<Event> time) {

//        Log.e("????????????????????????",":"+mynow.get(Calendar.YEAR) + mynow.get(Calendar.MONTH)+mynow.get(Calendar.DAY_OF_MONTH));
        groupTestAdapter = new GroupTestAdapter(context, time, now);
        recyclerView.setAdapter(groupTestAdapter);

        //????????????GroupItemDecoration
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_group, null);
        final int myy = now.get(Calendar.YEAR);
        final int mym = now.get(Calendar.MONTH);
        final int myd = now.get(Calendar.DAY_OF_MONTH);  GroupItemDecoration groupItemDecoration = new GroupItemDecoration(
                context,
                view,
                new GroupItemDecoration.DecorationCallback() {
                    @Override
                    public void setGroup(List<GroupItem> groupList) {
                        //????????????
                        GroupItem groupItem;
                        for (int i = 0; i < time.size(); i++) {
                            if (time.get(i).isAllDay() || isContinuous(time.get(i),myd,mym,myy)) {

                                groupItem = new GroupItem(0);
                                if (isallday == 0) {
                                    groupItem.setData("name", context.getString(R.string.Allday));
                                    groupItem.setData("time", "");
                                    groupList.add(groupItem);
                                    isallday = 1;
                                }
                            } else if (time.get(i).getStartHour() < 6) {
                                if (d1 == 0) {
                                    groupItem = new GroupItem(i);
                                    groupItem.setData("name", context.getString(R.string.Night));
                                    groupItem.setData("time", "00:00-05:59");
                                    groupList.add(groupItem);
                                    d1 = 1;
                                }
                            } else if (time.get(i).getStartHour() > 5 && time.get(i).getStartHour() < 12) {
                                if (d2 == 0) {
                                    groupItem = new GroupItem(i);
                                    groupItem.setData("name", context.getString(R.string.Morning));
                                    groupItem.setData("time", "06:00-11:59");
                                    groupList.add(groupItem);
                                    d2 = 1;
                                }
                            } else if (time.get(i).getStartHour() > 11 && time.get(i).getStartHour() < 18) {
                                if (d3 == 0) {
                                    groupItem = new GroupItem(i);
                                    groupItem.setData("name", context.getString(R.string.Afternoon));
                                    groupItem.setData("time", "12:00-17:59");
                                    groupList.add(groupItem);
                                    d3 = 1;
                                }
                            } else if (time.get(i).getStartHour() > 17 && time.get(i).getStartHour() < 24) {
                                if (d4 == 0) {
                                    groupItem = new GroupItem(i);
                                    groupItem.setData("name", context.getString(R.string.Evening));
                                    groupItem.setData("time", "18:00-23:59");
                                    groupList.add(groupItem);
                                    d4 = 1;
                                }
                            }
                        }

                        isallday = 0;
                        d1 = 0;
                        d2 = 0;
                        d3 = 0;
                        d4 = 0;
                    }


                    @Override
                    public void buildGroupView(View groupView, GroupItem groupItem) {
                        //??????GroupView?????????view.findViewById??????????????????
                        TextView textName =  groupView.findViewById(R.id.text_name);
                        TextView textName1 = groupView.findViewById(R.id.textView2);
                        textName.setText(groupItem.getData("name").toString());
                        textName1.setText(groupItem.getData("time").toString());

                    }
                });
        recyclerView.addItemDecoration(groupItemDecoration);
        recyclerView.addOnItemTouchListener(new GroupItemClickListener(groupItemDecoration, new GroupItemClickListener.OnGroupItemClickListener() {
            @Override
            public void onGroupItemClick(GroupItem groupItem) {
//                Toast.makeText(context, "?????????Group:" + groupItem.getData("name"), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGroupItemLongClick(GroupItem groupItem) {
//                Toast.makeText(context, "?????????Group:" + groupItem.getData("name"), Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public ArrayList<Event> getDayEvent(ArrayList<Event> events, int year, int month , int day) {

        ArrayList<Event> myevents;
        events = getHaveTimeEvents(events);
        GetTime getTime = new GetTime(events);
        myevents = getTime.getSomedayTime(year,month,day);
        return myevents;
    }

    private ArrayList<Event> getHaveTimeEvents(ArrayList<Event> times) {
        ArrayList<Event> events = new ArrayList<>();

        for(int i = 0; i<times.size(); i++){
            if(!times.get(i).isNoTime()){
                events.add(times.get(i));
            }
        }

        return events;
    }

    private ArrayList<Event> sort(ArrayList<Event> time){
        Collections.sort(time, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                if(isContinuous(o1)){
                    return -1;
                }else if(isContinuous(o2)){
                    return 1;
                }
                int f = o1.getStartHour();
                int b = o2.getStartHour();
                if(o1.isAllDay()){return -1;}
                else if(o2.isAllDay()){return 1;}
                else if (f >= b) {
                    return 1;
                }else {
                    return -1;
                }
            }
        });
        return time;
    }

    //????????????????????????,?????????true(???????????????)
    private boolean isContinuous(Event o1) {
//        Log.e("","???????????????????????????"+"day"+o1.getStartDayOfMonth()+ o1.getEndDayOfMonth() +
//                "month"+o1.getStartMonth() +" "+o1.getEndMonth() +"year"+
//                o1.getStartYear() +o1.getEndYear());
//
//        Log.e("?????????now??????",""+""+now.get(Calendar.DAY_OF_MONTH) +now.get(Calendar.MONTH)
//                +now.get(Calendar.YEAR));

        if (
                (
                        !(o1.getStartDayOfMonth() == o1.getEndDayOfMonth() &&
                o1.getStartMonth() == o1.getEndMonth() &&
                o1.getStartYear() == o1.getEndYear()) && (!(
                                o1.getStartDayOfMonth() == now.get(Calendar.DAY_OF_MONTH) &&
                                        o1.getStartMonth() == now.get(Calendar.MONTH) &&
                                        o1.getStartYear() == now.get(Calendar.YEAR)
                                )
                        )
                )
                )

        {
           return true;
        }
        return false;
    }

    private boolean isContinuous(Event o1,int day,int month,int year) {

        if (

                (!(o1.getStartDayOfMonth() == o1.getEndDayOfMonth() &&
                                o1.getStartMonth() == o1.getEndMonth() &&
                                o1.getStartYear() == o1.getEndYear())) && (!(
                                o1.getStartDayOfMonth() == day &&
                                        o1.getStartMonth() == month &&
                                        o1.getStartYear() == year
                        )
                        )

                )

        {
            return true;
        }
        return false;
    }

    @Override
    public void refresh() {
        notifyDataSetChanged();
    }
}

