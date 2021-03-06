package com.honhai.foxconn.fxccalendar.month;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Path;
import android.graphics.Region;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.Canvas;
import android.view.WindowManager;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.holiday.Holiday;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;
import com.honhai.foxconn.fxccalendar.eventpreview.MyDialog2;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.weekly.data.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_EVENT;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.dayDiff;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getAfterDay;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getCurrentDate;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getMonth;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getMonthNumFromDate;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getWeekNumOfMonth;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getWordCount;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.getYear;
import static com.honhai.foxconn.fxccalendar.month.CalendarUtil.solarToLunar;


public class CalendarMonthView extends View implements RefreshData, View.OnTouchListener {



    private final String ALL_DAY = getResources().getString(R.string.calendarWeeklyAllDayText);
    private final String HOLIDAY = getResources().getString(R.string.calendarWeeklyHolidayText);
    private final String WORKDAY = getResources().getString(R.string.calendarWeeklyWorkdayText);
    private final String OBSERVED = getResources().getString(R.string.calendarWeeklyObservedText);


    @Override
    public void refresh() {
        setYearMonth(year,month);
        //invalidate();
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     *
     *
     */
    public static enum CalendarState {
        TODAY, CURRENT_MONTH, LAST_MONTH,NEXT_MONTH
    }


    OnDoubleClickListener eventDoubleClickListener;
    OnLongPressListener eventLongPressListener;
    private GestureDetector gestureDetector;

    private MonthDay[] days;

    String[] weeks = getResources().getStringArray(R.array.weeks);
    /** ???????????? */
    private int width;
    /** ???????????? */
    private int height;
    /** ???????????? */
    private int[][] dateNum;
    /** ???????????????????????? */
    private CalendarState[][] calendarStates;
    /**???????????????????????????*/
    private int weekNum;
    /** ??? */
    private int year;
    /** ??? */
    private int month;
    //???????????????
    private int lunarYear;
    //???????????????
    private int lunarMonth;

    private int[] date;
    /** ????????? */
    private DrawCalendar drawCalendar;

    /**?????????????????????*/
    private float weekHeight;

    /** ?????????????????? */
    private float dateNumWidth;

    private float dateGapWidth;

    private float dateNumHeight;

    private float  dateNumHeighter;
    //Cursor????????????
    private int dateCursorX,dateCursorY;

    private Boolean isCursor = false;

    private float eventHeight;

    private CalendarDay cursor;


    public CalendarMonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    public CalendarMonthView(final Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI(context);
        gestureDetector = new GestureDetector(context,new simpleGestureListener());
        setOnTouchListener(this);
        setClickable(true);
        setDoubleClickListener(new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(MotionEvent event) {
                Intent intent = new Intent();
                date = new int[3];
                date = CalendarUtil.getCurrentDate(year,month,dateNum[dateCursorY][dateCursorX],dateCursorY,dateCursorX);
                intent.putExtra("date",date);
                MyDialog2 myDialog2 = new MyDialog2(getContext(),date);
                myDialog2.show();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                myDialog2.getWindow().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

                WindowManager.LayoutParams params = myDialog2.getWindow().getAttributes();
                params.height = (int)(displayMetrics.heightPixels*0.70);
                myDialog2.getWindow().setAttributes(params);

            }
        });
        setLongPressListener(new OnLongPressListener() {
            @Override
            public void onLongPress(MotionEvent event) {
                Intent intent = new Intent();
                date = new int[3];
                date = CalendarUtil.getCurrentDate(year,month,dateNum[dateCursorY][dateCursorX],dateCursorY,dateCursorX);
                intent.putExtra("year",date[0]);
                intent.putExtra("month",date[1]-1);
                intent.putExtra("dayOfMonth",date[2]);
                intent.setClass(context, AddEventActivity.class);
                context.startActivity(intent);
            }
        });
        Data.getInstance().addRefreshView(this);
    }

    public CalendarMonthView(Context context) {
        super(context);
        initUI(context);
    }

    public void setCursor(CalendarDay day) {
        cursor = day;
    }

    public void setDoubleClickListener(OnDoubleClickListener listener) {
        eventDoubleClickListener = listener;
    }
     public  void onEventClick(int dateNumX, int dateNumY,Boolean tf){
        dateCursorX = dateNumX;
        dateCursorY = dateNumY;
        isCursor = tf;

     }

     public void setSingleClick(Event event){
         Intent intent = new Intent();
         intent.setClass(getContext(), AddEventActivity.class);
         Bundle bundle = new Bundle();
         bundle.putParcelable(INTENT_EVENT, event);
         intent.putExtras(bundle);
         getContext().startActivity(intent);
     }
     public void setLongPressListener(OnLongPressListener listener){
        eventLongPressListener = listener;
     }

    /**
     * ?????????UI
     *
     * @param context
     */
    private void initUI(Context context) {
        // ???????????????
        year = CalendarUtil.getYear();
        month = CalendarUtil.getMonth();
        //setYearMonth(year,month);
        lunarYear = year;
        lunarMonth = month;
        calendarStates = new CalendarState[6][7];
        //drawCalendar = new DrawCalendar(year, month);

    }
    /**
     * ???????????????????????????????????????
     *
     * @param year
     * @param month
     */
    public void setYearMonth(int year, int month) {
        this.year = year;
        this.month = month;
        weekNum = getWeekNumOfMonth(year,month);
        //System.out.println("weekNum:"+weekNum);
        days = new MonthDay[weekNum*7];
        for (int i = 0;i < days.length;i++){
            days[i] = new MonthDay();
        }
        refreshMonthDayEvent(year,month,days);
        drawCalendar = new DrawCalendar(year, month);

        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // ??????????????????
        width = getMeasuredWidth();
        // ??????????????????
        height = getMeasuredHeight();
        //??????????????????
        weekHeight = height / 20.0f;
        // ????????????????????????
        dateNumWidth = width / 7.0f;
        //????????????????????????
        dateGapWidth = dateNumWidth/40.0f;


        eventHeight = (height - weekHeight)/30.0f;
        // ??????????????????
        dateNumHeight = (height - weekHeight) / 6.0f;

        dateNumHeighter =  (height - weekHeight) / 5.0f;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCalendar.drawCalendarCanvas(canvas);

    }

