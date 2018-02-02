package com.fragmentapp.view.beans;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.fragmentapp.R;
import com.fragmentapp.helper.RandomUtil;
import com.fragmentapp.view.MyBounceInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhen on 2017/1/17.
 */

public class BeansView extends View {

    private Paint paint;

    private Paint jumpPaint;
    private Path path;
    private int mWidth;
    private int mHeight;
    private int top;

    private int jump;

    private ValueAnimator va;

    private List<Beans> beans = null;

    public BeansView(Context context) {
        this(context, null);
    }

    public BeansView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BeansView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Log.e("tag","init");
        setWillNotDraw(false);
        path = new Path();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        jumpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        jumpPaint.setAntiAlias(true);
        jumpPaint.setStyle(Paint.Style.STROKE);
        jumpPaint.setColor(getResources().getColor(R.color.white));

        beans = new ArrayList<>();

        Bitmap bitmap = ((BitmapDrawable)(getResources().getDrawable(R.mipmap.ball))).getBitmap();
        //随机生成球体的大小、
        for(int i = 0;i < 6; i ++){
            int radius = RandomUtil.random(15,30);
            final Beans b = new Beans(radius);
            b.setDirection(i % 2 == 0 ? Beans.Left : Beans.Right);
            b.bitmap = Bitmap.createScaledBitmap(bitmap,radius,radius,true);
            beans.add(b);
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            this.top = top;
            jump = mHeight - 2;
            startAnim();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path.reset();
        path.moveTo(0,mHeight - 2);
        path.quadTo(mWidth/2, jump,mWidth, mHeight - 2);
        path.close();
        canvas.drawPath(path, jumpPaint);

        for (Beans b : beans) {
            if (b.bitmap != null) {
                if (b.getDirection() == Beans.Left) {
                    canvas.drawBitmap(b.bitmap, b.getCx(), b.getCy(), null);
                } else {
                    canvas.drawBitmap(b.bitmap, -b.getCx() + mWidth, b.getCy(), null);
                }
            }
        }

    }

    public void startAnim() {
        if (mWidth == 0 || beans.size() == 0) return;

        for (Beans b : beans) {
            b.setState(0);
            b.setOff(0);
            b.setRand(RandomUtil.random(20));//随机生成抛出的速度值
        }

        va = ValueAnimator.ofFloat(top, mHeight);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float val = (float)animation.getAnimatedValue();

                for (Beans b : beans) {
                    b.setCy(val - b.getRadius());
                    b.move(mWidth);//先移动坐标，实际上是改变了off 偏移量的值
                    b.setCx(mWidth / 2 + b.getOff());//刷新X轴坐标
                }
//                Log.e("tag",val+" height "+mHeight);
                if (val >= mHeight){
                    Log.e("tag",val+"---------------------------");
                }
                postInvalidate();
            }
        });
        va.setInterpolator(new MyBounceInterpolator());//重力差值器
        va.setDuration(3500);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.start();
    }

    public void stopAnim() {
        va.cancel();
        va = null;
    }

}