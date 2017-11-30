package com.fragmentapp.view.refresh;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhen on 2017/11/22.
 */

public class TextHeadView extends View implements IHeadView{

    private final String TAG = "tag";

    private Paint mPaint;
    private Path mPath;

    private Paint paint;

    private int viewWidth, viewHeight; //控件的宽和高
    private float commandX, commandY; //控制点的坐标
    private float waterHeight;  //水位高度

    private boolean isInc;// 判断控制点是该右移还是左移


    public TextHeadView(@NonNull Context context) {
        this(context,null,0);
    }

    public TextHeadView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TextHeadView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init(){
        //画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#AFDEE4"));
        //路径
        mPath = new Path();
        //辅助画笔
        paint =  new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5f);

    }

    /**
     * 获取控件的宽和高
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;

        // 控制点 开始时的Y坐标
        commandY = viewHeight - (int)(viewHeight * 0.2);

        //终点一开始的Y坐标 ，也就是水位水平高度 ， 红色辅助线
        waterHeight = viewHeight - (int)(viewHeight * 0.1);
    }

    /**
     * 绘制
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 起始点位置
        mPath.moveTo(-1 / 4F * viewWidth, waterHeight);
        //绘制水波浪(圆滑曲线)
        mPath.quadTo(commandX, commandY, viewWidth + 1 / 4F * viewWidth, waterHeight);
        //绘制波浪下方闭合区域
//        mPath.lineTo(viewWidth + 1 / 4F * viewWidth, viewHeight);
//        mPath.lineTo(-1 / 4F * viewWidth, viewHeight);
        mPath.close();
        //绘制路径
        canvas.drawPath(mPath, mPaint);
//        //绘制红色水位高度辅助线
//        canvas.drawLine(0,waterHeight,viewWidth,waterHeight,paint);
//        //产生波浪左右涌动的感觉
//        if (commandX >= viewWidth + 1 / 4F * viewWidth) {//控制点坐标大于等于终点坐标改标识
//            isInc = false;
//        } else if (commandX <= -1 / 4F * viewWidth) {//控制点坐标小于等于起点坐标改标识
//            isInc = true;
//        }
//        commandX = isInc ? commandX + 20 : commandX - 20;
//        //水位不断加高  当距离控件顶端还有1/3的高度时，不再上升
//        if (commandY >= 1 / 3f * viewHeight) {
//            commandY -= 2;
//            waterHeight -= 2;
//        }
//        //路径重置
//        mPath.reset();
//        // 重绘
//        invalidate();
    }

    /**
     * 测量
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (wSpecMode == MeasureSpec.AT_MOST && hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, 300);
        } else if (wSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(300, hSpecSize);
        } else if (hSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(wSpecSize, 300);
        }
    }

    public void setText(String val){

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void startAnim() {
//        startAnim();
    }

    @Override
    public void stopAnim() {

    }
}
