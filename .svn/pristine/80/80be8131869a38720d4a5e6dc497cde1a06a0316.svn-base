package com.honhai.foxconn.fxccalendar.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class PopupCalendarImageView extends View {

    private Bitmap bitmap;

    public PopupCalendarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBitmap(int resId) {
        this.bitmap = BitmapFactory.decodeResource(getResources(), resId);
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.scale(.6f, .6f, getWidth() / 2, getHeight() / 2);
        super.onDraw(canvas);
    }
}
