package com.yhw.alixiaohao.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    public static final int STATE_DOWN = MotionEvent.ACTION_DOWN;
    public static final int STATE_UP = MotionEvent.ACTION_UP;
    public static final int STATE_MOVE = MotionEvent.ACTION_MOVE;

    int state = STATE_UP;
    //傻逼

    private boolean noScroll = false;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public void setNoScroll(boolean noScroll) {
        this.noScroll = noScroll;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (noScroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(mOnTouchListener != null)
            mOnTouchListener.onTouch(ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    public void setOnTouchListener(OnTouchListener l) {
        mOnTouchListener = l;
    }

    OnTouchListener mOnTouchListener;

    interface OnTouchListener {
        void onTouch(int state);
    }

}