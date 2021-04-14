package com.honhai.foxconn.fxccalendar.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

public class AddEventButton extends android.support.v7.widget.AppCompatImageView {
    Paint themePaint = new Paint();
    Path path = new Path();

    public AddEventButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                themePaint.setColor(getResources().getColor(R.color.colorBottomMenuDefault));
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                themePaint.setColor(Data.getInstance().getThemeColor());
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        float w = getMeasuredWidth();
        float h = getMeasuredHeight();

        path.reset();
        path.addCircle(0, 0, w / 2, Path.Direction.CCW);
        path.addRoundRect(-w / 6, -h / 36, w / 6, h / 36, h / 36, h / 36, Path.Direction.CW);
        path.addRoundRect(-w / 36, -h / 6, w / 36, h / 6, h / 36, h / 36, Path.Direction.CW);
        path.addRect(-w / 36, -h / 36, w / 36, h / 36, Path.Direction.CCW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(.7f, .7f);

        themePaint.setColor(Data.getInstance().getThemeColor());
        canvas.drawPath(path, themePaint);
    }
}
