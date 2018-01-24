package com.fragmentapp.view.refresh;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/1/24.
 */

public class SunHeadView extends View implements IHeadView{

    private int mWidth;
    private int mHeight;

    private Paint paint;
    private RectF rectF = null;

    private float angle,loadAngle = 45;

    private ValueAnimator va;

    private int left,top;

    private int off = 1;

    private Path path;

    public SunHeadView(Context context) {
        this(context, null, 0);
    }

    public SunHeadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SunHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){

        path = new Path();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.color_a9a05c));

        rectF = new RectF(0,0,0,0);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();

            this.left = mWidth / 2;
            this.top = mHeight / 3;

            rectF.set(this.left,this.top,this.left + 50,this.top + 50);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path.reset();
        path.moveTo(mWidth/3,mHeight);
        path.quadTo(mWidth/2,0,mWidth*2/3,mHeight);
        canvas.drawPath(path, paint);

        canvas.drawArc(rectF, angle, 360 - angle * 2, true, paint);


    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void startAnim() {

        this.left = mWidth / 2;
        rectF.set(left,top,left + 50,top + 50);
        angle = loadAngle;

        va = ValueAnimator.ofFloat(loadAngle , 0);

        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = (float)valueAnimator.getAnimatedValue();

                left -= off;
                rectF.set(left,top,left + 50,top + 50);
                postInvalidate();
            }
        });
        va.setInterpolator(new LinearInterpolator());//重力差值器
        va.setDuration(2500);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.start();
    }

    @Override
    public void stopAnim() {
        if (va != null)
            va.cancel();
        va = null;
    }
}