    protected void refreshMonthDayEvent(int year,int month,MonthDay[] monthDays){
        int tmpDays;
        dateNum = getMonthNumFromDate(year,month);
        ArrayList<Event> events = Data.getInstance().getEvents();
        Locale locale = getResources().getConfiguration().locale;
        ArrayList<Holiday> holidays;

        holidays = Data.getInstance().getHolidays(getContext(), locale.equals(Locale.CHINA) ? locale : Locale.TAIWAN);
        for (MonthDay monthDay : monthDays){
            monthDay.events.clear();
        }
        for (int i = 0;i < monthDays.length;i++){
            monthDays[i].daysCount = i;
            int[] tmpDate = getCurrentDate(year,month,dateNum[i/7][i%7],i/7,i%7);
            monthDays[i].set(tmpDate[0],tmpDate[1],dateNum[i/7][i%7]);
            for (int k = 0; k < holidays.size(); k++) {
                Holiday holiday = holidays.get(k);
                String[] time = holiday.getDate().split("-");
                int mYear = Integer.valueOf(time[0]);
                int mMonth = Integer.valueOf(time[1]) + 1;
                int dayOfMonth = Integer.valueOf(time[2]);
                if (monthDays[i].equal(mYear, mMonth, dayOfMonth)) {
                    monthDays[i].count++;
                    monthDays[i].isHoliday = true;
                    monthDays[i].events.add(new MonthDayEvent(holiday.getName(), holiday.getName(), -1));
                    //Log.i("Holiday:",holiday.getName()+holiday.getDate());
                }
            }

        }
        for (int i = 0;i < monthDays.length;i++){
                for (int j = 0;j < events.size();j++){
                    Event event = events.get(j);
                    tmpDays = checkEventTime(event,monthDays[i],j);
                    if (tmpDays > 0) {
                        for (int m = 1;m < tmpDays;m++){//add long event for the tmpDays
                            for (int n = monthDays[i+m].events.size();n<monthDays[i].count - 1;n++){
                                monthDays[i+m].events.add(n,null);
                                monthDays[i+m].count++;
                            }
                            monthDays[i+m].events.add(monthDays[i].count -1,new MonthDayEvent(event, "0", j));
                            monthDays[i+m].events.get(monthDays[i].count -1).isLongEvent = true;
                            //monthDays[i+m].isLongEvent[monthDays[i].count -1] = true;
                            monthDays[i+m].count++;
                        }
                        if (monthDays[i].count ==1){
                            for (int p = 0;p < tmpDays;p++){
                                if (monthDays[i+p].isHoliday){
                                    monthDays[i].isSigner = true;
                                }
                            }
                            if (monthDays[i].isSigner){
                                for (int s=0;s<tmpDays;s++){
                                    monthDays[s+i].isSigner = true;
                                }
                            }

                        }


                        }

                }

            //arrangeDayEvents(monthDays[i]);

        }


        for (int i = 0;i < monthDays.length;i++){
            if (monthDays[i].isSigner)
                //Log.i("isSigner:",String.valueOf(i));
            if (monthDays[i].events.size()!=0&&monthDays[i].isSigner&&!monthDays[i].isHoliday){
                monthDays[i].events.add(0,null);
                monthDays[i].count++;
                monthDays[i].isLongEvent[1] = false;
                if (monthDays[i].isLongEvent[0])
                monthDays[i].isLongEvent[1] = true;
                //Log.i("IsSigner:",String.valueOf(monthDays[i].daysCount));
            }
            //Log.i("monthDays[i]:",String.valueOf(monthDays[i].daysCount));
            for (int j = 0;j < monthDays[i].events.size();j++){
                if (monthDays[i].events.get(j)!= null)
                Log.i("everyDayEvent:",j + monthDays[i].events.get(j).title);
            }
        }

        invalidate();
    }
    private void arrangeDayEvents(MonthDay day) {
        if (day.events.size() != 0) {
            ArrayList<MonthDayEvent> events = day.events;
            Collections.sort(events);
        }
    }

