package com.honhai.foxconn.fxccalendar.elsemember;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.colormanager.ColorManagerActivity;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.local.eventsql.EventDAO;
import com.honhai.foxconn.fxccalendar.elsemember.PopupWindow.ColorPicker;
import com.honhai.foxconn.fxccalendar.elsemember.PopupWindow.ColorPickerPopupWindow;
import com.honhai.foxconn.fxccalendar.elsemember.ShowEventPopWindow.ShowEventPopupWindow;
import com.honhai.foxconn.fxccalendar.login.ModifyActivity;

import java.util.ArrayList;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.SP_THEME;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.SP_THEME_COLOR;

public class SettingsActivity extends AppCompatActivity {

    private TextView tv_alarm_setting, tv_label_manager, tv_color_theme, tv_personal_information;
    private TextView title;
//    private ImageButton ib_left_return, ib_right_save;

    private ImageView iv_modify, iv_edit, iv_note, iv_color_theme, iv_label_manage, iv_select, iv_personal_information, iv_modify_personal;
    private ConstraintLayout rl_event_label, rl_alarm_color, rl_alarm_vibrate, layout_event_personal;
    private Toolbar toolbar;
    private ColorPickerPopupWindow mPopWindow;
    private SharedPreferences colorSharedPreference;
    private int pervColor;
    private int currColor;
    private int eventType;
    //    public static final String SP_EVENTS_TYPE = "EventsType";
    private SharedPreferences showEventsPreferences;

    public static final int EVENTS_ALL = 0;
    public static final int EVENTS_COMPLETED = 1;
    public static final int EVENTS_UNCOMPLETED = 2;

    public static final String SP_COMPLETED_EVENTS = "CompletedEvents";
    public static final String SP_COMPLETED_EVENTS_TYPE = "CompletedEventsType";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_a);
        initSharePreference();
        findViews();
        pervColor = Data.getInstance().getThemeColor();
        currColor = pervColor;

        setToolbar();
        setViewsColor();


        rl_alarm_color.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopupWindow();
                // Toast.makeText(getApplicationContext(),"????????????????????????",Toast.LENGTH_LONG).show();
            }
        });

        rl_alarm_vibrate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                showCompletedEvents();

            }
        });


        rl_event_label.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, ColorManagerActivity.class);
                startActivity(intent);
            }
        });

        layout_event_personal.setOnClickListener(new OnClickListener() {    //??????????????????????????????
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"????????????",Toast.LENGTH_LONG).show();
                Intent intent = new Intent();
                intent.setClass(SettingsActivity.this, ModifyActivity.class);
                startActivity(intent);
            }
        });



    }

    private void refreshEvent() {
        EventDAO eventDAO = new EventDAO(this);
        ArrayList<Event> events = Data.getInstance().getEvents();
        events.clear();
        events.addAll(eventDAO.getEventsByGroups(Data.getInstance().getGroups(), eventType));
        Data.getInstance().setEventType(eventType);
        Data.getInstance().redrawViews();
    }

    private void initSharePreference() {
        showEventsPreferences = getSharedPreferences(SP_COMPLETED_EVENTS, Context.MODE_PRIVATE);
        eventType = showEventsPreferences.getInt(SP_COMPLETED_EVENTS_TYPE, EVENTS_UNCOMPLETED);
    }

    private void findViews() {

        title = findViewById(R.id.toolbar_title);
        toolbar = findViewById(R.id.toolbar);


        tv_alarm_setting = findViewById(R.id.alarm_setting);
        tv_color_theme = findViewById(R.id.tv_color);
        tv_label_manager = findViewById(R.id.tv_label_manager);


        iv_modify = findViewById(R.id.iv_modify);
        iv_edit = findViewById(R.id.iv_edit);
        iv_note = findViewById(R.id.iv_note);
        iv_color_theme = findViewById(R.id.iv_color_theme);
        iv_label_manage = findViewById(R.id.iv_label_manage);
        iv_select = findViewById(R.id.iv_select);

        rl_event_label = findViewById(R.id.layout_color_label_management);
        rl_alarm_vibrate = findViewById(R.id.layout_complete);
        rl_alarm_color = findViewById(R.id.layout_theme_color);


        //??????????????????????????????
        iv_personal_information = findViewById(R.id.iv_personal_information);
        tv_personal_information = findViewById(R.id.tv_personal_information);
        iv_modify_personal = findViewById(R.id.iv_modify_personal);
        layout_event_personal = findViewById(R.id.layout_change_personal_detail);


    }

    private void setToolbar() {

        toolbar.setNavigationIcon(R.drawable.ic_backup);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        title.setText(getString(R.string.Calendar_Settings));


        Drawable drawable = toolbar.getNavigationIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);
        drawable = toolbar.getMenu().getItem(0).getIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.confirm) {
                    Data.getInstance().setThemeColor(currColor);

                    colorSharedPreference = getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor colorEdit = colorSharedPreference.edit();
                    colorEdit.putInt(SP_THEME_COLOR, currColor);
                    colorEdit.apply();

                    showEventsPreferences.edit().putInt(SP_COMPLETED_EVENTS_TYPE, eventType).apply();
                    refreshEvent();
                    finish();

                    finish();
                    return true;
                }

                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Data.getInstance().setThemeColor(pervColor);
        super.onBackPressed();
    }

    private void setViewsColor() {
        title.setTextColor(currColor);
        tv_alarm_setting.setTextColor(currColor);
        tv_color_theme.setTextColor(currColor);
        tv_label_manager.setTextColor(currColor);

        tv_personal_information.setTextColor(currColor);       //??????????????????????????????


        Drawable drawable_modify_edit = getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp);
        iv_modify.setImageDrawable(drawable_modify_edit);
        iv_edit.setImageDrawable(drawable_modify_edit);
        iv_modify_personal.setImageDrawable(drawable_modify_edit);//??????????????????????????????
        drawable_modify_edit.setTint(currColor);

        Drawable drawable_iv_personal_information = iv_personal_information.getBackground();//??????????????????????????????
        drawable_iv_personal_information.setTint(currColor);

        Drawable drawable_iv_label_manage = iv_label_manage.getBackground();
        drawable_iv_label_manage.setTint(currColor);


        Drawable drawable_iv_color_theme = iv_color_theme.getBackground();
        drawable_iv_color_theme.setTint(currColor);

        Drawable drawable_iv_note = iv_note.getBackground();
        drawable_iv_note.setAlpha(255);
        drawable_iv_note.setTint(currColor);

        Drawable drawable_iv_select = iv_select.getBackground();
        drawable_iv_select.setTint(currColor);

        Drawable drawable = toolbar.getNavigationIcon();
