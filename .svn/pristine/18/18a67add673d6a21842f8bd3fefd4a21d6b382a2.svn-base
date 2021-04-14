package com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.AddEventActivity;
import com.honhai.foxconn.fxccalendar.colormanager.ColorImageView;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.Event;
import com.honhai.foxconn.fxccalendar.data.Group;
import com.honhai.foxconn.fxccalendar.eventpreview.GetTime;

import java.util.Calendar;
import java.util.List;

import static com.honhai.foxconn.fxccalendar.main.MainActivity.INTENT_EVENT;


public class GroupTestAdapter extends RecyclerView.Adapter<GroupTestAdapter.ViewHolder> {

    private Context context;
    private List<Event> list;
    private OnItemClickListener mOnItemClickListener;
    private Calendar mynow = Calendar.getInstance();
    int day;
    int month;
    int year;
    private String objectId;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView_up;
        TextView textView_down;
        TextView data;
        RelativeLayout RL ;
        ColorImageView colorImageView;
        ImageView alarm;
        ImageView groupImg;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text_data);
            textView_up = view.findViewById(R.id.textView5);
            textView_down = view.findViewById(R.id.textView6);
            data = view.findViewById(R.id.textView4);
            RL = view.findViewById(R.id.day_event);
            colorImageView  = view.findViewById(R.id.imageView5);
            alarm = view.findViewById(R.id.alarm);
            groupImg = view.findViewById(R.id.imageView3);
        }
    }

    public GroupTestAdapter(Context context, List<Event> list, Calendar now) {
        this.context = context;
        this.list = list;
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_MONTH);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int itemViewId = R.layout.item_test;
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(itemViewId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        Log.e("nihaia","nihaonihao");
        String endTime,startTime;
        Event myevent = list.get(position);
//        Log.e("年+月+日",""+year+" "+month+" "+day);
        mynow.set(year, month, day);
        GetTime now = new GetTime(list.get(position), mynow);
        endTime = now.getEndDay();
        startTime = now.getStartDay();

        holder.textView_down.setText(endTime);
        holder.textView_up.setText(startTime);
        holder.data.setText(myevent.getTitle()+"");
        Log.e("看看groupId",myevent.getGroupId()+"");
        if (myevent.isComplete()){
            holder.data.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.data.getPaint().setAntiAlias(true);
        }
        if (Data.getInstance().isRemind(myevent)){
            holder.alarm.setVisibility(View.VISIBLE);
        }
        holder.colorImageView.setColor(myevent.getColor());

        for (Group group : Data.getInstance().getGroups()){
            if (group.getGroupId() == (myevent.getGroupId())) {
                objectId = group.getObjectId();
            }
        }
        Data.getInstance().loadImageByGlide(holder.groupImg, objectId);
//// 9是軟體總的，10是sw1，11是2課，12是sqa
//        switch (myevent.getGroupId()){
//            case 9:
//                holder.groupImg.setImageResource(R.drawable.group11);
//                break;
//            case 10:
//                break;
//            case 11:
//                break;
//                default:
//                    break;
//        }




        if(myevent.isAllDay() || now.getIsAllday()){
            holder.textView_up.setText("");
            holder.textView_down.setText("");
            holder.textView.setText(R.string.Allday);
            holder.textView.setVisibility(View.VISIBLE);
        }

        holder.RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putParcelable(INTENT_EVENT, list.get(position));
                intent.putExtras(bundle);
                intent.setClass(context, AddEventActivity.class);
                context.startActivity(intent);
            }
        });

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}