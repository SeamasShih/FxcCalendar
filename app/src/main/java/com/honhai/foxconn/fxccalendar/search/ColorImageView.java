package com.honhai.foxconn.fxccalendar.search;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

public class ColorImageView extends android.support.v7.widget.AppCompatImageView {
    private Paint paint = new Paint();

    public ColorImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), getWidth() / 2, getWidth() / 2, paint);
    }
}
