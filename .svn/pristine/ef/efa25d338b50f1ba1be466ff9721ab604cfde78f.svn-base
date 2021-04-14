package com.honhai.foxconn.fxccalendar.elsemember.GroupManager;

import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

import java.util.List;

public class GroupManagerAdapter extends RecyclerView.Adapter<GroupManagerAdapter.ViewHolder> {
    private List<ManagerGroupList> mList;
//    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();
//    private boolean mIsSelectable = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_name_manager;
        private final CheckBox cb_checkbox;
        View groupView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            groupView = itemView;
            tv_name_manager = itemView.findViewById(R.id.tv_name_manager);
            cb_checkbox = itemView.findViewById(R.id.cb_checkbox);

        }
    }

    public GroupManagerAdapter(List<ManagerGroupList> managerGroupLists) {

        mList = managerGroupLists;
    }

    @Override
    public GroupManagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.select_group_manager_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupManagerAdapter.ViewHolder viewHolder, final int position) {

        ManagerGroupList member = mList.get(position);
        viewHolder.tv_name_manager.setText(member.getName());
        viewHolder.cb_checkbox.setButtonTintList(createColorStateList(Data.getInstance().getThemeColor(), viewHolder.cb_checkbox.getContext().getResources().getColor(R.color.colorPopupSwitchCalendarText)));

        viewHolder.cb_checkbox.setChecked(isItemChecked(position));
        viewHolder.cb_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setItemChecked(position, isChecked);
            }
        });
    }

    private void setItemChecked(int position, boolean isChecked) {
        mList.get(position).setStatus(isChecked);
    }

    private boolean isItemChecked(int position) {
        return mList.get(position).isChecked();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private ColorStateList createColorStateList(int checked, int normal) {
        int[] colors = new int[]{checked, normal};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_checked};
        states[1] = new int[]{};
        return new ColorStateList(states, colors);
    }

}
