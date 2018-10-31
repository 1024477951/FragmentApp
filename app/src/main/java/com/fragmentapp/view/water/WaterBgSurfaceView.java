package com.fragmentapp.view.water;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/12/8.
 */

public class WaterBgSurfaceView extends SurfaceView implements
        SurfaceHolder.Callback {

    private Path path = null;

    private int width, height, waveHeight, waterUp = -1;

    private Paint paint;
    /**
     * off = 偏移值，speed = 速度
     */
    private int offx,offy,speedx = 5,speedy = 5;

    private Bitmap bitmap;

    private DrawThread drawThread;

    public WaterBgSurfaceView(Context context) {
        this(context, null, 0);
    }

    public WaterBgSurfaceView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterBgSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        path = new Path();
        // 初始绘制波纹的画笔
        paint = new Paint();
        // 去除画笔锯齿
        paint.setAntiAlias(true);
        // 设置风格为实线
        paint.setStyle(Paint.Style.FILL);
        // 设置画笔颜色
//        paint.setColor(getResources().getColor(R.color.color_4446b7));

        bitmap = ((BitmapDrawable) (getResources().getDrawable(R.mipmap.icon_moon))).getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder());
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        bitmap = null;
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
        paint = null;
        path = null;
    }

    class DrawThread extends Thread {
        private boolean isRun = false;
        private SurfaceHolder surfaceHolder;

        public DrawThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        public void setRunning(boolean run) {
            isRun = run;
        }

        @Override
        public void run() {
            Canvas canvas = null;
            while (isRun) {
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas != null) {
                        synchronized (surfaceHolder) {
                            //clear
                            canvas.drawColor(getResources().getColor(R.color.transparent), android.graphics.PorterDuff.Mode.CLEAR);
                            paint.setColor(getResources().getColor(R.color.color_ed3159c7));
                            path.reset();
                            //假设屏幕宽度为600，平均分成四分，两个控制点分别定位宽度的四分之三和四分之一，结束点为二分之一
                            path.moveTo(0, waveHeight);//起点
                            path.quadTo(width / 4, waveHeight + offx + 70, width / 2, waveHeight);//150,-60
                            path.quadTo(width * 3 / 4, waveHeight - offy - 70, width, waveHeight);//450,60
                            //闭合区域
                            path.lineTo(width, height);
                            path.lineTo(0, height);//一直到最下方
                            path.close();
                            canvas.drawPath(path, paint);//绘制二级贝塞尔弧形

//                            int bigWidth = width + 100,bigHeight = width + 100;
//                            //绘制相交波浪，波形相反
//                            paint.setColor(getResources().getColor(R.color.color_ed3159c7));
//                            path.reset();
//                            path.moveTo(0, waveHeight);
//                            path.quadTo(bigWidth / 4, waveHeight - off - 50, bigWidth / 2, waveHeight);
//                            path.quadTo(bigWidth * 3 / 4, waveHeight + off + 50, bigWidth, waveHeight);
//                            path.lineTo(bigWidth, bigHeight);
//                            path.lineTo(0, bigHeight);
//                            path.close();
//                            canvas.drawPath(path, paint);
//
//                            //第三条波浪降低高度和深色
//                            int newHeight = waveHeight + 80;
//                            paint.setColor(getResources().getColor(R.color.color_be3c64c7));
//                            path.reset();
//                            path.moveTo(0, newHeight);
//                            path.quadTo(width / 4, newHeight + off + 50, width / 2, newHeight);
//                            path.quadTo(width * 3 / 4, newHeight - off - 50, width, newHeight);
//                            path.lineTo(width, height);
//                            path.lineTo(0, height);
//                            path.close();
//                            canvas.drawPath(path, paint);
//                            //第四条波浪
//                            newHeight = waveHeight + 130;
//                            paint.setColor(getResources().getColor(R.color.color_be3c64c7));
//                            path.reset();
//                            path.moveTo(0, newHeight);
//                            path.quadTo(width / 4, newHeight + off + 80, width / 2, newHeight);
//                            path.quadTo(width * 3 / 4, newHeight - off - 80, width, newHeight);
//                            path.lineTo(width, height);
//                            path.lineTo(0, height);
//                            path.close();
//                            canvas.drawPath(path, paint);

                            offx += speedx;
                            if (offx >= 100 || offx <= -150) {
                                speedx = - speedx;
                            }
                            offy += speedy;
                            if (offy >= 100 || offy <= -150) {
                                speedy = - speedy;
                            }
//                            waveHeight = waveHeight + waterUp;
//                            //水位上升下降
//                            if (waveHeight <= height * 3 / 5 - 50 || waveHeight >= height * 3 / 5 + 50) {
//                                waterUp = -waterUp;
//                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (canvas != null) {
                        canvas.drawBitmap(bitmap, width * 3 / 4, 100, paint);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

}
