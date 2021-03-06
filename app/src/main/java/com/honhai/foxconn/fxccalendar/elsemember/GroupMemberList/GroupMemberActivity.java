package com.honhai.foxconn.fxccalendar.elsemember.GroupMemberList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;
import com.honhai.foxconn.fxccalendar.elsemember.CalendarElseFragment;
import com.honhai.foxconn.fxccalendar.elsemember.MemberList;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberActivity extends AppCompatActivity {
    private List<GroupMember> groupMemberList = new ArrayList<>();
    private Group group;
private Toolbar toolbar;
private RecyclerView recyclerView;
private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member_list);

        findViews();
        setToolbar();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupMemberActivity.this.finish();
            }
        });

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        GroupMemberAdapter adapter = new GroupMemberAdapter(groupMemberList);
        recyclerView.setAdapter(adapter);
        initMemberLists();

    }

    private void initMemberLists() {
        groupMemberList.clear();
        for (String userName : Data.getInstance().getUserNames())
            groupMemberList.add(new GroupMember(R.drawable.ic_person_black_24dp, userName));
//        GroupMember member1=new GroupMember(R.drawable.ic_person_black_24dp,"??????");
//        groupMemberList.add(member1);
    }

    private void findViews(){
        recyclerView = findViewById(R.id.rv_recycler_view);
        toolbar = findViewById(R.id.toolbar);
        title =findViewById(R.id.toolbar_title);

    }

    private void setToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_backup);
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);
        toolbar.setNavigationIcon(drawable);
        title.setTextColor(Data.getInstance().getThemeColor());
        title.setText(getString(R.string.group_member));

    }



}
