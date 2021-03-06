package com.honhai.foxconn.fxccalendar.addevent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.colormanager.ColorManagerActivity;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.data.local.groupsql.GroupDAO;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.Reminder;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.ReminderDAO;
import com.honhai.foxconn.fxccalendar.data.local.remindersql.ReminderUtils;
import com.honhai.foxconn.fxccalendar.widget.CustomDatePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

import static com.honhai.foxconn.fxccalendar.colormanager.ColorAdapter.SP_COLOR_LABEL;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_DAY_OF_MONTH;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_EVENT;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_IS_NO_TIME;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_MONTH;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_YEAR;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.SP_LATEST_EVENT_COLOR;
import static com.honhai.foxconn.fxccalendar.main.MainActivity.SP_THEME;

public class AddEventActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView colorLabelText, noTimeText, completeText, allDayText, remindText, repeatText;
    private EditText title, urlText, mapText, noteText;
    private ImageView allDayImg, colorLabelImg, remindImg, completeImg, noTimeImg, urlImg, mapImg, noteImg, repeatImg;
    private DirectorView timeDirectorImg, colorLabelDirectorImg;
    private Switch allDaySwitch, completeSwitch, noTimeSwitch;
    private int prevColor;
    private int currColor;
    private Event event;
    private boolean isConfirm = false;
    private boolean isCreate = false;
    private SharedPreferences colorLabelSP;
    private String[] colorStringId;
    private int[] colors;
    private TextView startYearText, startTimeText, endYearText, endTimeText;
    private CustomDatePicker customDatePicker1, customDatePicker2, customDatePicker;
    private ColorPopupWindow popupWindow;
    private RemindTimePopupWindow remindTimePopupWindow;
    private Spinner event_groupId;
    private Toast toast;
    private int currReminderType = 0;
    private int prevReminderType = 0;

    public static final int REMINDER_NONE = 0;
    public static final int REMINDER_ON_TIME = 1;
    public static final int REMINDER_FIFTEEN_BEFORE = 2;
    public static final int REMINDER_ONE_HOUR_BEFORE = 3;
    public static final int REMINDER_ONE_DAY_BEFORE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        colorLabelSP = getSharedPreferences(SP_COLOR_LABEL, Context.MODE_PRIVATE);
        colorStringId = getResources().getStringArray(R.array.stringColor);
        colors = getResources().getIntArray(R.array.eventColorArray);

        findViews();

        setToolbar();

        getIntentInfo();

        initDatePicker();

        setImgDrawable();

        colorChange(currColor);

        setEventDetailToUI();

        setTimeSettingListener();

        hideFunction();
    }

    @Override
    protected void onDestroy() {
        clearParameters();
        super.onDestroy();
    }

    private void hideFunction() {
        if (isCreate) {
            View view = findViewById(R.id.lunar_layout);
            view.setVisibility(View.GONE);

            view = findViewById(R.id.lineComplete);
            view.setVisibility(View.GONE);

            view = findViewById(R.id.layout_alarm_remind);
            view.setVisibility(View.GONE);

            view = findViewById(R.id.lineReminder);
            view.setVisibility(View.GONE);

            view = findViewById(R.id.layout_alarm_replay);
            view.setVisibility(View.GONE);
        } else {
            View view = findViewById(R.id.lunar_layout);
            view.setVisibility(View.VISIBLE);

            view = findViewById(R.id.layout_alarm_remind);
            view.setVisibility(View.VISIBLE);

            view = findViewById(R.id.layout_alarm_replay);
            view.setVisibility(View.GONE);
        }


    }

    private void clearParameters() {
        toolbar = null;
        colorLabelText = null;
        noTimeText = null;
        completeText = null;
        allDayText = null;
        remindText = null;
        repeatText = null;
        title = null;
        urlText = null;
        mapText = null;
        noteText = null;
        allDayImg = null;
        colorLabelImg = null;
        remindImg = null;
        completeImg = null;
        noTimeImg = null;
        urlImg = null;
        mapImg = null;
        noteImg = null;
        repeatImg = null;
        timeDirectorImg = null;
        colorLabelDirectorImg = null;
        allDaySwitch = null;
        completeSwitch = null;
        noTimeSwitch = null;
        event = null;
        colorLabelSP = null;
        colorStringId = null;
        colors = null;
        startYearText = null;
        startTimeText = null;
        endYearText = null;
        endTimeText = null;
        customDatePicker1 = null;
        customDatePicker2 = null;
        customDatePicker = null;
        popupWindow = null;
        event_groupId = null;
    }

    private void setToolbar() {
        toolbar.inflateMenu(R.menu.menu_add_event_toolbar);
        toolbar.getMenu().getItem(1).getIcon().setAlpha(255);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Data.getInstance().isNetworkAvailable(AddEventActivity.this)) {
                    switch (menuItem.getItemId()) {
                        case R.id.confirm:
                            if (completeSwitch.isChecked()) {
                                showToast(getString(R.string.completeEventEditRemindText), Toast.LENGTH_SHORT);
                                return true;
                            }
                            else if (!isTimeCorrect() && !noTimeSwitch.isChecked()) {
                                showToast(getString(R.string.addEventTimeIsInvalid), Toast.LENGTH_LONG);
                            }
                            else if (title.getText() == null || title.getText().length() == 0) {
                                showToast(getString(R.string.addEventTitleCantBeEmpty), Toast.LENGTH_LONG);
                            }
                            else if (isCreate)
                                createEvent();
                            else
                                modifiedEvent();
                            finish();
                            return true;
                        case R.id.cancel:
                            showDeleteCheckPopupWindow();
                            return true;
                    }
                    return false;
                }
                return false;
            }
        });
    }

    private void getIntentInfo() {
        Intent intent = getIntent();
        event = intent.getParcelableExtra(INTENT_EVENT);
        if (event == null) {
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            int year = intent.getIntExtra(INTENT_YEAR, start.get(Calendar.YEAR));
            int month = intent.getIntExtra(INTENT_MONTH, start.get(Calendar.MONTH));
            int dayOfMonth = intent.getIntExtra(INTENT_DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH));
            start.set(year, month, dayOfMonth);
            end.set(year, month, dayOfMonth);
            end.add(Calendar.HOUR_OF_DAY, 1);
            event = new Event("", start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH), start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE),
                    end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH), end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE),
                    false, getSharedPreferences(SP_THEME, Context.MODE_PRIVATE).getInt(SP_LATEST_EVENT_COLOR, getResources().getColor(R.color.colorEventRed)),
                    0, false, 0, "", "", "", intent.getBooleanExtra(INTENT_IS_NO_TIME, false), "");
            isCreate = true;
            toolbar.getMenu().getItem(0).setVisible(false);
        }
        prevColor = event.getColor();
        currColor = prevColor;
    }

    private void setTimeSettingListener() {
        startYearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDatePicker.setTimeText(startYearText.getText().toString() + " " + startTimeText.getText().toString(),
                        endYearText.getText().toString() + " " + endTimeText.getText().toString(),
                        true);

                customDatePicker.show(startYearText, startTimeText, startYearText.getText().toString() + " " + startTimeText.getText().toString());
            }
        });
        startTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDatePicker.setTimeText(startYearText.getText().toString() + " " + startTimeText.getText().toString(),
                        endYearText.getText().toString() + " " + endTimeText.getText().toString(),
                        true);
                customDatePicker.show(startYearText, startTimeText, startYearText.getText().toString() + " " + startTimeText.getText().toString());
            }
        });
        endYearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDatePicker.setTimeText(startYearText.getText().toString() + " " + startTimeText.getText().toString(),
                        endYearText.getText().toString() + " " + endTimeText.getText().toString(),
                        false);
                customDatePicker.show(endYearText, endTimeText, endYearText.getText().toString() + " " + endTimeText.getText().toString());
            }
        });
        endTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDatePicker.setTimeText(startYearText.getText().toString() + " " + startTimeText.getText().toString(),
                        endYearText.getText().toString() + " " + endTimeText.getText().toString(),
                        false);
                customDatePicker.show(endYearText, endTimeText, endYearText.getText().toString() + " " + endTimeText.getText().toString());
            }
        });
        allDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customDatePicker = customDatePicker1;

                    startTimeText.setVisibility(View.GONE);
                    endTimeText.setVisibility(View.GONE);
                    changeTimeAllDayText();

                } else {
                    customDatePicker = customDatePicker2;
                    startTimeText.setVisibility(View.VISIBLE);
                    endTimeText.setVisibility(View.VISIBLE);
                    changeTimeNormalText();
                }
                if (isChecked) {
                    allDayText.setTextColor(currColor);
                    allDayImg.setImageTintList(ColorStateList.valueOf(currColor));
                } else {
                    allDayText.setTextColor(getResources().getColor(R.color.colorPopupSwitchCalendarText));
                    allDayImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
                }
            }
        });
    }

    private void changeTimeNormalText() {
        startTimeText.setText(doubleDigit(event.getStartHour()) + ":" + doubleDigit(event.getStartMinute()));
        endTimeText.setText(doubleDigit(event.getEndHour()) + ":" + doubleDigit(event.getEndMinute()));
    }

    private void changeTimeAllDayText() {
        startTimeText.setText("00:00");
        endTimeText.setText("23:59");
    }


    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        startTimeText.setText(now.split(" ")[0]);
        startTimeText.setText(now);


        customDatePicker1 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(TextView yearView, TextView timeView, String time) { // ????????????????????????????????????
                yearView.setText(time.split(" ")[0]);
                timeView.setText(time.split(" ")[1]);
            }
        }, "1999-01-01 00:00", "2100-01-01 23:59"); // ??????????????????????????????yyyy-MM-dd HH:mm???????????????????????????
        customDatePicker1.showSpecificTime(false); // ??????????????????
        customDatePicker1.setIsLoop(true); // ?????????????????????
        customDatePicker1.setAllDay(true);

        customDatePicker2 = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(TextView yearView, TextView timeView, String time) { // ????????????????????????????????????
                yearView.setText(time.split(" ")[0]);
                timeView.setText(time.split(" ")[1]);
            }
        }, "1999-01-01 00:00", "2100-01-01 00:00"); // ??????????????????????????????yyyy-MM-dd HH:mm???????????????????????????
        customDatePicker2.showSpecificTime(true); // ???????????????
        customDatePicker2.setIsLoop(true); // ??????????????????

        customDatePicker = customDatePicker2;
    }

    private void setImgDrawable() {
//        timeDirectorImg.setBackground(getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));
//        timeDirectorImg.getBackground().setTint(currColor);
//        colorLabelDirectorImg.setBackground(getDrawable(R.drawable.ic_keyboard_arrow_right_black_24dp));

        remindImg.setImageDrawable(getDrawable(R.drawable.ic_alarm_on_black_24dp));
        remindImg.getDrawable().setAlpha(255);
        allDayImg.setImageDrawable(getDrawable(R.drawable.ic_brightness_4_black_24dp));
        allDayImg.getDrawable().setAlpha(255);
    }

    private void setEventDetailToUI() {
        title.setText(event.getTitle());
        setEventTimeItem();
        setSwitchTypeItem();
        setEditTextTypeItem();
        setGroupIdSpinner();
        setReminderType();
    }

    private void setReminderType() {
        if (event.getObjectId().equals("")) {
            View view = findViewById(R.id.layout_alarm_remind);
            view.setVisibility(View.GONE);
            return;
        }
        ReminderDAO reminderDAO = new ReminderDAO(this);
        Reminder reminder = reminderDAO.get(event.getObjectId());
        if (reminder != null) {
            currReminderType = reminder.getReminderType();
            prevReminderType = currReminderType;
            setReminderColor(currReminderType != REMINDER_NONE, currColor);
            String[] strings = getResources().getStringArray(R.array.stringRemind);
            remindText.setText(strings[currReminderType]);
        }
    }

    private void setEditTextTypeItem() {
        urlText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    urlImg.getBackground().setTint(currColor);
                else
                    urlImg.getBackground().setTint(getResources().getColor(R.color.colorPopupSwitchCalendarText));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mapText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    mapImg.getBackground().setTint(currColor);
                else
                    mapImg.getBackground().setTint(getResources().getColor(R.color.colorPopupSwitchCalendarText));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0)
                    noteImg.getBackground().setTint(currColor);
                else
                    noteImg.getBackground().setTint(getResources().getColor(R.color.colorPopupSwitchCalendarText));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        urlText.setText(event.getUrl());
        mapText.setText(event.getMap());
        noteText.setText(event.getNote());
    }

    @SuppressLint("SetTextI18n")
    private void setEventTimeItem() {
        startYearText.setText(event.getStartYear() + "-" + doubleDigit(event.getStartMonth() + 1) + "-" + doubleDigit(event.getStartDayOfMonth()));
        startTimeText.setText(doubleDigit(event.getStartHour()) + ":" + doubleDigit(event.getStartMinute()));
        endYearText.setText(event.getEndYear() + "-" + doubleDigit(event.getEndMonth() + 1) + "-" + doubleDigit(event.getEndDayOfMonth()));
        endTimeText.setText(doubleDigit(event.getEndHour()) + ":" + doubleDigit(event.getEndMinute()));
    }

    private boolean isTimeCorrect() {
        String[] startYearStrings = startYearText.getText().toString().split("-");
        String[] startTimeStrings = startTimeText.getText().toString().split(":");
        String[] endYearStrings = endYearText.getText().toString().split("-");
        String[] endTimeStrings = endTimeText.getText().toString().split(":");
        int sY = Integer.parseInt(startYearStrings[0]);
        int sM = Integer.parseInt(startYearStrings[1]);
        int sD = Integer.parseInt(startYearStrings[2]);
        int sH = Integer.parseInt(startTimeStrings[0]);
        int sMin = Integer.parseInt(startTimeStrings[1]);
        int eY = Integer.parseInt(endYearStrings[0]);
        int eM = Integer.parseInt(endYearStrings[1]);
        int eD = Integer.parseInt(endYearStrings[2]);
        int eH = Integer.parseInt(endTimeStrings[0]);
        int eMin = Integer.parseInt(endTimeStrings[1]);
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.set(sY, sM - 1, sD, sH, sMin);
        end.set(eY, eM - 1, eD, eH, eMin, start.get(Calendar.SECOND));
        return end.after(start);
    }

    private void showDeleteCheckPopupWindow() {
        View layout = getLayoutInflater().inflate(R.layout.popup_window_delete_event_message, null);
        final DeleteMessagePopupWindow popupWindow = new DeleteMessagePopupWindow(this, layout, (int) (getWindow().getDecorView().getWidth() * .8f), (int) (getWindow().getDecorView().getHeight() * .2f));
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        TextView cancel = layout.findViewById(R.id.deleteCancelBtn);
        TextView confirm = layout.findViewById(R.id.deleteConfirmBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data.getInstance().deleteEventForCloud(event.getObjectId());
                ArrayList<Event> events = Data.getInstance().getEvents();
                for (int i = 0; i < events.size(); i++) {
                    if (events.get(i).getObjectId().equals(event.getObjectId())) {
                        events.remove(i);
                        break;
                    }
                }
                popupWindow.dismiss();
                finish();
            }
        });
    }

    private void createEvent() {
        setEvent();
        Data.getInstance().addEventForCloud(event);
        Data.getInstance().getEvents().add(event);
    }

    private String doubleDigit(int i) {
        return i < 10 ? "0" + i : "" + i;
    }

    private void setSwitchTypeItem() {
        allDayImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
        allDaySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    allDayText.setTextColor(currColor);
                    allDayImg.setImageTintList(ColorStateList.valueOf(currColor));
                } else {
                    allDayText.setTextColor(getResources().getColor(R.color.colorPopupSwitchCalendarText));
                    allDayImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
                }
            }
        });
        completeImg.getBackground().setTint(getResources().getColor(R.color.colorPopupSwitchCalendarText));
        completeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    completeText.setTextColor(currColor);
                    completeImg.getBackground().setTintList(ColorStateList.valueOf(currColor));
                    title.setCursorVisible(false);
                } else {
                    completeText.setTextColor(getResources().getColor(R.color.colorPopupSwitchCalendarText));
                    completeImg.getBackground().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
                    title.setCursorVisible(true);
                }
            }
        });
        noTimeImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
        noTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noTimeText.setTextColor(currColor);
                    noTimeImg.getBackground().setTintList(ColorStateList.valueOf(currColor));
                    View view = findViewById(R.id.all_day_layout);
                    view.setVisibility(View.GONE);
                    view = findViewById(R.id.customer_calendar);
                    view.setVisibility(View.GONE);
                    view = findViewById(R.id.layout_alarm_remind);
                    view.setVisibility(View.GONE);
                    view = findViewById(R.id.lineComplete);
                    view.setVisibility(View.GONE);
                    view = findViewById(R.id.lineReminder);
                    view.setVisibility(View.GONE);
                } else {
                    noTimeText.setTextColor(getResources().getColor(R.color.colorPopupSwitchCalendarText));
                    noTimeImg.getBackground().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
                    View view = findViewById(R.id.all_day_layout);
                    view.setVisibility(View.VISIBLE);
                    view = findViewById(R.id.customer_calendar);
                    view.setVisibility(View.VISIBLE);
                    if (!event.getObjectId().equals("")) {
                        view = findViewById(R.id.layout_alarm_remind);
                        view.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        allDaySwitch.setChecked(event.isAllDay());
        completeSwitch.setChecked(event.isComplete());
        noTimeSwitch.setChecked(event.isNoTime());
        if (allDaySwitch.isChecked()) {
            startTimeText.setVisibility(View.GONE);
            endTimeText.setVisibility(View.GONE);
            customDatePicker = customDatePicker1;
        }
        if (noTimeSwitch.isChecked()) {
            View view = findViewById(R.id.all_day_layout);
            view.setVisibility(View.GONE);
            view = findViewById(R.id.customer_calendar);
            view.setVisibility(View.GONE);
        } else
            noTimeImg.getBackground().setTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPopupSwitchCalendarText)));
    }

    private void setEventTime() {
        String[] startYearStrings = startYearText.getText().toString().split("-");
        String[] startTimeStrings = startTimeText.getText().toString().split(":");
        String[] endYearStrings = endYearText.getText().toString().split("-");
        String[] endTimeStrings = endTimeText.getText().toString().split(":");
        int sY = Integer.parseInt(startYearStrings[0]);
        int sM = Integer.parseInt(startYearStrings[1]);
        int sD = Integer.parseInt(startYearStrings[2]);
        int sH = Integer.parseInt(startTimeStrings[0]);
        int sMin = Integer.parseInt(startTimeStrings[1]);
        int eY = Integer.parseInt(endYearStrings[0]);
        int eM = Integer.parseInt(endYearStrings[1]);
        int eD = Integer.parseInt(endYearStrings[2]);
        int eH = Integer.parseInt(endTimeStrings[0]);
        int eMin = Integer.parseInt(endTimeStrings[1]);
        event.setStartYear(sY);
        event.setStartMonth(sM - 1);
        event.setStartDayOfMonth(sD);
        event.setStartHour(sH);
        event.setStartMinute(sMin);
        event.setEndYear(eY);
        event.setEndMonth(eM - 1);
        event.setEndDayOfMonth(eD);
        event.setEndHour(eH);
        event.setEndMinute(eMin);
    }

    private void setEvent() {
        event.setTitle(title.getText().toString());
        event.setAllDay(allDaySwitch.isChecked());
        event.setColor(currColor);
        setEventTime();
//        event.setRepeat();
        event.setComplete(completeSwitch.isChecked());
        String[] groupInfo = event_groupId.getSelectedItem().toString().split("-");
        event.setGroupId(Integer.parseInt(groupInfo[0]));
        event.setNote(noteText.getText().toString());
        event.setMap(mapText.getText().toString());
        event.setUrl(urlText.getText().toString());
        event.setNoTime(noTimeSwitch.isChecked());
    }

    private void modifiedEvent() {
        setEvent();
        updateEvent(event);
        setEventReminderToLocal();
    }

    private void setEventReminderToLocal() {
        if (event.getObjectId().equals("") || currReminderType == REMINDER_NONE)
            return;
        ReminderUtils.updateEventReminder(event, currReminderType, this);
    }

    private void updateEvent(Event event) {
        Data.getInstance().updateEventForCloud(
                event.getTitle(),
                event.getStartYear(),
                event.getStartMonth(),
                event.getStartDayOfMonth(),
                event.getStartHour(),
                event.getStartMinute(),
                event.getEndYear(),
                event.getEndMonth(),
                event.getEndDayOfMonth(),
                event.getEndHour(),
                event.getEndMinute(),
                event.isAllDay(),
                event.getColor(),
                event.getRepeat(),
                event.isComplete(),
                event.getGroupId(),
                event.getNote(),
                event.getMap(),
                event.getUrl(),
                event.isNoTime(),
                event.getObjectId()
        );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN &&
                completeSwitch.isChecked() &&
                !isTouchView(ev, completeSwitch) &&
                !isTouchView(ev, toolbar)) {
            showToast(getString(R.string.completeEventEditRemindText), Toast.LENGTH_SHORT);
            return true;
        }
        View v = getCurrentFocus();
        if (isShouldHideInput(v, ev)) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isTouchView(MotionEvent ev, View view) {
        int[] position = new int[2];
        view.getLocationInWindow(position);
        float[] touch = {ev.getX(), ev.getY()};
        int[] length = {view.getWidth(), view.getHeight()};
        return position[0] <= touch[0] && position[0] + length[0] >= touch[0] &&
                position[1] <= touch[1] && position[1] + length[1] >= touch[1];
    }

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v instanceof EditText) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    @Override
    protected void onResume() {
        if (popupWindow != null)
            popupWindow.changColorLabel();
        super.onResume();
    }

    private void findViews() {
        title = findViewById(R.id.alarm_title);

        colorLabelText = findViewById(R.id.alarm_color);
        colorLabelImg = findViewById(R.id.iv_color);
        colorLabelDirectorImg = findViewById(R.id.iv_point);


        startYearText = findViewById(R.id.alarm_start_year);
        startTimeText = findViewById(R.id.alarm_start_time);
        endYearText = findViewById(R.id.alarm_end_year);
        endTimeText = findViewById(R.id.alarm_end_time);
        timeDirectorImg = findViewById(R.id.iv_time);

        toolbar = findViewById(R.id.add_event_toolbar);

        allDayText = findViewById(R.id.tvAllDayText);
        allDayImg = findViewById(R.id.iv_all_day);
        allDaySwitch = findViewById(R.id.sw_all_day);

        completeText = findViewById(R.id.tvCompleteText);
        completeImg = findViewById(R.id.iv_complete);
        completeSwitch = findViewById(R.id.sw_complete);

        noTimeText = findViewById(R.id.tv_event_show);
        noTimeImg = findViewById(R.id.iv_vibrate);
        noTimeSwitch = findViewById(R.id.sw_vibrate);

        remindImg = findViewById(R.id.iv_remind);
        remindText = findViewById(R.id.alarm_remind);

        urlText = findViewById(R.id.alarm_tone_Path);
        urlImg = findViewById(R.id.iv_alarm_tone_Path);

        mapText = findViewById(R.id.alarm_local);
        mapImg = findViewById(R.id.iv_local);

        noteText = findViewById(R.id.alarm_description);
        noteImg = findViewById(R.id.iv_description);

        repeatText = findViewById(R.id.alarm_replay);
        repeatImg = findViewById(R.id.iv_replay);

        event_groupId = findViewById(R.id.event_groupId);

    }

    public void colorChange(int color) {
        toolbar.getNavigationIcon().setTint(color);
        toolbar.getMenu().getItem(0).getIcon().setTint(color);
        toolbar.getMenu().getItem(1).getIcon().setTint(color);
        timeDirectorImg.setColor(color);

        colorLabelDirectorImg.setColor(color);
        colorLabelImg.getBackground().setTint(color);

        title.setTextColor(color);
        title.setHintTextColor(Color.argb(100, Color.red(color), Color.green(color), Color.blue(color)));
        setCursorColor(title, color);

        allDaySwitch.getThumbDrawable().setTintList(createColorStateList(color, Color.rgb(240, 240, 240)));
        allDaySwitch.getTrackDrawable().setTintList(createColorStateList(color, Color.GRAY));
        if (allDaySwitch.isChecked()) {
            allDayImg.setImageTintList(ColorStateList.valueOf(color));
            allDayText.setTextColor(color);
        }

        completeSwitch.getThumbDrawable().setTintList(createColorStateList(color, Color.rgb(240, 240, 240)));
        completeSwitch.getTrackDrawable().setTintList(createColorStateList(color, Color.GRAY));
        if (completeSwitch.isChecked()) {
            completeImg.getBackground().setTint(color);
            completeText.setTextColor(color);
        }

        noTimeSwitch.getThumbDrawable().setTintList(createColorStateList(color, Color.rgb(240, 240, 240)));
        noTimeSwitch.getTrackDrawable().setTintList(createColorStateList(color, Color.GRAY));
        if (noTimeSwitch.isChecked()) {
            noTimeImg.getBackground().setTint(color);
            noTimeText.setTextColor(color);
        }

        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == color) {
                colorLabelText.setText(colorLabelSP.getString(colorStringId[i], colorStringId[i]));
                if (colorLabelText.getText().equals(""))
                    colorLabelText.setText(colorStringId[i]);
                break;
            }
        }

        setReminderColor(currReminderType != REMINDER_NONE, color);

        urlText.setTextColor(color);
        setCursorColor(urlText, color);
        if (urlText.getText().toString().length() != 0)
            urlImg.getBackground().setTint(color);
        mapText.setTextColor(color);
        setCursorColor(mapText, color);
        if (mapText.getText().toString().length() != 0)
            mapImg.getBackground().setTint(color);
        noteText.setTextColor(color);
        setCursorColor(noteText, color);
        if (noteText.getText().toString().length() != 0)
            noteImg.getBackground().setTint(color);

        currColor = color;
    }

    private void showToast(String string, int duration) {
        if (toast == null) {
            toast = Toast.makeText(this, string, duration);
            toast.show();
            return;
        }
        toast.cancel();
        toast = Toast.makeText(this, string, duration);
        toast.show();
    }

    public void setCursorColor(EditText view, @ColorInt int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }

    private ColorStateList createColorStateList(int checked, int normal) {
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_alarm_color:
                View popupLayout = getLayoutInflater().inflate(R.layout.add_event_color_popup_window, (ViewGroup) findViewById(R.id.add_event_color_window_layout));
                popupWindow = new ColorPopupWindow(this, popupLayout, getWindow().getDecorView().getWidth() - 100, getWindow().getDecorView().getHeight() * 3 / 5);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                TextView manage = popupLayout.findViewById(R.id.tv_group_manage_cancel);
                manage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(AddEventActivity.this, ColorManagerActivity.class);
                        startActivity(intent);
                    }
                });
                TextView confirm = popupLayout.findViewById(R.id.tv_group_manage_confirm);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isConfirm = true;
                        SharedPreferences sharedPreferences = getSharedPreferences(SP_THEME, Context.MODE_PRIVATE);
                        sharedPreferences.edit().putInt(SP_LATEST_EVENT_COLOR, currColor).apply();
                        popupWindow.dismiss();
                    }
                });
                isConfirm = false;
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
                        if (isConfirm) {
                            prevColor = currColor;
                            colorChange(currColor);
                        } else
                            colorChange(prevColor);
                    }
                });
                break;
            case R.id.layout_alarm_remind:
                View popupLayout1 = getLayoutInflater().inflate(R.layout.popup_window_set_alarm_time, (ViewGroup) findViewById(R.id.popup_set_time_window_layout));
                remindTimePopupWindow = new RemindTimePopupWindow(this, new RemindTimePopupWindow.ResultHandeler() {
                    @Override
                    public void handel(int reminderType) {
                        currReminderType = reminderType;
                        setReminderColor(currReminderType != REMINDER_NONE, currColor);
                    }
                }, popupLayout1, currReminderType, currColor,
                        (int) (getWindow().getDecorView().getWidth() * .9f), getWindow().getDecorView().getHeight() * 3 / 5);
                remindTimePopupWindow.setOutsideTouchable(true);
                remindTimePopupWindow.setFocusable(true);
                remindTimePopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                WindowManager.LayoutParams lp1 = getWindow().getAttributes();
                lp1.alpha = .5f;
                getWindow().setAttributes(lp1);
                TextView tvcancle = popupLayout1.findViewById(R.id.tv_cancel);
                tvcancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currReminderType = prevReminderType;
                        remindText.setText(remindTimePopupWindow.getReminderString(prevReminderType));
                        setReminderColor(currReminderType != REMINDER_NONE, currColor);
                        remindTimePopupWindow.setOutSideTouch(false);
                        remindTimePopupWindow.dismiss();
                    }

                });
                TextView tvconfirm = popupLayout1.findViewById(R.id.tv_group_manage_confirm);
                tvconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                        prevReminderType = currReminderType;
                        remindText.setText(remindTimePopupWindow.getReminderString(currReminderType));
                        setReminderColor(currReminderType != REMINDER_NONE, currColor);
                        remindTimePopupWindow.setOutSideTouch(false);
                        remindTimePopupWindow.dismiss();
                    }
                });
                remindTimePopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp = getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                        if (remindTimePopupWindow.isOutSideTouch()) {
                            currReminderType = prevReminderType;
                            remindText.setText(remindTimePopupWindow.getReminderString(prevReminderType));
                            setReminderColor(currReminderType != REMINDER_NONE, currColor);
                        }
                    }
                });
        }
    }

    private void setReminderColor(boolean isShow, int color) {
        if (isShow) {
            remindImg.getDrawable().setTint(color);
            remindText.setTextColor(color);
        } else {
            remindImg.getDrawable().setTint(getResources().getColor(R.color.colorPopupSwitchCalendarText));
            remindText.setTextColor(getResources().getColor(R.color.colorPopupSwitchCalendarText));
        }
    }

    private void setGroupIdSpinner() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.clear();
        GroupDAO groupDAO = new GroupDAO(this);
        for (Group group : groupDAO.getGroupsByWorkId(Data.getUserInfo().getWorkId())) {
            arrayList.add(group.getGroupId() + "-" + group.getGroupName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayList);
        event_groupId.setAdapter(adapter);

        for (int i = 0; i < arrayList.size(); i++) {
            String[] groupInfo = arrayList.get(i).split("-");
            if (event.getGroupId() == Integer.parseInt(groupInfo[0])) {
                event_groupId.setSelection(i);
            }
        }

        event_groupId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (completeSwitch.isChecked() && event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    showToast(getString(R.string.completeEventEditRemindText), Toast.LENGTH_SHORT);
                    return true;
                }
                return false;
            }
        });
    }
}

