package com.honhai.foxconn.fxccalendar.elsemember.GroupManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.data.local.eventsql.EventDAO;
import com.honhai.foxconn.fxccalendar.data.local.groupsql.GroupDAO;

import java.util.ArrayList;

import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.EVENTS_UNCOMPLETED;
import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.SP_COMPLETED_EVENTS;
import static com.honhai.foxconn.fxccalendar.elsemember.SettingsActivity.SP_COMPLETED_EVENTS_TYPE;

public class GroupManagerActivity extends AppCompatActivity {
    private ArrayList<ManagerGroupList> memberLists = new ArrayList<>();
    private GroupManagerAdapter adapter;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_window_select_group_display);
        findViews();
        setToolbar();
        initData();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupManagerAdapter(memberLists);
        recyclerView.setAdapter(adapter);
//        Data.getInstance().addRefreshView((RefreshData) this);


    }


    private void findViews() {
        recyclerView = findViewById(R.id.addEventColorPopupWindowRecycler);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);

    }

    private void setToolbar() {
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_backup);

        Drawable drawable = toolbar.getNavigationIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);
        drawable = toolbar.getMenu().getItem(0).getIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);

        title.setTextColor(Data.getInstance().getThemeColor());
        title.setText(getString(R.string.settingsManageGroupPopupWindowTitle));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                SharedPreferences sharedPreferences = getSharedPreferences(Data.getUserInfo().getWorkId(), MODE_PRIVATE);
                for (ManagerGroupList groupList : memberLists)
                    sharedPreferences.edit().putBoolean(groupList.getGroupId(), groupList.isChecked()).apply();

                refreshGroup();
                refreshEvent();

                Data.getInstance().redrawViews();
                finish();
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void refreshEvent() {
        EventDAO eventDAO = new EventDAO(this);
        ArrayList<Event> events = Data.getInstance().getEvents();
        events.clear();
        SharedPreferences sharedPreferences = getSharedPreferences(SP_COMPLETED_EVENTS, Context.MODE_PRIVATE);
        int eventType = sharedPreferences.getInt(SP_COMPLETED_EVENTS_TYPE, EVENTS_UNCOMPLETED);
        events.addAll(eventDAO.getEventsByGroups(Data.getInstance().getGroups(), eventType));
    }

    private void refreshGroup() {
        GroupDAO groupDAO = new GroupDAO(this);
        ArrayList<Group> all = groupDAO.getGroupsByWorkId(Data.getUserInfo().getWorkId());
        ArrayList<Group> nows = new ArrayList<>(all);
        SharedPreferences sharedPreferences = getSharedPreferences(Data.getUserInfo().getWorkId(), MODE_PRIVATE);
        for (Group group : all){
            if (!sharedPreferences.getBoolean(group.getObjectId(),true))
                nows.remove(group);
        }
        Data.getInstance().getGroups().clear();
        Data.getInstance().getGroups().addAll(nows);
    }


    public void initData() {
        memberLists.clear();
//        MemberList group1 = new MemberList(R.drawable.ic_person_black_24dp, "未设置", "");
        //memberLists.add(group1);
        SharedPreferences sharedPreferences = getSharedPreferences(Data.getUserInfo().getWorkId(), MODE_PRIVATE);

        GroupDAO groupDAO = new GroupDAO(this);
        for (Group group : groupDAO.getGroupsByWorkId(Data.getUserInfo().getWorkId()))
            memberLists.add(new ManagerGroupList(group.getGroupName(), group.getObjectId(), sharedPreferences.getBoolean(group.getObjectId(), true)));
//        adapter.notifyDataSetChanged();
    }
}