//        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setTint(currColor);
        drawable.setAlpha(255);
        toolbar.setNavigationIcon(drawable);

//       Drawable drawable_toolbar_menu= toolbar.getMenu().getItem(0).getIcon();
//       drawable_toolbar_menu.setTint(currColor);
//        toolbar.getMenu().getItem(0).getIcon().setTint(currColor);

        MenuItem item = toolbar.getMenu().getItem(0);
        Drawable drawable_toolbar_menu = item.getIcon();
        drawable_toolbar_menu.setTint(currColor);
//        item.setIcon(drawable);

    }

    private ColorStateList createColorStateList(int checked, int normal) {
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

    private void showPopupWindow() {

        mPopWindow = new ColorPickerPopupWindow(SettingsActivity.this);
        mPopWindow.setOnConfirmListener(new ColorPickerPopupWindow.OnConfirmListener() {
            @Override
            public void onConfirm() {
                Data.getInstance().setThemeColor(currColor);
            }

        });
        mPopWindow.setOnCancelListener(new ColorPickerPopupWindow.OnCancelListener() {
            @Override
            public void onCancel() {
                currColor = pervColor;
                setViewsColor();
            }
        });
        mPopWindow.setOnColorChoosingListener(new ColorPicker.OnColorChoosingListener() {
            @Override
            public void OnColorChoosing(int color) {
                currColor = color;
                setViewsColor();
            }
        });
        mPopWindow.setOnColorChoseListener(new ColorPicker.OnColorChoseListener() {
            @Override
            public void OnColorChose(int color) {
                currColor = color;
                setViewsColor();
            }
        });
    }

    private void showCompletedEvents() {

//        View popupLayout = getLayoutInflater().inflate(R.layout.popup_window_completed_events_select, (ViewGroup) findViewById(R.id.parent_completed_events));
        View popupLayout = getLayoutInflater().inflate(R.layout.popup_window_completed_events_select, null);
        ShowEventPopupWindow popupWindow = new ShowEventPopupWindow(this, popupLayout, getWindow().getDecorView().getWidth() - 100, getWindow().getDecorView().getHeight() / 3);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
//        popupWindow.showAsDropDown(popupLayout, 0, 0, Gravity.CENTER);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = .5f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        popupWindow.setPopupCompletedEventsListType(eventType);

    }

    public void setEventsType(int eventType) {
        this.eventType = eventType;
    }
}
