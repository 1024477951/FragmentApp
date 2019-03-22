package com.fragmentapp.view.viewpager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by liuzhen on 2018/3/21.
 */

public class ScrollerViewPager extends ViewPager{

    private boolean isScroller = false;

    public ScrollerViewPager(@NonNull Context context) {
        this(context,null);
    }

    public ScrollerViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 触摸事件不触发
        if (this.isScroller) {
            return super.onTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // 不处理触摸拦截事件
        if (this.isScroller) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (this.isScroller) {
            return super.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
