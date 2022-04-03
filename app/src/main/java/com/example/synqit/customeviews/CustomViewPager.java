package com.example.synqit.customeviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private boolean isPagingEnabled = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event);
    }

    /**
     * heelo
     *
     * @param b bb
     */
    public void setPagingEnabled(final boolean b) {
        this.isPagingEnabled = b;
    }
}
