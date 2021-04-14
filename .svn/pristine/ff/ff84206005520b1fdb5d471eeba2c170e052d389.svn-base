package com.honhai.foxconn.fxccalendar.main;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.honhai.foxconn.fxccalendar.data.Data;

public class BottomNavigationBar extends BottomNavigationView {
    private ValueAnimator animator;
    private float rectLeft = 0;
    private OnGestureListener gestureListener = new OnGestureListener();
    private GestureDetector detector;

    public BottomNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        animator = new ValueAnimator();
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rectLeft = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        detector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    public void moveLabelBar(int to) {
        float w = (float) getWidth() / getMenu().size();
        animator.cancel();
        animator.setFloatValues(rectLeft, w * to);
        animator.start();
    }

    public int getPressItem(MotionEvent e) {
        float x = e.getX();
        int s = getMenu().size();
        float w = getWidth();
        for (int i = 1; i <= s; i++) {
            if (x < w * i / s)
                return i - 1;
        }
        return s;
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        canvas.save();
        canvas.translate(0, -10);
        super.drawChild(canvas, child, drawingTime);
        canvas.restore();

        Paint paint = new Paint();
        paint.setColor(Data.getInstance().getThemeColor());
        canvas.drawRect(rectLeft, canvas.getHeight() - 15, rectLeft + (float) getWidth() / getMenu().size(), canvas.getHeight(), paint);
        return true;
    }

    public void setOnNavigationItemLongClickListener(OnNavigationItemLongClickListener longClickListener) {
        gestureListener.setLongClickListener(longClickListener);
    }

    public void setOnNavigationItemClickListener(OnNavigationItemClickListener clickListener) {
        gestureListener.setClickListener(clickListener);
    }

    public interface OnNavigationItemLongClickListener {
        void onNavigationItemLongClickListener(MotionEvent ev);
    }

    public interface OnNavigationItemClickListener {
        boolean onNavigationItemClickListener(MotionEvent ev);
    }

    private class OnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private OnNavigationItemClickListener clickListener;
        private OnNavigationItemLongClickListener longClickListener;

        public void setClickListener(OnNavigationItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void setLongClickListener(OnNavigationItemLongClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (clickListener != null)
                return clickListener.onNavigationItemClickListener(e);
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (longClickListener != null)
                longClickListener.onNavigationItemLongClickListener(e);
            else
                super.onLongPress(e);
        }
    }
}
