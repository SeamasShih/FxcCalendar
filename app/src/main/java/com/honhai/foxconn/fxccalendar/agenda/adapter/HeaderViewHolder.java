package com.honhai.foxconn.fxccalendar.agenda.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;


public class HeaderViewHolder extends RecyclerView.ViewHolder {
    TextView timeday;
    TextView today;

    public HeaderViewHolder (View itemView) {
        super(itemView);
        System.err.println("GroupViewHolder");
        timeday = (TextView) itemView.findViewById(R.id.view_agenda_day_of_month);
        today = (TextView) itemView.findViewById(R.id.view_agenda_today);
    }
}

