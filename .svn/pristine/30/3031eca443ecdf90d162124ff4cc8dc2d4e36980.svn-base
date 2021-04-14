package com.honhai.foxconn.fxccalendar.addevent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DirectorView extends View {
    private Path path;
    private Paint white;

    public DirectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        white = new Paint();
        white.setColor(Color.LTGRAY);
        white.setStyle(Paint.Style.STROKE);
        white.setAntiAlias(true);
    }

    public void setColor(int color) {
        white.setColor(color);
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    private void setPath() {
        int w = getWidth() / 2;
        int h = getHeight() / 2;
        path.reset();
        path.moveTo(0, -h);
        path.lineTo(w, 0);
        path.lineTo(0, h);
        white.setStrokeWidth(w * .3f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setPath();

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(0.5f, 0.5f);
        canvas.drawPath(path, white);
        canvas.restore();
    }
}
