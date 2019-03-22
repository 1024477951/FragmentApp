package com.fragmentapp.view.remove;

import android.content.Context;
import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by liuzhen on 2018/3/20.
 */

public class ScrollerRemoveView extends FrameLayout {

    private final int Left = 0;
    private final int Right = 1;
    private final int Top = 2;
    private final int Bottom = 3;

    //检测手指在滑动过程中的速度
    private VelocityTracker mVelocityTracker;
    //item对应的布局
    private View mItemLayout;
    private int topX = 0;

    public ScrollerRemoveView(Context context) {
        this(context, null);
    }

    public ScrollerRemoveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerRemoveView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mItemLayout = getChildAt(0);
        mItemLayout.setClickable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        mVelocityTracker.addMovement(e);

        int x = (int) e.getX();
        int y = (int) e.getY();

        int downX = 0, downY = 0;

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;

                break;
            case MotionEvent.ACTION_MOVE:

                float dx = x - downX;
                float dy = y - downY;

                mVelocityTracker.computeCurrentVelocity(1000);//计算手指滑动的速度
                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度（向左为负）

                if (Math.abs(dx) > mItemLayout.getRight() / 2 && xVelocity < -50) {
                    int orientation = getOrientation(dx, dy);
                    switch (orientation) {
                        case Right:
                            int scrollX = Math.abs(x - topX);

                            Log.e("tag","scrollX "+scrollX + " dx "+dx+" Right "+mItemLayout.getRight());
                            mItemLayout.scrollBy(scrollX, 0);
                            topX = x;
                            break;
                        case Left:

                            break;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:

//                mVelocityTracker.computeCurrentVelocity(1000);//计算手指滑动的速度
//                float xVelocity = mVelocityTracker.getXVelocity();//水平方向速度（向左为负）
//                float yVelocity = mVelocityTracker.getYVelocity();//垂直方向速度
//                if (Math.abs(xVelocity) > 100 && Math.abs(xVelocity) > Math.abs(yVelocity)) {
//                    if (xVelocity > 100) {//右滑速度大于100，则删除按钮隐藏
//
//                    }
//                }
//                getParent().requestDisallowInterceptTouchEvent(false);
            case MotionEvent.ACTION_CANCEL:
                mItemLayout.scrollTo(0, 0);
                break;
        }

        return super.onInterceptTouchEvent(e);
    }

    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            //X轴移动
            return dx > 0 ? Right : Left;
        } else {
            //Y轴移动
            return dy > 0 ? Bottom : Top;
        }
    }

    private void clearVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
        }
        mVelocityTracker = null;
    }

    @Override
    protected void onDetachedFromWindow() {
        clearVelocityTracker();
        super.onDetachedFromWindow();
    }

}
