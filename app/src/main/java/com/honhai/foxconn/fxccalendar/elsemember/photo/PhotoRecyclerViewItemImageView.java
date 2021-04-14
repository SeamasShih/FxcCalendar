package com.honhai.foxconn.fxccalendar.elsemember.photo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.honhai.foxconn.fxccalendar.data.Data;

public class PhotoRecyclerViewItemImageView extends android.support.v7.widget.AppCompatImageView {
    private boolean isSelect = false;
    private Paint paint = new Paint();

    public PhotoRecyclerViewItemImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Data.getInstance().getThemeColor());
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isSelect)
            canvas.drawRect(0, 0, getWidth() - 1, getHeight() - 1, paint);
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
        invalidate();
    }
}
