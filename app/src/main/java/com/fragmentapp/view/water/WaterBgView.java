package com.fragmentapp.view.water;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/12/8.
 */

public class WaterBgView extends View {

    private Path path = null;

    private int width, height,waveHeight,waterUp = -1;

    private Paint paint;
    /**off = 偏移值，speed = 速度*/
    private int off,speed = 10;

    private Bitmap bitmap;

    public WaterBgView(Context context) {
        this(context,null,0);
    }

    public WaterBgView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaterBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        path = new Path();
        // 初始绘制波纹的画笔
        paint = new Paint();
        // 去除画笔锯齿
        paint.setAntiAlias(true);
        // 设置风格为实线
        paint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
//        paint.setColor(getResources().getColor(R.color.color_4446b7));

        bitmap = ((BitmapDrawable)(getResources().getDrawable(R.mipmap.icon_moon))).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        paint.setColor(getResources().getColor(R.color.color_ed3159c7));
        path.reset();
        //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
        path.moveTo(-width + off, waveHeight);//起点
        //左边区域，移动的时候连贯,假设max=600
        path.quadTo(-width * 3 / 4 + off,waveHeight + 70,-width / 2 + off,waveHeight);//-450,60
        path.quadTo(-width / 4 + off,waveHeight - 70,off,waveHeight);//-150,-60
        //根据上面的假设得到两个的控制点距离，x1 = width * 3 / 4，x2 = width / 4 ，和结束点 end = width / 2
        path.quadTo(width / 4 + off,waveHeight + 70,width / 2 + off,waveHeight);//150,-60
        path.quadTo(width * 3 / 4 + off,waveHeight - 70,width + off,waveHeight);//450,60
        //闭合区域
        path.lineTo(width,height);
        path.lineTo(-width,height);//一直到最下方
        path.close();
        canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

        paint.setColor(getResources().getColor(R.color.color_ed3159c7));
        path.reset();
        //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
        path.moveTo(-width + off, waveHeight);//起点
        //左边区域，移动的时候连贯,假设max=600
        path.quadTo(-width * 3 / 4 + off,waveHeight - 50,-width / 2 + off,waveHeight);//-450,60
        path.quadTo(-width / 4 + off,waveHeight + 50,off,waveHeight);//-150,-60
        //根据上面的假设得到两个的控制点距离，x1 = width * 3 / 4，x2 = width / 4 ，和结束点 end = width / 2
        path.quadTo(width / 4 + off,waveHeight - 50,width / 2 + off,waveHeight);//150,-60
        path.quadTo(width * 3 / 4 + off,waveHeight + 50,width + off,waveHeight);//450,60
        //闭合区域
        path.lineTo(width,height);
        path.lineTo(-width,height);//一直到最下方
        path.close();
        canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

//        //第三条只是辅助波浪，本身衔接不够平滑，为了追求衔接平滑还是在原来的高度上下降调整
//        paint.setColor(getResources().getColor(R.color.color_4b12a6d2));
//        path.reset();
//        //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
//        path.moveTo(-width + off, waveHeight + 100);//起点
//        //左边区域，移动的时候连贯,假设max=600
//        path.quadTo(-width / 2 + off,waveHeight - 120,off,waveHeight + 100);//-450,60
//        path.quadTo(width / 2 + off,waveHeight - 120,width + off,waveHeight + 100);//450,60
//        //闭合区域
//        path.lineTo(width,height);
//        path.lineTo(-width,height);//一直到最下方
//        path.close();
//        canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

//        canvas.drawCircle(-width + off, waveHeight - 400, 5, paint);
//        canvas.drawCircle(-width / 2 + off, waveHeight - 400, 5, paint);
//        canvas.drawCircle(off, waveHeight, 5, paint);
//        canvas.drawCircle(width / 2 + off, waveHeight + 400, 5, paint);
//        canvas.drawCircle(width + off, waveHeight + 400, 5, paint);
//        canvas.drawCircle(width + off, waveHeight, 5, paint);

        int newHeight = waveHeight + 80;
        paint.setColor(getResources().getColor(R.color.color_be3c64c7));
        path.reset();
        //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
        path.moveTo(-width + off, newHeight);//起点
        //左边区域，移动的时候连贯,假设max=600
        path.quadTo(-width * 3 / 4 + off,newHeight + 50,-width / 2 + off,newHeight);//-450,60
        path.quadTo(-width / 4 + off,newHeight - 50,off,newHeight);//-150,-60
        //根据上面的假设得到两个的控制点距离，x1 = width * 3 / 4，x2 = width / 4 ，和结束点 end = width / 2
        path.quadTo(width / 4 + off,newHeight + 50,width / 2 + off,newHeight);//150,-60
        path.quadTo(width * 3 / 4 + off,newHeight - 50,width + off,newHeight);//450,60
        //闭合区域
        path.lineTo(width,height);
        path.lineTo(-width,height);//一直到最下方
        path.close();
        canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

        newHeight = waveHeight + 130;
        paint.setColor(getResources().getColor(R.color.color_be3c64c7));
        path.reset();
        //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
        path.moveTo(-width + off, newHeight);//起点
        //左边区域，移动的时候连贯,假设max=600
        path.quadTo(-width * 3 / 4 + off,newHeight + 80,-width / 2 + off,newHeight);//-450,60
        path.quadTo(-width / 4 + off,newHeight - 80,off,newHeight);//-150,-60
        //根据上面的假设得到两个的控制点距离，x1 = width * 3 / 4，x2 = width / 4 ，和结束点 end = width / 2
        path.quadTo(width / 4 + off,newHeight + 80,width / 2 + off,newHeight);//150,-60
        path.quadTo(width * 3 / 4 + off,newHeight - 80,width + off,newHeight);//450,60
        //闭合区域
        path.lineTo(width,height);
        path.lineTo(-width,height);//一直到最下方
        path.close();
        canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

        //贝塞尔坐标，测试红点
//        canvas.drawCircle(-width + off, waterHeight + 100, 5, paint2);
//
//        canvas.drawCircle(-width * 3 / 4 + off, waterHeight + 100, 5, paint2);
//        canvas.drawCircle(-width / 2 + off, waterHeight, 5, paint2);
//
//        canvas.drawCircle(-width / 4 + off, waterHeight-100, 5, paint2);
//        canvas.drawCircle(off, waterHeight, 5, paint2);
//
//        canvas.drawCircle(width / 4 + off, waterHeight + 100, 5, paint2);
//        canvas.drawCircle(width / 2 + off, waterHeight, 5, paint2);
//
//        canvas.drawCircle(width * 3 / 4 + off, waterHeight - 100, 5, paint2);
//        canvas.drawCircle(width + off, waterHeight, 5, paint2);

//        Log.e("tag",""+off);
        off = off + speed;//移动X控制点
        if (off >= width){
            off = 0;
//            Log.e("tag","-----------------------------"+off);
        }
        waveHeight = waveHeight + waterUp;
        //水位上升下降
        if (waveHeight <=  height * 3 / 5 - 50 || waveHeight >=  height * 3 / 5 + 50) {
            waterUp = -waterUp;
        }

        canvas.drawBitmap(bitmap,width * 3 / 4,100,paint);
        postInvalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            width = getWidth();
            height = getHeight();
            waveHeight = height * 3 / 5;//波浪的高度
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
    }

    public void cancel(){
        bitmap = null;
    }

}
