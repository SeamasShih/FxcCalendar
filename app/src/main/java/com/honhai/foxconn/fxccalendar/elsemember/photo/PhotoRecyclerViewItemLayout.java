package com.honhai.foxconn.fxccalendar.elsemember.photo;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;

public class PhotoRecyclerViewItemLayout extends ConstraintLayout {
    public PhotoRecyclerViewItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
