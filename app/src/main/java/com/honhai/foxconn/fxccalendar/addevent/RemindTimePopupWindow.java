package com.honhai.foxconn.fxccalendar.addevent;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

public class RemindTimePopupWindow extends PopupWindow {

    public interface ResultHandeler {
        /**
         * 回调函数
         */
        void handel(int reminderValue);
    }

    private ResultHandeler handeler;
    private Context context;

    private RecyclerView recyclerView;
    private AddEventActivity addEventActivity;
    private String[] data;
    private Adapter adapter;
    private TextView remindSelect;
    private int selects = -1;
    private boolean isOutSideTouch = true;
    private int color;


    RemindTimePopupWindow(Context context, ResultHandeler resultHandeler, View popupLayout1, int reminderType, int color, int w, int h) {
        super(popupLayout1, w, h);
        this.handeler = resultHandeler;
        this.context = context;
        this.color = color;
        addEventActivity = (AddEventActivity) context;
        data = context.getResources().getStringArray(R.array.stringRemind);
        recyclerView = popupLayout1.findViewById(R.id.popup_window_remind_time);
        remindSelect = popupLayout1.findViewById(R.id.remind_time_title);
        selects = reminderType;
        adapter = new Adapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(popupLayout1.getContext()));
    }

    public String getReminderString(int reminderType) {
        return data[reminderType];
    }

    public boolean isOutSideTouch() {
        return isOutSideTouch;
    }

    public void setOutSideTouch(boolean outSideTouch) {
        isOutSideTouch = outSideTouch;
    }

    public class Adapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.add_event_alarm_time_recycler_item, viewGroup, false);
            return new MyViewHolder(v);
        }


        @Override
        public int getItemCount() {
            return data.length;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int position) {
            viewHolder.mTextView.setText(data[position]);
            viewHolder.mCheckBox.setChecked(selects == position);
            viewHolder.mCheckBox.setButtonTintList(createColorStateList(color, context.getResources().getColor(R.color.colorPopupSwitchCalendarText)));
            viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (selects != position) {
                        handeler.handel(position);
                        MyViewHolder vh = (MyViewHolder) recyclerView.findViewHolderForAdapterPosition(selects);
                        if (vh != null)
                            vh.mCheckBox.setChecked(false);
                        selects = position;
                        remindSelect.setText(data[position]);
                        notifyDataSetChanged();
                    }
                }

            });
        }

        private ColorStateList createColorStateList(int checked, int normal) {
            int[] colors = new int[]{checked, normal};
            int[][] states = new int[2][];
            states[0] = new int[]{android.R.attr.state_checked};
            states[1] = new int[]{};
            return new ColorStateList(states, colors);
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        CheckBox mCheckBox;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tview);
            mCheckBox = itemView.findViewById(R.id.min15_remind);
        }

    }

}

