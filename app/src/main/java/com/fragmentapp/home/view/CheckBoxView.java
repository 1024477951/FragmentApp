package com.fragmentapp.home.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;


import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/11/13.
 */

public class CheckBoxView extends android.support.v7.widget.AppCompatButton {

    // view默认宽高
    private float mHeight = 200;
    private float mWidth = 200;

    private Paint mPaint;
    private Path mPath;

    // 绘制贝塞尔曲线控制点的位置参数
    private float C = 1f;
    // 贝塞尔动画缓存参数
    private float radio = C;

    // 控制点与数据点的差值
    private float mDifferenceHeigth;
    private float mDifferenceWidth;

    // 顺时针记录绘制的四个数据点
    private float[] mData = new float[8];
    // 顺时针记录绘制的八个控制点
    private float[] mCtrl = new float[16];

    //背景色
    private int mColor;
    // 动画时间
    private long mDuration = 300;
    //放大缩小度
    private float mScaleRange = 1.2f;
    //压后 弯曲度
    private float mBezierRange = 0.3f;

    public CheckBoxView(Context context) {
        super(context);
        init(context,null);
    }

    public CheckBoxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    public CheckBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.pressureview);

            C = ta.getFloat(R.styleable.pressureview_bezierradio, C);
            radio = C;

            mDuration = ta.getInt(R.styleable.pressureview_animduration,
                    (int) mDuration);

            mScaleRange = ta.getFloat(R.styleable.pressureview_scale_ranger,
                    mScaleRange);
            if(mScaleRange<=1){
                mScaleRange = 1.2f;
            }

            mBezierRange = ta.getFloat(
                    R.styleable.pressureview_bezierradion_range, mBezierRange);

            if(mBezierRange<=0||mBezierRange>=C){
                mBezierRange = 0.3f;
            }

            ta.recycle();
        }
        setBackgroundColor(Color.TRANSPARENT);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);

        mPath = new Path();
    }

    private void changeData() {
        mDifferenceHeigth = mHeight * radio;
        mDifferenceWidth = mWidth * radio;

        mData[0] = 0;
        mData[1] = mHeight;
        mData[2] = mWidth;
        mData[3] = 0;
        mData[4] = 0;
        mData[5] = -mHeight;
        mData[6] = -mWidth;
        mData[7] = 0;

        mCtrl[0] = mData[0] + mDifferenceWidth;
        mCtrl[1] = mData[1];

        mCtrl[2] = mData[2];
        mCtrl[3] = mData[3] + mDifferenceHeigth;

        mCtrl[4] = mData[2];
        mCtrl[5] = mData[3] - mDifferenceHeigth;

        mCtrl[6] = mData[4] + mDifferenceWidth;
        mCtrl[7] = mData[5];

        mCtrl[8] = mData[4] - mDifferenceWidth;
        mCtrl[9] = mData[5];

        mCtrl[10] = mData[6];
        mCtrl[11] = mData[7] - mDifferenceHeigth;

        mCtrl[12] = mData[6];
        mCtrl[13] = mData[7] + mDifferenceHeigth;

        mCtrl[14] = mData[0] - mDifferenceWidth;
        mCtrl[15] = mData[1];
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h / 2;
        mWidth = w / 2;
        changeData();
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save(1);
        canvas.translate(mWidth, mHeight);

        mPath.reset();
        mPath.moveTo(mData[0], mData[1]);

        mPath.cubicTo(mCtrl[0], mCtrl[1], mCtrl[2], mCtrl[3], mData[2],
                mData[3]);
        mPath.cubicTo(mCtrl[4], mCtrl[5], mCtrl[6], mCtrl[7], mData[4],
                mData[5]);
        mPath.cubicTo(mCtrl[8], mCtrl[9], mCtrl[10], mCtrl[11], mData[6],
                mData[7]);
        mPath.cubicTo(mCtrl[12], mCtrl[13], mCtrl[14], mCtrl[15], mData[0],
                mData[1]);

        canvas.drawPath(mPath, mPaint);
        canvas.restoreToCount(1);
        super.onDraw(canvas);
    }

    // down事件动画
    private AnimatorSet as;
    // up事件动画
    private AnimatorSet as1;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 贝塞尔曲线改变动画
                ValueAnimator va = ObjectAnimator.ofFloat(0, 1);
                va.setInterpolator(new OvershootInterpolator());
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (as.isRunning()) {
                            // 改变贝塞尔曲线参数
                            radio = (float) (C - mBezierRange
                                    * animation.getAnimatedFraction());
                            // 改变所有相关数值
                            changeData();
                            postInvalidate();
                        }
                    }
                });
                // X,Y放大缩小动画
                ValueAnimator vaSX = ObjectAnimator.ofFloat(this, "scaleX", 1f,
                        mScaleRange);
                ValueAnimator vaSY = ObjectAnimator.ofFloat(this, "scaleY", 1f,
                        mScaleRange);
                vaSX.setInterpolator(new OvershootInterpolator());
                vaSY.setInterpolator(new OvershootInterpolator());
                // 动画集合初始化
                as = new AnimatorSet();
                as.playTogether(va, vaSX, vaSY);
                as.setDuration(mDuration);
                as.start();
                break;
            case MotionEvent.ACTION_UP:
                // 贝塞尔曲线改变动画
                ValueAnimator va1 = ObjectAnimator.ofFloat(0, 1.5f, 1);
                va1.setInterpolator(new OvershootInterpolator(4));
                va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (as1.isRunning()) {
                            // 改变贝塞尔曲线参数
                            radio = (float) (C - mBezierRange + mBezierRange
                                    * animation.getAnimatedFraction());
                            // 改变所有相关数值
                            changeData();
                            postInvalidate();
                        } else {
                            Log.e("TAG", "as1 not running");
                        }
                    }
                });
                // X,Y放大缩小动画
                ValueAnimator vaSX1 = ObjectAnimator.ofFloat(this, "scaleX",
                        mScaleRange, 1f);
                ValueAnimator vaSY1 = ObjectAnimator.ofFloat(this, "scaleY",
                        mScaleRange, 1f);
                vaSX1.setInterpolator(new OvershootInterpolator(5));
                vaSY1.setInterpolator(new OvershootInterpolator(5));
                // 动画集合初始化
                as1 = new AnimatorSet();
                as1.playTogether(va1, vaSX1, vaSY1);
                as1.setDuration(mDuration);
                as1.start();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        if(as!=null){
            as.cancel();
        }
        if(as1!=null){
            as1.cancel();
        }
        super.onDetachedFromWindow();
    }

}
