package com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class GroupItemClickListener implements RecyclerView.OnItemTouchListener {
    IGroupItemDecoration mDecoration;
    OnGroupItemClickListener mListener;
    GestureDetector mGestureDetector;

    public interface OnGroupItemClickListener {
        void onGroupItemClick(GroupItem groupItem);
        void onGroupItemLongClick(GroupItem groupItem);
    }

    public GroupItemClickListener(final IGroupItemDecoration decoration, OnGroupItemClickListener listener){
        mDecoration = decoration;
        mListener = listener;
        mGestureDetector = new GestureDetector(decoration.getContext(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                GroupItem groupItem = decoration.findGroupItemUnder((int)e.getX(),(int)e.getY());
                if(groupItem != null && mListener != null){
                    mListener.onGroupItemClick(groupItem);
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                GroupItem groupItem = decoration.findGroupItemUnder((int)e.getX(),(int)e.getY());
                if (groupItem != null && mListener != null) {
                    mListener.onGroupItemLongClick(groupItem);
                }
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
