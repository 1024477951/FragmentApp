package com.fragmentapp.view.beans;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.fragmentapp.R;
import com.fragmentapp.helper.RandomUtil;

/**
 * Created by liuzhen on 2017/1/17.
 */

public class BeansView extends View {

    private Paint paint;
    private int mWidth;
    private int mHeight;
    private int top;

    private ValueAnimator va;

    private Beans beans1,beans2,beans3,beans4,beans5,beans6;

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
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.color_ff9c19));
        //随机生成球体的大小、
        beans1 = new Beans(RandomUtil.random(5,15));
        beans2 = new Beans(RandomUtil.random(5,15));
        beans3 = new Beans(RandomUtil.random(5,15));
        beans4 = new Beans(RandomUtil.random(5,15));
        beans5 = new Beans(RandomUtil.random(5,15));
        beans6 = new Beans(RandomUtil.random(5,15));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            this.top = top;
            startAnim();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //正常向右掉落，这里也可以利用随机生成方向，这里就固定左边三个右边三个
        canvas.drawCircle(beans1.getCx(), beans1.getCy(), beans1.getRadius(), paint);
        canvas.drawCircle(beans2.getCx(), beans2.getCy(), beans2.getRadius(), paint);
        canvas.drawCircle(beans3.getCx(), beans3.getCy(), beans3.getRadius(), paint);
        //让球往左边掉落
        canvas.drawCircle(-beans4.getCx()+mWidth, beans4.getCy(), beans4.getRadius(), paint);
        canvas.drawCircle(-beans5.getCx()+mWidth, beans5.getCy(), beans5.getRadius(), paint);
        canvas.drawCircle(-beans6.getCx()+mWidth, beans6.getCy(), beans6.getRadius(), paint);

    }

    public void startAnim() {
        if (mWidth == 0) return;

        beans1.setState(0);
        beans1.setOff(0);
        beans1.setRand(RandomUtil.random(20));//随机生成抛出的速度值

        beans2.setState(0);
        beans2.setOff(0);
        beans2.setRand(RandomUtil.random(20));

        beans3.setState(0);
        beans3.setOff(0);
        beans3.setRand(RandomUtil.random(20));

        beans4.setState(0);
        beans4.setOff(0);
        beans4.setRand(RandomUtil.random(20));

        beans5.setState(0);
        beans5.setOff(0);
        beans5.setRand(RandomUtil.random(20));

        beans6.setState(0);
        beans6.setOff(0);
        beans6.setRand(RandomUtil.random(20));

        va = ValueAnimator.ofFloat(top, mHeight - top);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                float val = (float)animation.getAnimatedValue();

                beans1.setCy(val);
                beans1.move(mWidth);//先移动坐标，实际上是改变了off偏移量的值
                beans1.setCx(mWidth / 2 + beans1.getOff());//刷新X轴坐标

                beans2.setCy(val);
                beans2.move(mWidth);
                beans2.setCx(mWidth / 2 + beans2.getOff());

                beans3.setCy(val);
                beans3.move(mWidth);
                beans3.setCx(mWidth / 2 + beans3.getOff());

                beans4.setCy(val);
                beans4.move(mWidth);
                beans4.setCx(mWidth / 2 + beans4.getOff());

                beans5.setCy(val);
                beans5.move(mWidth);
                beans5.setCx(mWidth / 2 + beans5.getOff());

                beans6.setCy(val);
                beans6.move(mWidth);
                beans6.setCx(mWidth / 2 + beans6.getOff());

                invalidate();
            }
        });
        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //防止停止后球体因为半径的不一样而降落到地面的水平不一样，统一水平线
                beans1.setCy(mHeight - beans1.getRadius());

                beans2.setCy(mHeight - beans2.getRadius());

                beans3.setCy(mHeight - beans3.getRadius());

                beans4.setCy(mHeight - beans4.getRadius());

                beans5.setCy(mHeight - beans5.getRadius());

                beans6.setCy(mHeight - beans6.getRadius());

                invalidate();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        va.setInterpolator(new BounceInterpolator());//重力差值器
        va.setDuration(3000);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.start();
    }

    public void stopAnim() {
        va.cancel();
        va = null;
    }

}