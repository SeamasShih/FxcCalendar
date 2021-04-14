package com.honhai.foxconn.fxccalendar.agenda.adapter;


import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.colormanager.ColorImageView;

public class EventViewHolder extends RecyclerView.ViewHolder {
    TextView stattime;
    TextView endtime;
    TextView title;
    //ImageView color;
    ColorImageView colorImageView;
    ImageView alarm;
    ImageView user;

    public EventViewHolder (View itemView) {
        super(itemView);
        stattime = (TextView) itemView.findViewById(R.id.view_agenda_event_StartTime);
        endtime = (TextView) itemView.findViewById(R.id.view_agenda_event_EndTime);
        title = (TextView) itemView.findViewById(R.id.view_agenda_event_title);
        colorImageView  =(ColorImageView) itemView.findViewById(R.id.view_agenda_event_color);
       alarm = itemView.findViewById(R.id.view_agenda_event_alarm);
        user = itemView.findViewById(R.id.view_agenda_user);


    }
}
