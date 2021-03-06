package com.honhai.foxconn.fxccalendar.elsemember;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;
import com.honhai.foxconn.fxccalendar.main.MainActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CalendarElseFragment extends Fragment implements RefreshData {

    private RecyclerView recyclerView;
    private ArrayList<MemberList> memberLists = new ArrayList<>();
    private String workID;
    private GroupAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View elseView = inflater.inflate(R.layout.fragment_else_layout, null);
        initGroups();

        recyclerView = elseView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupAdapter(memberLists);
        recyclerView.setAdapter(adapter);

        Data.getInstance().addRefreshView(this);
        return elseView;
    }

    private void initGroups() {
        //MemberList group1 = new MemberList(R.drawable.ic_person_black_24dp, "?????????", "");
        //memberLists.add(group1);
        // MemberList group2 =new MemberList(R.drawable.drawable_bottom_keep,"RD4 SW 1???","??????????????????");
        // memberLists.add(group2);


    }

    public void refresh() {
        memberLists.clear();
//        MemberList group3 = new MemberList(R.drawable.drawable_bottom_feed, "???????????????????????????!", "");
//        memberLists.add(group3);
//        workID = Data.getUserInfo().getWorkId();
        for (Group group : Data.getInstance().getGroups())
            memberLists.add(new MemberList(R.drawable.ic_people_black_24dp, group.getGroupName(), group.getObjectId()));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();

    }
}