    private int checkEventTime(Event event, MonthDay day, int order) {
        if (event.isNoTime() || !isEventTimeCorrect(event))
            return 0;
        int startStatus = checkStartTime(event, day);
        int endStatus = checkEndTime(event, day);
        if (startStatus != 1 && endStatus != -1) {
            if (startStatus == 0&&endStatus == 0){
                for (int i = 0;i < day.events.size();i++){
                    if (day.events.get(i) == null){
                        day.events.set(i,new MonthDayEvent(event, "0", order));
                        return 0;
                    }
                }
                day.events.add(new MonthDayEvent(event, "0", order));
                day.count++;
                return 0;
            }else{
                String eventStartDate = new StringBuilder().append(event.getStartYear()).append("-").append(event.getStartMonth()+1).append("-").append(event.getStartDayOfMonth()).toString();
                String eventEndDate = new StringBuilder().append(event.getEndYear()).append("-").append(event.getEndMonth()+1).append("-").append(event.getEndDayOfMonth()).toString();
                String eventDays = String.valueOf(dayDiff(eventEndDate,eventStartDate,"yyyy-MM-dd"));
                /*if (startStatus != 0&&day.count < 5)
                day.isLongEvent[day.count] = true;
                day.events.add(new MonthDayEvent(event, eventDays, order));
                day.count++;*/
                if (startStatus ==0){
                    int tmpDays = Integer.valueOf(eventDays) + 1 > 7 - day.daysCount%7 ? 7 - day.daysCount%7:Integer.valueOf(eventDays) + 1;
                    for (int i = 0;i < day.events.size();i++){
                        if (day.events.get(i) == null){
                            day.events.set(i,new MonthDayEvent(event, String.valueOf(tmpDays), order));
                            return tmpDays;
                        }
                    }
                    day.events.add(new MonthDayEvent(event, String.valueOf(tmpDays), order));
                    day.count++;
                    return tmpDays;
                    //Log.i("LongEvent:",event.getTitle()+"eventDays:"+eventDays);
                    //Log.i("eventStartDate:",eventStartDate+"eventEndDate:"+eventEndDate);
                }else if (day.daysCount%7 == 0) {
                    String eventCurrentDate = new  StringBuilder().append(day.year).append("-").append(day.month).append("-").append(day.dayOfMonth).toString();
                    int endToNowDays = Integer.valueOf(String.valueOf(dayDiff(eventEndDate, eventCurrentDate, "yyyy-MM-dd")))+1> 7 ? 7 : Integer.valueOf(String.valueOf(dayDiff(eventEndDate, eventCurrentDate, "yyyy-MM-dd")))+1;
                    for (int i = 0;i < day.events.size();i++){
                        if (day.events.get(i) == null){
                            day.events.set(i,new MonthDayEvent(event, String.valueOf(endToNowDays), order));
                            return endToNowDays;
                        }
                    }
                    day.events.add(new MonthDayEvent(event,String.valueOf(endToNowDays),order));
                    day.count++;
                    return endToNowDays;
                }else
                    return 0;
            }

        }else
            return 0;
    }

