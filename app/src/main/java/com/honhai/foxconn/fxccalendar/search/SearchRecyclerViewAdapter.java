package com.honhai.foxconn.fxccalendar.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.agenda.AgendaDayEvent;
import com.honhai.foxconn.fxccalendar.search.ColorImageView;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.weekly.data.Day;

import java.util.ArrayList;
import java.util.Calendar;

import static com.honhai.foxconn.fxccalendar.agenda.AgendaDayEvent.LABEL_ALL_DAY;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_EVENT;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter {
    public ArrayList<Object> list;
    private Context context;

    private final int HEAD = 1;
    private final int EVENT = 2;

    SearchRecyclerViewAdapter(Context context, ArrayList<Object> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view;
        if (type == HEAD) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_event_recycler_view_head_item, viewGroup, false);
            return new HeadViewHolder(view);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_event_recycler_view_event_item, viewGroup, false);
            return new EventViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == HEAD) {
            HeadViewHolder holder = (HeadViewHolder) viewHolder;
            AgendaDayEvent day = (AgendaDayEvent) list.get(position);
            holder.titleTime.setText(getDayString(day.getDay()));
            holder.eventAmount.setText(day.getEventSize() + " " + context.getResources().getString(R.string.searchEventNumberString));
        } else {
            EventViewHolder holder = (EventViewHolder) viewHolder;
            final AgendaDayEvent.AgendaEvent event = (AgendaDayEvent.AgendaEvent) list.get(position);
            holder.colorImageView.setColor(event.getColor());
            holder.eventTitle.setText(event.getTitle());
            holder.eventTime.setText(getEventTime(event));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context,AddEventActivity.class);
                    intent.putExtra(INTENT_EVENT,event.getEvent());
                    context.startActivity(intent);
                }
            });
        }
    }

    private String getEventTime(AgendaDayEvent.AgendaEvent agendaEvent) {
        Event event = agendaEvent.getEvent();
        if (agendaEvent.getEvent().isAllDay())
            return context.getResources().getString(R.string.calendarWeeklyAllDayText);
        else if (agendaEvent.getTime().startsWith(LABEL_ALL_DAY)){
            return context.getResources().getString(R.string.calendarWeeklyAllDayText);
        }
        return event.getStartYear() + "-" +
                doubleDigit(event.getStartMonth()) + "-" +
                doubleDigit(event.getStartDayOfMonth()) + " " +
                doubleDigit(event.getStartHour()) + ":" +
                doubleDigit(event.getStartMinute()) + " ~ " +
                event.getEndYear() + "-" +
                doubleDigit(event.getEndMonth()) + "-" +
                doubleDigit(event.getEndDayOfMonth()) + " " +
                doubleDigit(event.getEndHour()) + ":" +
                doubleDigit(event.getEndMinute());
    }

    private String doubleDigit(int i) {
        return i < 10 ? "0" + i : "" + i;
    }

    private String getDayString(Day day) {
        return day.year + "." + day.month + "." + day.dayOfMonth + " (" + day.getWeekString(context, Calendar.LONG) + ")";
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object object = list.get(position);
        if (object instanceof AgendaDayEvent)
            return HEAD;
        else if (object instanceof AgendaDayEvent.AgendaEvent)
            return EVENT;
        return super.getItemViewType(position);
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        TextView titleTime;
        TextView eventAmount;

        HeadViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTime = itemView.findViewById(R.id.titleTime);
            eventAmount = itemView.findViewById(R.id.eventAmount);
        }
    }

    private class EventViewHolder extends RecyclerView.ViewHolder {
        ColorImageView colorImageView;
        TextView eventTitle;
        TextView eventTime;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            colorImageView = itemView.findViewById(R.id.imgEventColor);
            eventTitle = itemView.findViewById(R.id.tvEventTitle);
            eventTime = itemView.findViewById(R.id.tvEventTime);
        }
    }
}