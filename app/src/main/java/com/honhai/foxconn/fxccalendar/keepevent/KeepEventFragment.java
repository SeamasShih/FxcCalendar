package com.honhai.foxconn.fxccalendar.keepevent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;

import java.util.ArrayList;
import java.util.List;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_IS_NO_TIME;


public class KeepEventFragment extends Fragment implements RefreshData {
    public List<Event> KeepEvent;
    private List<Event> AllEvent;
    private int i;
    private LinearLayout noKeepEvent;
    private RecyclerView myRecyclerView;
    private KeepEventAdapter keepEventAdapter;
    private ImageView addKeepEvent;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keep_event, container, false);
        noKeepEvent = view.findViewById(R.id.noKeepEvent);
        myRecyclerView = view.findViewById(R.id.KeepEventList);

        KeepEvent = new ArrayList();
        AllEvent = Data.getInstance().getEvents();
        for (i = 0; i < AllEvent.size(); i++) {
            if (AllEvent.get(i).isNoTime()) {
                KeepEvent.add(AllEvent.get(i));
            }
        }
        Data.getInstance().addRefreshView(this);

        addKeepEvent = view.findViewById(R.id.iv_add_keep_event);
        Drawable drawable = getActivity().getDrawable(R.drawable.ic_add_keep_event);
        drawable.setAlpha(255);
        drawable.setTint(Data.getInstance().getThemeColor());
        addKeepEvent.setImageDrawable(drawable);
        addKeepEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), AddEventActivity.class);
                intent.putExtra(INTENT_IS_NO_TIME,true);
                startActivity(intent);
            }
        });
        keepEventAdapter = new KeepEventAdapter(getContext(), KeepEvent);
        myRecyclerView.setLayoutManager
                (new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecyclerView.setAdapter(keepEventAdapter);
        if (KeepEvent.size() == 0) {
            noKeepEvent.setVisibility(View.VISIBLE);
            myRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            noKeepEvent.setVisibility(View.INVISIBLE);
            myRecyclerView.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onResume() {
        addKeepEvent.getDrawable().setTint(Data.getInstance().getThemeColor());
        super.onResume();
    }

    @Override
    public void refresh() {
        KeepEvent.clear();
        AllEvent = Data.getInstance().getEvents();
        for (i = 0; i < AllEvent.size(); i++) {
            if (AllEvent.get(i).isNoTime()) {
                KeepEvent.add(AllEvent.get(i));
            }
        }
        if (KeepEvent.size() == 0) {
            noKeepEvent.setVisibility(View.VISIBLE);
            myRecyclerView.setVisibility(View.INVISIBLE);
        } else {
            noKeepEvent.setVisibility(View.INVISIBLE);
            myRecyclerView.setVisibility(View.VISIBLE);
        }
        keepEventAdapter.notifyDataSetChanged();
    }
}