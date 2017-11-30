package com.fragmentapp.view.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by liuzhen on 2017/11/29.
 */

public class DownView extends View implements IHeadView{

    private int PULL_HEIGHT;
//    private int PULL_DELTA;
    private float mWidthOffset;
    private Path mPath;
    private Paint mBackPaint;
    private int mWidth;
    private int mHeight;

    public DownView(Context context) {
        this(context, null);
    }

    public DownView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mWidthOffset = 0.5f;
        mPath = new Path();
        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(0xff8b90af);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        if (height > PULL_DELTA + PULL_HEIGHT) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(PULL_DELTA + PULL_HEIGHT, MeasureSpec.getMode(heightMeasureSpec));
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
//            PULL_HEIGHT = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeight/3, getResources().getDisplayMetrics());
//            PULL_DELTA = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mHeight/2, getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, PULL_HEIGHT, mBackPaint);

        mPath.reset();
        mPath.moveTo(0, PULL_HEIGHT);
        mPath.quadTo(mWidthOffset * mWidth, PULL_HEIGHT + (mHeight - PULL_HEIGHT) * 2, mWidth, PULL_HEIGHT);
        canvas.drawPath(mPath, mBackPaint);
        invalidate();
    }

    @Override
    public View getView() {
        return this;
    }

    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(10);
    @Override
    public void startAnim() {
        ValueAnimator va = ValueAnimator.ofFloat(mHeight, mHeight/2);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                val = decelerateInterpolator.getInterpolation(val / mHeight) * val;
//                setTranslationY(val);
//                getLayoutParams().height = (int) val;
                PULL_HEIGHT = (int)val;
                requestLayout();
//                Log.e("tag",val+"");
            }
        });
        va.setDuration(500);
        va.start();
    }

    @Override
    public void stopAnim() {

    }

    public void setPULL_HEIGHT(int y){
        PULL_HEIGHT = y;
        invalidate();
    }

}