    private boolean isEventTimeCorrect(Event event) {
        Calendar s = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        s.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth(), event.getStartHour(), event.getStartMinute());
        e.set(event.getEndYear(), event.getEndMonth(), event.getEndDayOfMonth(), event.getEndHour(), event.getEndMinute());
        return e.after(s);
    }

    private int checkStartTime(Event event, MonthDay day) {
        int y = Integer.compare(event.getStartYear(), day.year);
        if (y == 0) {
            Calendar e = Calendar.getInstance();
            Calendar d = Calendar.getInstance();
            e.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth());
            d.set(day.year, day.month-1, day.dayOfMonth);
            int wE = e.get(Calendar.DAY_OF_YEAR);
            int wD = d.get(Calendar.DAY_OF_YEAR);
            return Integer.compare(wE, wD);
        } else
            return y;
    }

    private int checkEndTime(Event event, MonthDay day) {
        int y = Integer.compare(event.getEndYear(), day.year);
        if (y == 0) {
            Calendar e = Calendar.getInstance();
            Calendar d = Calendar.getInstance();
            e.set(event.getEndYear(), event.getEndMonth(), event.getEndDayOfMonth());
            d.set(day.year, day.month-1, day.dayOfMonth);
            int wE = e.get(Calendar.DAY_OF_YEAR);
            int wD = d.get(Calendar.DAY_OF_YEAR);
            return Integer.compare(wE, wD);
        } else
            return y;
    }

    public class MonthDayEvent implements Comparable<MonthDayEvent> {

        int color;
        Boolean isAllday;
        Boolean isLongEvent;
        public boolean isComplete = false;
        int startDate;
        int endDate;
        String time;
        String title;
        String eventId;
        int eventOrder;

        MonthDayEvent() {
        }

        MonthDayEvent(Event event, String time, int order) {
            this.color = event.getColor();
            this.time = time;
            this.isLongEvent = false;
            this.isComplete = event.isComplete();
            this.startDate = event.getStartDayOfMonth();
            this.endDate = event.getEndDayOfMonth();
            this.isAllday = event.isAllDay();
            this.title = event.getTitle();
            eventId = event.getObjectId();
            eventOrder = order;
        }

        MonthDayEvent(String title, String time, int order) {
            this.title = title;
            this.time = time;
            this.isLongEvent = false;
            this.eventOrder = order;
        }

        @Override
        public int compareTo(@NonNull MonthDayEvent o) {
                int mPriority = 0;
                int oPriority = 0;
                String oTime = o.time;
                if (time.equals(ALL_DAY))
                    mPriority = 4;
                else if (time.equals(HOLIDAY) || time.equals(OBSERVED) || time.equals(WORKDAY))
                    mPriority = 5;
                else if (time.startsWith("~"))
                    mPriority = 2;
                else if (time.contains(":"))
                    mPriority = 3;
                if (oTime.equals(ALL_DAY))
                    oPriority = 4;
                else if (oTime.equals(HOLIDAY) || oTime.equals(OBSERVED) || oTime.equals(WORKDAY))
                    oPriority = 5;
                else if (oTime.startsWith("~"))
                    oPriority = 2;
                else if (oTime.contains(":"))
                    oPriority = 3;
                if (oPriority == mPriority) {
                    String[] mTimeString;
                    String[] oTimeString;
                    int[] mTimeInt = new int[2];
                    int[] oTimeInt = new int[2];
                    switch (oPriority) {
                        case 2:
                            String mS = time.substring(1);
                            String oS = time.substring(1);
                            mTimeString = mS.split(":");
                            oTimeString = oS.split(":");
                            mTimeInt[0] = Integer.parseInt(mTimeString[0]);
                            mTimeInt[1] = Integer.parseInt(mTimeString[1]);
                            oTimeInt[0] = Integer.parseInt(oTimeString[0]);
                            oTimeInt[1] = Integer.parseInt(oTimeString[1]);
                            if (mTimeInt[0] != oTimeInt[0])
                                return oTimeInt[0] - mTimeInt[0];
                            return oTimeInt[1] - mTimeInt[1];
                        case 3:
                            mTimeString = time.split(":");
                            oTimeString = oTime.split(":");
                            mTimeInt[0] = Integer.parseInt(mTimeString[0]);
                            mTimeInt[1] = Integer.parseInt(mTimeString[1]);
                            oTimeInt[0] = Integer.parseInt(oTimeString[0]);
                            oTimeInt[1] = Integer.parseInt(oTimeString[1]);
                            if (mTimeInt[0] != oTimeInt[0])
                                return mTimeInt[0] - oTimeInt[0];
                            return mTimeInt[1] - oTimeInt[1];
                    }
                }
                return oPriority - mPriority;
        }
    }
    /*protected ArrayList<Event> loadEvents(int year,int month){
        //????????????3??????????????????
        int leMinYear = 0,leMinMonth = 0,leMaxYear = 0,leMaxMonth = 0;
        if (month == 1){
            leMinYear = year - 1;
            leMinMonth = 12;
            leMaxYear = year;
            leMaxMonth = month + 1;
        }else if (month == 12){
            leMinYear = year;
            leMinMonth = month - 1;
            leMaxYear = year + 1;
            leMaxMonth = 1;
        }else{
            leMaxYear = year;
            leMinYear = year;
            leMinMonth = month - 1;
            leMaxMonth = month + 1;
        }
        int [][] dateNumLast = getMonthNumFromDate(leMinYear, leMinMonth);
        int [][] dateNumNext = getMonthNumFromDate(leMaxYear, leMaxMonth);
        int weekNumNext = getWeekNumOfMonth(leMaxYear,leMaxMonth);
        ArrayList<Event> monthEvents = new ArrayList<>();
        ArrayList<Event> events = Data.getInstance().getEvents();
        int[] tmp1 = getCurrentDate(leMinYear,leMinMonth,dateNumLast[0][0],0,0);
        int[] tmp2 = getCurrentDate(leMaxYear,leMaxMonth,dateNumNext[weekNumNext-1][6],weekNumNext-1,6);
        String firstDateOfMonth = new StringBuilder().append(tmp1[0]).append("-").append(tmp1[1]).append("-").append(tmp1[2]).toString();
        String endDateOfMonth = new StringBuilder().append(tmp2[0]).append("-").append(tmp2[1]).append("-").append(tmp2[2]).toString();
        //System.out.println("firstDateOfMonth"+firstDateOfMonth);
        //System.out.println("endDateOfMonth"+endDateOfMonth);
        for (Event event : events){
            String eventStartDate = new StringBuilder().append(event.getStartYear()).append("-").append(event.getStartMonth()).append("-").append(event.getStartDayOfMonth()).toString();
            String eventEndDate = new StringBuilder().append(event.getEndYear()).append("-").append(event.getEndMonth()).append("-").append(event.getEndDayOfMonth()).toString();
            //System.out.print("monthEvents:start");
            //System.out.println(dayDiff(eventStartDate,firstDateOfMonth,"yyyy-MM-dd"));
            //System.out.println(dayDiff(endDateOfMonth,eventStartDate,"yyyy-MM-dd"));
            if (dayDiff(eventStartDate,firstDateOfMonth,"yyyy-MM-dd") >= 0&&dayDiff(endDateOfMonth,eventStartDate,"yyyy-MM-dd") >= 0){
                //System.out.print("monthEvents:start00");
                //System.out.println(event.getTitle());
                monthEvents.add(event);
            }

        }
        return monthEvents;
    }*/


    /**
     * ????????????????????????????????????
     *
     *
     *
     */
    class DrawCalendar {

        /**????????????????????????*/
        private Paint mPaintLine;
        /**???????????????????????????*/
        private Paint mPaintWeekLine;
        /**??????????????????*/
        private Paint mPaintDeleteLine;
        private Paint mPaintWeek;
        /** ?????????????????? */
        private Paint mPaintText;
        /** ????????????????????? ??????????????? */
        private Paint mPaintCircle;
        /** ???????????? */
        private float fontHeight;
        /**??????????????????*/
        private Paint mLunerPaintText;
        /** ????????????*/
        private Paint mPaintCursor;
        /*????????????*/
        private  Paint mPaintHoliday;

        /*??????????????????*/
        private Paint mPaintHolidayText;

        /*??????????????????*/
        private  Paint mPaintEventText;

        private  Paint mPaintEventRect;

        private  Paint mPainEventNumText;
        //??????????????????
        private float fontLunarHeight;

        //private float fontEventHeight;

        private int[] lunar;


        private int maxItems;


        @SuppressLint("ResourceAsColor")
        public DrawCalendar(int year, int month) {

            // ??????????????????????????????
            dateNum = getMonthNumFromDate(year, month);
            weekNum = getWeekNumOfMonth(year,month);


                    //??????????????????????????????
            mPaintWeek = new Paint();
            mPaintWeek.setTextSize(28);
            mPaintWeek.setColor(Color.GRAY);
            mPaintWeek.setAntiAlias(true);// ??????????????????????????????
            // ??????????????????????????????
            mPaintText = new Paint();
            mPaintText.setTextSize(28);
            mPaintText.setColor(Color.GRAY);// ????????????
            mPaintText.setAntiAlias(true);// ??????????????????????????????
            // ??????????????????
            Paint.FontMetrics fm = mPaintText.getFontMetrics();
            fontHeight = (float) Math.ceil(fm.descent - fm.top) / 2;

            //???????????????????????????
            mLunerPaintText = new Paint();
            mLunerPaintText.setTextSize(25);
            mLunerPaintText.setColor(Color.LTGRAY);
            mLunerPaintText.setAntiAlias(true);
            //????????????????????????
            Paint.FontMetrics fmLuner = mLunerPaintText.getFontMetrics();
            fontLunarHeight = (float) Math.ceil(fmLuner.descent - fmLuner.top) / 2;

            //???????????????
            mPaintLine = new Paint();
            mPaintLine.setColor(getResources().getColor(R.color.colorCalendarLine));
            mPaintLine.setStyle(Paint.Style.STROKE);
            mPaintLine.setStrokeWidth(1f);

            mPaintWeekLine = new Paint();
            mPaintWeekLine.setColor(Data.getInstance().getThemeColor());
            mPaintWeekLine.setStyle(Paint.Style.STROKE);
            mPaintWeekLine.setStrokeWidth(3f);

            mPaintDeleteLine = new Paint();
            mPaintDeleteLine.setColor(Color.BLACK);
            mPaintDeleteLine.setStyle(Paint.Style.STROKE);
            mPaintDeleteLine.setStrokeWidth(3f);

            // ?????????????????????????????????
            mPaintCircle = new Paint();
            mPaintCircle.setStyle(Paint.Style.FILL);
            mPaintCircle.setStrokeWidth(2);
            mPaintCircle.setColor(Color.LTGRAY);
            mPaintCircle.setAntiAlias(true);// ??????????????????????????????

            //????????????????????????????????????
            mPaintCursor = new Paint();
            mPaintCursor.setStyle(Paint.Style.FILL);
            mPaintCursor.setStrokeWidth(2);
            mPaintCursor.setColor(getResources().getColor(R.color.colorCalendarLine));
            mPaintCursor.setAntiAlias(true);

            //???????????????????????????
            mPaintHoliday = new Paint();
            mPaintHoliday.setAntiAlias(true);
            mPaintHoliday.setStyle(Paint.Style.FILL);
            mPaintHoliday.setColor(Color.RED);

            /*???????????????????????????*/
            mPaintHolidayText = new Paint();
            mPaintHolidayText.setColor(Color.WHITE);
            mPaintHolidayText.setTextSize(25);
            mPaintHolidayText.setAntiAlias(true);

            /*???????????????????????????*/
            mPaintEventText = new Paint();
            mPaintEventText.setColor(Color.WHITE);
            mPaintEventText.setTextSize(25);
            mPaintEventText.setAntiAlias(true);
            mPaintEventText.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fmEvent = mPaintEventText.getFontMetrics();
            //fontEventHeight = (float) Math.ceil(fmEvent.descent - fmEvent.top) / 2;

            /*????????????????????????*/
            mPaintEventRect = new Paint();
            mPaintEventRect.setStyle(Paint.Style.FILL);
            mPaintEventRect.setStrokeWidth(2);
            mPaintEventRect.setColor(getResources().getColor(R.color.colorCalendarLine));
            mPaintEventRect.setAntiAlias(true);

            mPainEventNumText = new Paint();
            mPainEventNumText.setColor(Color.WHITE);
            mPainEventNumText.setTextSize(20);
            mPainEventNumText.setAntiAlias(true);
        }

        /**
         * ????????????
         *
         * @param canvas
         */
        public void drawCalendarCanvas(Canvas canvas) {

            maxItems = weekNum > 5 ? 2 : 3;

            if (isCursor) {
                canvas.drawRect(dateNumWidth * dateCursorX,
                        weekHeight + dateNumHeight * dateCursorY, dateNumWidth * (dateCursorX + 1), weekHeight + dateNumHeight * (dateCursorY + 1),
                        mPaintCursor);
            }
            if (weekNum < 6){
                dateNumHeight = dateNumHeighter;
            }
                       //????????????
            for(int i = 0;i < 7; i++ ){
                if(i==0){
                    mPaintWeek.setColor(Color.RED);
                }else{
                    if (i==6){
                        mPaintWeek.setColor(Color.BLUE);
                    }
                    else{
                        mPaintWeek.setColor(Color.GRAY);
                    }
                }
                canvas.drawText(weeks[i],dateNumWidth * i + dateNumWidth
                                / 2 - mPaintWeek.measureText(weeks[i] + "") / 2,
                         weekHeight/2.0f + fontHeight / 2.0f,mPaintWeek);
                //Log.i(TAG, "drawCalendarCanvas: i="+i);
            }

            //ArrayList<Event> monthEvent = loadEvents(year,month);
            /*if (monthEvent != null){
                for (Event event : monthEvent){
                    //System.out.println("montEvent:");
                    //System.out.println(event.getTitle());
                    //System.out.println(event.getStartYear());
                    //System.out.println(event.getStartMonth());
                    //System.out.println(event.getStartDayOfMonth());
                }
            }*/


            /*ArrayList<Holiday> holidays;
            Locale locale = getResources().getConfiguration().locale;
            holidays = Data.getInstance().getHolidays(getContext(),locale.equals(Locale.CHINA) ? locale : Locale.TAIWAN);
*/

            //weekLine draw
            for (int i = 0; i < weekNum+1; i++){
                canvas.drawLine(0,weekHeight + dateNumHeight*i,width,weekHeight +dateNumHeight*i,mPaintLine);
            }

            //????????????
            for (int i = 0; i < weekNum; i++) {

                for (int j = 0; j < dateNum[i].length; j++) {
                    if (i == 0 && dateNum[i][j] > 20) {// ??????????????????
                        drawCalendarCell(i, j, CalendarState.LAST_MONTH,
                                canvas);
                    } else if ((i == 5 || i == 4) && dateNum[i][j] < 20) {// ??????????????????
                        drawCalendarCell(i, j, CalendarState.NEXT_MONTH,
                                canvas);
                    } else {// ????????????
                        if (dateNum[i][j] == CalendarUtil.getCurrentMonthDay()) {// ???????????????????????????
                            if (year == CalendarUtil.getYear()
                                    && month == CalendarUtil.getMonth()) {// ?????????????????????
                                drawCalendarCell(i, j, CalendarState.TODAY,
                                        canvas);
                                canvas.drawLine(0,weekHeight + dateNumHeight*i,width,weekHeight +dateNumHeight*i,mPaintWeekLine);
                            }
                            drawCalendarCell(i, j, CalendarState.CURRENT_MONTH,
                                    canvas);
                        } else {
                            drawCalendarCell(i, j, CalendarState.CURRENT_MONTH,
                                    canvas);
                        }
                    }
                }
            }

            //????????????
            for (int i = 0;i < weekNum;i++){

                for (int j = 0;j < dateNum[i].length;j++){
                     int count = 0;
                     int eventNum = days[i*7+j].events.size();
                    eventNum = eventNum > maxItems ? maxItems : eventNum;
                    for (int m = 0;m < eventNum;m++){
                        MonthDayEvent event = days[i*7+j].events.get(m);
                        //Log.i("event:",m + event.title);
                        if (event == null){
                            count++;
                        }else{
                            if (event.eventOrder == -1){ //???????????????
                                if (getWordCount(event.title) < 10){
                                    if (event.title.equals("Workday") ||event.title.equals("?????????"))
                                        mPaintHoliday.setColor(Color.BLACK);
                                    mPaintHolidayText.setTextAlign(Paint.Align.CENTER);
                                    canvas.drawRoundRect(dateNumWidth*j+dateGapWidth,weekHeight + dateNumHeight*i+weekHeight+dateGapWidth,dateNumWidth*(j+1) - dateGapWidth,weekHeight + dateNumHeight*i+weekHeight+eventHeight-dateGapWidth,5,5,mPaintHoliday);
                                    canvas.drawText(event.title,dateNumWidth*j + dateNumWidth/2,weekHeight+dateNumHeight*i+weekHeight+eventHeight/2 + eventHeight/4,mPaintHolidayText);
                                    mPaintHoliday.setColor(Color.RED);
                                }else{
                                    mPaintHolidayText.setTextAlign(Paint.Align.LEFT);
                                    canvas.save();
                                    canvas.clipRect(dateNumWidth*j,weekHeight + dateNumHeight*i+weekHeight+dateGapWidth,dateNumWidth*(j+1) - dateGapWidth,weekHeight + dateNumHeight*i+weekHeight+eventHeight-dateGapWidth);
                                    canvas.drawRoundRect(dateNumWidth*j,weekHeight + dateNumHeight*i+weekHeight+dateGapWidth,dateNumWidth*(j+1) - dateGapWidth,weekHeight + dateNumHeight*i+weekHeight+eventHeight-dateGapWidth,5,5,mPaintHoliday);
                                    canvas.drawText(event.title,dateNumWidth*j,weekHeight+dateNumHeight*i+weekHeight+eventHeight/2 + eventHeight/4,mPaintHolidayText);
                                    canvas.restore();
                                }
                            }else if (m < eventNum + 1){
                                if (Integer.valueOf(event.time) > 0){//???????????????
                                    if (m==0&&days[i*7+j].isSigner){

                                    }else{
                                        //System.out.println("longEvent:"+ j + ":" + event.title);
                                        int longEvent = Integer.valueOf(event.time);

                                        //Log.i("longEvent:===796=====",String.valueOf(dateNumWidth*j + longEvent*dateNumWidth/2)+event.title+String.valueOf(longEvent*dateNumWidth/2));
                                        mPaintEventRect.setColor(event.color);
                                        mPaintEventText.setColor(Color.WHITE);
                                        mPaintEventText.setTextAlign(Paint.Align.CENTER);
                                        canvas.save();
                                        canvas.clipRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + dateGapWidth, dateNumWidth * (j + longEvent) - dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m + 1) * eventHeight - dateGapWidth);
                                        canvas.drawRoundRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + dateGapWidth, dateNumWidth * (j + longEvent) - dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m + 1) * eventHeight - dateGapWidth, 5, 5, mPaintEventRect);
                                        canvas.drawText(event.title, dateNumWidth*j + longEvent*dateNumWidth/2, weekHeight + dateNumHeight * i +weekHeight + eventHeight/2 + eventHeight/4+ (m) * eventHeight, mPaintEventText);
                                        if (event.isComplete){
                                            //Log.i("------isComplete",String.valueOf(event.isComplete));
                                            canvas.drawLine(dateNumWidth*j + longEvent*dateNumWidth/2-mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + eventHeight/2 + (m) * eventHeight,dateNumWidth*j + longEvent*dateNumWidth/2+mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + eventHeight/2 + (m) * eventHeight,mPaintDeleteLine);
                                        }
                                        canvas.restore();
                                    }

                                }else{
                                    //System.out.println("event.time"+ event.time);
                                    //System.out.println("event:Allday;"+event.title);
                                    if (event.isAllday) {//all day event
                                        if (getWordCount(event.title) < 10) {
                                            mPaintEventText.setTextAlign(Paint.Align.CENTER);
                                            mPaintEventText.setColor(Color.WHITE);
                                            mPaintEventRect.setColor(event.color);
                                            canvas.drawRoundRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m ) * eventHeight + dateGapWidth, dateNumWidth * (j + 1) - dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m+1) * eventHeight - dateGapWidth, 5, 5, mPaintEventRect);
                                            canvas.drawText(event.title, dateNumWidth * j + dateNumWidth / 2, weekHeight + dateNumHeight * i + weekHeight + (m ) * eventHeight + eventHeight/2 + eventHeight/4, mPaintEventText);
                                            if (event.isComplete){
                                                canvas.drawLine(dateNumWidth*j + dateNumWidth/2-mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,dateNumWidth*j + dateNumWidth/2+mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,mPaintDeleteLine);
                                            }
                                        } else {
                                            mPaintEventText.setTextAlign(Paint.Align.LEFT);
                                            mPaintEventText.setColor(Color.WHITE);
                                            mPaintEventRect.setColor(event.color);
                                            canvas.save();
                                            canvas.clipRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + m * eventHeight + dateGapWidth, dateNumWidth * (j + 1) - dateGapWidth, weekHeight + dateNumHeight * i +weekHeight+ (m + 1) * eventHeight - dateGapWidth);
                                            canvas.drawRoundRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + m * eventHeight + dateGapWidth, dateNumWidth * (j + 1) - dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + (m + 1) * eventHeight - dateGapWidth, 5, 5, mPaintEventRect);
                                            canvas.drawText(event.title, dateNumWidth * j, weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2 + eventHeight/4, mPaintEventText);
                                            if (event.isComplete){
                                                canvas.drawLine(dateNumWidth*j + dateNumWidth/2-mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,dateNumWidth*j + dateNumWidth/2+mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,mPaintDeleteLine);
                                            }
                                            canvas.restore();

                                        }
                                    }else if (!event.isLongEvent){// not -allDay event
                                        if (getWordCount(event.title) < 10){
                                            mPaintEventText.setTextAlign(Paint.Align.CENTER);
                                            mPaintEventText.setColor(event.color);
                                            canvas.drawText(event.title,dateNumWidth*j + dateNumWidth/2,weekHeight+dateNumHeight*i+weekHeight+(m)*eventHeight+eventHeight/2 + eventHeight/4,mPaintEventText);
                                            if (event.isComplete){
                                                canvas.drawLine(dateNumWidth*j + dateNumWidth/2-mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,dateNumWidth*j + dateNumWidth/2+mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight+ (m) * eventHeight + eventHeight/2,mPaintDeleteLine);
                                            }
                                        }else{
                                            mPaintEventText.setTextAlign(Paint.Align.LEFT);
                                            mPaintEventText.setColor(event.color);
                                            canvas.save();
                                            canvas.clipRect(dateNumWidth * j + dateGapWidth, weekHeight + dateNumHeight * i + weekHeight + m * eventHeight + dateGapWidth, dateNumWidth * (j + 1) - dateGapWidth, weekHeight + dateNumHeight * i + weekHeight+ (m + 1) * eventHeight - dateGapWidth);
                                            canvas.drawText(event.title,dateNumWidth*j,weekHeight+dateNumHeight*i+weekHeight+(m)*eventHeight+eventHeight/2 + eventHeight/4,mPaintEventText);
                                            if (event.isComplete){
                                                canvas.drawLine(dateNumWidth*j + dateNumWidth/2-mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight + (m) * eventHeight + eventHeight/2,dateNumWidth*j + dateNumWidth/2+mPaintDeleteLine.measureText(event.title),weekHeight + dateNumHeight * i + weekHeight+ (m) * eventHeight + eventHeight/2,mPaintDeleteLine);
                                            }
                                            canvas.restore();
                                        }


                                    }
                                }
                            }
                        }
                    }
                    //??????????????????events numbers
                    if (days[i*7+j].count - count > maxItems){
                        Path pathIcon = new Path();
                        pathIcon.moveTo(dateNumWidth*(j+1)-fontLunarHeight*3,weekHeight+dateNumHeight*(i+1)-dateGapWidth/2);
                        pathIcon.lineTo(dateNumWidth*(j+1),weekHeight+dateNumHeight*(i+1)-fontLunarHeight*3);
                        pathIcon.lineTo(dateNumWidth*(j+1),weekHeight+dateNumHeight*(i+1)-dateGapWidth/2);
                        pathIcon.close();
                        mPaintEventRect.setColor(Color.LTGRAY);
                        canvas.drawPath(pathIcon,mPaintEventRect);
                        canvas.drawText("+" + String.valueOf(days[i*7+j].count-maxItems -count),dateNumWidth*(j+1)-fontLunarHeight*3/2,weekHeight+dateNumHeight*(i+1)-fontLunarHeight/2,mPainEventNumText);
                    }
                }
            }


        }

        /**
         * ??????????????????
         *
         * @param i
         *            ?????????
         * @param j
         *            ?????????
         * @param state
         *            ??????
         * @param canvas
         *            ??????
         */
        private void drawCalendarCell(int i, int j, CalendarState state,
                                      Canvas canvas) {
            switch (state) {
                case TODAY:// ??????
                    lunarYear = year;
                    lunarMonth = month;
                    calendarStates[i][j] = CalendarState.TODAY;
                    //mPaintText.setColor(Color.WHITE);
                    if(j==0){
                        mPaintText.setColor(Color.RED);
                    }else{
                        if (j==6){
                            mPaintText.setColor(Color.BLUE);
                        }
                        else{
                            mPaintText.setColor(Color.BLACK);
                        }
                    }
                    canvas.drawRect(dateNumWidth * j ,
                            weekHeight + dateNumHeight * i , dateNumWidth * (j+1) ,weekHeight + dateNumHeight * i + weekHeight ,
                            mPaintCircle);

                   // Log.i(TAG, "drawCalendarCell: drawRect:i j "+i+j);
                    break;
                case CURRENT_MONTH:// ??????
                    lunarYear = year;
                    lunarMonth = month;
                    calendarStates[i][j] = CalendarState.CURRENT_MONTH;
                    if(j==0){
                        mPaintText.setColor(Color.RED);
                    }else{
                        if (j==6){
                            mPaintText.setColor(Color.BLUE);
                        }
                        else{
                            mPaintText.setColor(Color.BLACK);
                        }
                    }
                    break;
                case LAST_MONTH:// ??????
                    if (month == 1){
                        lunarYear = year - 1;
                        lunarMonth = 12;
                    }else{
                        lunarMonth = month - 1;
                    }
                    calendarStates[i][j] = CalendarState.LAST_MONTH;
                    if(j==0){
                        mPaintText.setColor(Color.rgb(250,128,114));
                    }else{
                        if (j==6){
                            mPaintText.setColor(Color.rgb(61,89,171));
                        }
                        else{
                            mPaintText.setColor(Color.GRAY);
                        }
                    }
                    break;
                case NEXT_MONTH://??????
                    if (month == 12){
                        lunarYear = year + 1;
                        lunarMonth = 1;
                    }else{
                        lunarMonth = month + 1;
                    }
                    calendarStates[i][j] = CalendarState.LAST_MONTH;
                    if(j==0){
                        mPaintText.setColor(Color.rgb(250,128,114)); //??????
                    }else{
                        if (j==6){
                            mPaintText.setColor(Color.rgb(61,89,171));//??????
                        }
                        else{
                            mPaintText.setColor(Color.GRAY);
                        }
                    }
                    break;
                default:
                    break;
            }


            // ????????????
            canvas.drawText(dateNum[i][j] + "", dateNumWidth * j + dateNumWidth
                            / 2 - mPaintText.measureText(dateNum[i][j] + "") / 2,
                    weekHeight + dateNumHeight * i  + weekHeight / 2.0f + fontHeight / 2.0f,
                    mPaintText);
            //??????????????????
            lunar = solarToLunar(lunarYear,lunarMonth,dateNum[i][j]);
            canvas.drawText(lunar[1]+"."+lunar[2],dateNumWidth * j + dateNumWidth
                    / 2 - mLunerPaintText.measureText(lunar[1]+"."+lunar[2] + "")/2, weekHeight + dateNumHeight *(i+1) - fontLunarHeight/1.0f,mLunerPaintText);

        }
    }



    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            //????????????
            float touchX = e.getX();
            float touchY = e.getY();
            int dateNumX = (int) (touchX / dateNumWidth);
            int dateNumY = (int) ((touchY-weekHeight) / dateNumHeight);
            int eventNumY = (int) ((touchY-dateNumY*dateNumHeight-weekHeight-weekHeight)/eventHeight);
            cursor.set(days[dateNumY*7+dateNumX]);
            onEventClick(dateNumX,dateNumY,true);
            if (days[dateNumY*7+dateNumX].events.size()>eventNumY){
                ArrayList<Event> allEvents = Data.getInstance().getEvents();
                for (int k = 0; k < allEvents.size(); k++) {
                    Event event = allEvents.get(k);
                    if (days[dateNumY*7+dateNumX].events.get(eventNumY)!=null&&days[dateNumY*7+dateNumX].events.get(eventNumY).eventId.equals(event.getObjectId())) {
                        setSingleClick(event);
                    }
                }
            }
            invalidate();
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            //????????????
            float touchX = e.getX();
            float touchY = e.getY();
            int dateNumX = (int) (touchX / dateNumWidth);
            int dateNumY = (int) ((touchY-weekHeight) / dateNumHeight);
            onEventClick(dateNumX,dateNumY,true);
            eventDoubleClickListener.onDoubleClick(e);
            invalidate();
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            float touchX = e.getX();
            float touchY = e.getY();
            int dateNumX = (int) (touchX / dateNumWidth);
            int dateNumY = (int) ((touchY-weekHeight) / dateNumHeight);
            onEventClick(dateNumX,dateNumY,true);
            eventLongPressListener.onLongPress(e);
            invalidate();
            super.onLongPress(e);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    public interface OnDoubleClickListener{
        void onDoubleClick(MotionEvent event);
    }

    public interface OnLongPressListener{
        void onLongPress(MotionEvent event);
    }
}