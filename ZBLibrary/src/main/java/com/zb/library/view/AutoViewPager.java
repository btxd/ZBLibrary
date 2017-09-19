package com.zb.library.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义ViewPager
 * 冰
 */
public class AutoViewPager extends ViewPager {

    //是否禁止可以滚动；默认可以
    private boolean isScroll = false;

    public AutoViewPager(Context context) {
        super(context);
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.isScroll && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isScroll && super.onInterceptTouchEvent(ev);
    }

    /**
     * 设置是否可以滚动
     * @param scroll
     */
    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}
