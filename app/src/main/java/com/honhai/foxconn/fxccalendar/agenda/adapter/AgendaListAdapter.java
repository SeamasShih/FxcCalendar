package com.honhai.foxconn.fxccalendar.agenda.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.support.v4.view.KeyEventDispatcher;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.agenda.AgendaDayEvent;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.weekly.data.Day;
import com.honhai.foxconn.fxccalendar.data.Group;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_EVENT;

public class AgendaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int GROUP_ITEM_HEADER = 1;
    public static final int CHILD_ITEM_TYPE = 2;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<AgendaDayEvent> mAgendaDayEvents;
    private List<Object> objects;
    private Day day;
    private String objectId;
    //private ItemClickListener ClickListener;

    public AgendaListAdapter(Context context, List<Object> objects) {
        this.context = context;
        this.objects = objects;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        if (viewType == GROUP_ITEM_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_view_header, parent, false);
            holder = new HeaderViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agenda_view_event_v2, parent, false);
            holder = new EventViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        int type = holder.getItemViewType();
        if (type == GROUP_ITEM_HEADER) {
            HeaderViewHolder holder1 = (HeaderViewHolder) holder;
            String title = (String) objects.get(position);
            holder1.timeday.setText(title);
            holder1.timeday.setTextColor(Color.BLACK);
            holder1.today.setVisibility(View.GONE);
            if (isToday(title)) {
                holder1.timeday.setTextColor(Data.getInstance().getThemeColor());
                holder1.today.setVisibility(View.VISIBLE);
            }
        } else {
            AgendaDayEvent.AgendaEvent agendaEvent = (AgendaDayEvent.AgendaEvent) objects.get(position);
            EventViewHolder holder1 = (EventViewHolder) holder;
            String time = agendaEvent.getEndTime();
            holder1.stattime.setText(agendaEvent.getStartTime());

           if (time==null)
                holder1.endtime.setVisibility(View.GONE);
           else{
               holder1.endtime.setVisibility(View.VISIBLE);
               holder1.endtime.setText(time);
           }
            holder1.title.setText(agendaEvent.getTitle());
           if(agendaEvent.isComplete()){
            holder1.title.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);}
            holder1.colorImageView.setColor(agendaEvent.getColor());
           if(agendaEvent.isRemind())
               holder1.alarm.setVisibility(View.VISIBLE);
           else
               holder1.alarm.setVisibility(View.GONE);
           int groupID = agendaEvent.getGroupId();
            for (Group group : Data.getInstance().getGroups()){
                if (group.getGroupId() == groupID) {
                    objectId = group.getObjectId();
                }
            }
           Data.getInstance().loadImageByGlide(holder1.user, objectId);
            //holder1.user.setImageResource(R.drawable.ic_person_black_24dp);
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, AddEventActivity.class);
                    intent.putExtra(INTENT_EVENT, ((AgendaDayEvent.AgendaEvent) objects.get(position)).getEvent());
                    context.startActivity(intent);
                }
            });
        }
    }

    private boolean isToday(String title) {
        int year, month, dayOfMonth;
        year = Integer.valueOf(title.substring(0, 4));
        month = Integer.valueOf(title.substring(5, 7));
        dayOfMonth = Integer.valueOf(title.substring(8, 10));
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == year &&
                calendar.get(Calendar.MONTH) + 1 == month &&
                calendar.get(Calendar.DAY_OF_MONTH) == dayOfMonth;
    }


    @Override
    public int getItemCount() {
        return objects == null ? 0 : objects.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (objects.get(position) instanceof String) {
            return GROUP_ITEM_HEADER;
        } else if (objects.get(position) instanceof AgendaDayEvent.AgendaEvent) {
            return CHILD_ITEM_TYPE;
        }
        return super.getItemViewType(position);
    }


}