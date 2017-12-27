package com.fragmentapp.view.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/11/29.
 */

public class DownHeadView extends View implements IHeadView{

    private int pull_height;
    private Path mPath;
    private Paint mBackPaint;
    private int backColor;
    private int mWidth;
    private int mHeight;
    private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator(10);//先快后慢差值器

    public DownHeadView(Context context) {
        this(context, null);
    }

    public DownHeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DownHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setWillNotDraw(false);
        backColor = getResources().getColor(R.color.color_8b90af);
        mPath = new Path();
        mBackPaint = new Paint();
        mBackPaint.setAntiAlias(true);
        mBackPaint.setStyle(Paint.Style.FILL);
        mBackPaint.setColor(backColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, mWidth, pull_height, mBackPaint);

        mPath.reset();
        mPath.moveTo(0, pull_height);//起点
        mPath.quadTo(mWidth/2,pull_height*2,mWidth,pull_height);//控制点和终点

        canvas.drawPath(mPath, mBackPaint);//绘制二级贝塞尔弧形
        invalidate();
    }

    @Override
    public View getView() {
        return this;
    }


    @Override
    public void startAnim() {
        backColor = getResources().getColor(R.color.color_8babaf);
        mBackPaint.setColor(backColor);
        ValueAnimator va = ValueAnimator.ofFloat(mHeight, mHeight/2);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (float) animation.getAnimatedValue();
                val = decelerateInterpolator.getInterpolation(val / mHeight) * val;
                pull_height = (int)val;
                requestLayout();
            }
        });
        va.setInterpolator(new OvershootInterpolator(3));//甩动差值器
        va.setDuration(500);
        va.start();
    }

    @Override
    public void stopAnim() {
        backColor = getResources().getColor(R.color.color_8b90af);
        mBackPaint.setColor(backColor);
    }
    /**改变控制点*/
    public void setPull_height(int y){
        pull_height = y;
        invalidate();
    }

}