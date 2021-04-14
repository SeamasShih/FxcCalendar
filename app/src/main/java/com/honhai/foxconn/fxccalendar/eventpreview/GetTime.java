package com.honhai.foxconn.fxccalendar.eventpreview;

import android.util.Log;
import android.widget.Switch;

import com.honhai.foxconn.fxccalendar.data.Event;

import java.util.ArrayList;
import java.util.Calendar;

public class GetTime {
    ArrayList<Event> events;
    Event event;
    Calendar start = Calendar.getInstance();
    Calendar end = Calendar.getInstance();
    int mystart;
    int myend;
    int now;
    private Calendar mnow = Calendar.getInstance();
    String startDay;//获取开始时间
    String endDay;
    String starthour,startminute,endhour,endminute;
    int [] startStr, endStr ;
    boolean isAllday = false;

    Calendar start1 = Calendar.getInstance();
    Calendar end1 = Calendar.getInstance();

    String starttime;
    String endtime;

    public GetTime(ArrayList<Event> events){
        this.events = events;
    }
int n = 0;
    public GetTime(Event event, Calendar now){
        this.event = event;
        this.mnow = now;
//        Log.e("行不行啊小老弟2",""+mnow.get(Calendar.YEAR) + mnow.get(Calendar.MONTH)+(mnow.get(Calendar.DAY_OF_MONTH))+":"
//                +event.getStartYear()+event.getStartMonth()+ event.getStartDayOfMonth()+"kanknajici"+n++);

        initTime();
    }


    public ArrayList<Event> getSomedayTime(int year, int month, int day){
        ArrayList<Event> myEvents = new ArrayList<>();

        for(int i = 0; i<events.size(); i++) {
            mystart = events.get(i).getStartYear()*10000 +
                    events.get(i).getStartMonth()*100 + events.get(i).getStartDayOfMonth();
            myend = events.get(i).getEndYear()*10000 +
                    events.get(i).getEndMonth()*100 + events.get(i).getEndDayOfMonth();
            now = year*10000 + month*100 + day;

            if(now >= mystart && now <= myend){
                myEvents.add(events.get(i));
            }
        }
        return myEvents;
    }

    public String getStartDay(){
        return starttime;
    }

    public String getEndDay(){
        return endtime;
    }

    public boolean getIsAllday() {
        return isAllday;
    }

    public void initTime(){
        starthour = event.getStartHour()<10 ? ("0"+event.getStartHour()) : ""+event.getStartHour();
        startminute = event.getStartMinute()<10 ? ("0"+event.getStartMinute()) : ""+event.getStartMinute();
        endhour = event.getEndHour()<10 ? ("0"+event.getEndHour()) : ""+event.getEndHour();
        endminute = event.getEndMinute()<10 ? ("0"+event.getEndMinute()) : ""+event.getEndMinute();

        if (isContinuous()){
            switch(whichPosition()){
                case -1:
                    starttime =  starthour+":"+startminute;
                    endtime = "00:00";
                    break;
                case 0:
                    starttime = "";
                    endtime = "";
                    break;
                case 1:
                    starttime = "00:00";
                    endtime = endhour+":"+endminute;
                    break;
                    default:
                        break;
            }
        }else {
            starttime = starthour+":"+startminute;
            endtime = endhour+":"+endminute;
        }
    }

//判断某一天在连续事件中的位置，-1 是第一个，0 是中间，1 是最后
    private int whichPosition() {
        startStr = new int[]{event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth()};
        endStr = new int[]{event.getEndYear(), event.getEndMonth(), event.getEndDayOfMonth()};
//        start1.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth());
//        end1.set(event.getEndYear(), event.getEndMonth(), event.getEndDayOfMonth());
//        Log.e("行不行啊小老弟2",""+mnow.get(Calendar.YEAR) + mnow.get(Calendar.MONTH)+(mnow.get(Calendar.DAY_OF_MONTH))+":"
//                +event.getStartYear()+event.getStartMonth()+ event.getStartDayOfMonth());
        if(
                startStr[0] == mnow.get(Calendar.YEAR) && startStr[1] == mnow.get(Calendar.MONTH)
            && startStr[2] == mnow.get(Calendar.DAY_OF_MONTH)
//                start1.equals(mnow)
                ){
            return -1;

        }else if(
                endStr[0] == mnow.get(Calendar.YEAR) && endStr[1] == mnow.get(Calendar.MONTH)
                        && endStr[2] == mnow.get(Calendar.DAY_OF_MONTH)
                ){

            return 1;
        }else{

            isAllday =true;
            return 0;
        }
    }

    private boolean isContinuous() {
        if (!(event.getStartDayOfMonth() == event.getEndDayOfMonth() &&
                event.getStartMonth() == event.getEndMonth() &&
                event.getStartYear() == event.getEndYear()))
        {
            return true;
        }else {
        return false;
        }
    }

//    ArrayList<Event> eventsOfDay = getDayEvent(times,now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH));
//
//             start1.set(event.getStartYear(), event.getStartMonth(), event.getStartDayOfMonth());
//            end1.set(event.getEndYear(), event.getEndMonth(), event.getEndDayOfMonth());


//        start.set(events.get(i).
//
//    getStartYear(),events.
//
//    get(i).
//
//    getStartMonth(),events.
//
//    get(i).
//
//    getStartDayOfMonth());
//        end.set(events.get(i).
//
//    getEndYear(),events.
//
//    get(i).
//
//    getEndMonth(),events.
//
//    get(i).
//
//    getEndDayOfMonth());
//        if(!end.equals(start))
//
//    {
//        long sd = start.getTime().getTime();
//        long ed = end.getTime().getTime();
//        number = Integer.parseInt(String.valueOf((ed - sd) / (1000 * 60 * 60 * 24)));//获取相差天数
//
//    }
}