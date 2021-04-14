package com.honhai.foxconn.fxccalendar.eventpreview.RecyclerView;

import android.content.Context;


public interface IGroupItemDecoration {
    public Context getContext();
    /**
     * 判断当前点击位置是否处于GroupItem区域
     * @param x
     * @param y
     * @return
     */
    public GroupItem findGroupItemUnder(int x, int y);
}
