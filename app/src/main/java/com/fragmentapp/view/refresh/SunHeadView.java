package com.fragmentapp.view.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.fragmentapp.R;
import com.fragmentapp.view.CubicEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhen on 2018/1/24.
 */

public class SunHeadView extends View implements IHeadView{

    private int mWidth;
    private int mHeight;

    private Paint effectPaint,facePaint,clearPaint,defPaint;//这里定义多个属性都是为了能够自定义不同的样式
    private RectF rectF = null;

    private float angle,loadAngle = 45;

    private ValueAnimator faceVa,arcVa;

    private int left,top;

    private boolean isDraw = false;

    private Path path,foodPath;
    private PointF startPoint = null,movePoint1 = null,movePoint2 = null,endPoint = null;
    private List<PointF> clears = null;

    private PathEffect effect = null;

    private int faceRadius = 30,foodRadius = 3;

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

        foodPath = new Path();
        foodPath.addCircle(0, 0, foodRadius, Path.Direction.CCW);

        effectPaint = new Paint();
        effectPaint.setAntiAlias(true);
        effectPaint.setStyle(Paint.Style.STROKE);
        effectPaint.setColor(getResources().getColor(R.color.color_a9a05c));

        effect = new PathDashPathEffect(foodPath, 12, -1, PathDashPathEffect.Style.ROTATE);
        effectPaint.setPathEffect(effect);

        facePaint = new Paint();
        facePaint.setAntiAlias(true);
        facePaint.setStyle(Paint.Style.FILL);
        facePaint.setColor(getResources().getColor(R.color.color_a9a05c));

        defPaint = new Paint();
        defPaint.setAntiAlias(true);
        defPaint.setStyle(Paint.Style.FILL);
        defPaint.setColor(getResources().getColor(R.color.color_a9a05c));

        rectF = new RectF(0,0,0,0);
        startPoint = new PointF();
        movePoint1 = new PointF();
        movePoint2 = new PointF();
        endPoint = new PointF();

        clearPaint = new Paint();
        clearPaint.setAntiAlias(true);
        clearPaint.setStyle(Paint.Style.FILL);
        clearPaint.setColor(getResources().getColor(R.color.white));

        clears = new ArrayList<>();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();

            this.left = mWidth / 2;
            this.top = mHeight / 3;

            rectF.set(startPoint.x - faceRadius/2,startPoint.y - faceRadius,startPoint.x + faceRadius/2,startPoint.y);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!isDraw) return;
        //绘制“食物”
        foodPath.reset();
        foodPath.moveTo(startPoint.x,startPoint.y);
        foodPath.cubicTo(movePoint1.x,movePoint1.y,movePoint2.x,movePoint2.y,endPoint.x,endPoint.y);
        canvas.drawPath(foodPath, effectPaint);
        //绘制大球
        path.reset();
        path.moveTo(startPoint.x + faceRadius/2,startPoint.y);
        path.cubicTo(movePoint1.x,movePoint1.y + faceRadius/2,movePoint2.x,movePoint2.y + faceRadius/2,endPoint.x - faceRadius/2,endPoint.y);
        canvas.drawPath(path, defPaint);
        //吃掉“食物”
        for (PointF f : clears) {
            RectF rectF = new RectF(f.x-foodRadius*2,f.y-foodRadius*2,f.x+foodRadius*2,f.y+foodRadius*2);
            canvas.drawOval(rectF,clearPaint);
        }

        //绘制小球,需要在最后面绘制
        canvas.drawArc(rectF, angle, 360 - angle * 2, true, facePaint);

    }

    @Override
    public View getView() {
        return this;
    }

    /**开始动画*/
    public void upAnim(){
        if (faceVa != null)
            faceVa.cancel();
        faceVa = null;

        effectPaint.setColor(getResources().getColor(R.color.color_a9a05c));
        facePaint.setColor(getResources().getColor(R.color.color_a9a05c));
        clearPaint.setColor(getResources().getColor(R.color.white));
        defPaint.setColor(getResources().getColor(R.color.color_a9a05c));

        startPoint.set(mWidth*1/2 + faceRadius*2,mHeight + faceRadius);
        movePoint1.set(mWidth*2/3, 0);
        movePoint2.set(mWidth*5/6, 0);
        endPoint.set(mWidth - faceRadius*2,mHeight + faceRadius);

        clears.clear();

        CubicEvaluator bezierEvaluator = new CubicEvaluator(movePoint1,movePoint2);
        arcVa = ValueAnimator.ofObject(bezierEvaluator, startPoint, endPoint);
        arcVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//饶球移动
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PointF point = (PointF) valueAnimator.getAnimatedValue();
//                if (point.x + faceRadius <= endPoint.x)
                clears.add(new PointF(point.x,point.y));//保存移动的坐标
                //faceRadius/2是为了让小球的中心点刚好在大球的中间
                rectF.set(point.x - faceRadius/2,point.y - faceRadius/2,point.x + faceRadius/2,point.y + faceRadius/2);

                postInvalidate();
            }
        });
        arcVa.setInterpolator(new LinearInterpolator());
        arcVa.setDuration(2000);
        arcVa.setRepeatMode(ValueAnimator.RESTART);
        arcVa.start();

        rectF.set(startPoint.x - faceRadius/2,startPoint.y - faceRadius,startPoint.x + faceRadius/2,startPoint.y);
        angle = loadAngle;
        faceVa = ValueAnimator.ofFloat(loadAngle , 0);

        faceVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//吃食物动作
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                angle = (float)valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        faceVa.setInterpolator(new LinearInterpolator());
        faceVa.setDuration(500);
        faceVa.setRepeatMode(ValueAnimator.RESTART);
        faceVa.setRepeatCount(-1);
        faceVa.start();
    }

    @Override
    public void startAnim() {//前奏
        effectPaint.setColor(getResources().getColor(R.color.transparent));
        facePaint.setColor(getResources().getColor(R.color.transparent));
        clearPaint.setColor(getResources().getColor(R.color.transparent));

        isDraw = true;
        faceVa = ValueAnimator.ofFloat(0 , mHeight + faceRadius);//大球落下

        faceVa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {//吃食物动作
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float val = (float)valueAnimator.getAnimatedValue();

                startPoint.set(mWidth*1/2 + faceRadius*2,val);
                movePoint1.set(mWidth*2/3, 0);
                movePoint2.set(mWidth*5/6, 0);
                endPoint.set(mWidth - faceRadius*2,val);

                postInvalidate();
            }
        });
        faceVa.setInterpolator(new LinearInterpolator());
        faceVa.setDuration(1000);
        faceVa.setRepeatMode(ValueAnimator.RESTART);
        faceVa.start();
    }

    @Override
    public void stopAnim() {
        if (arcVa != null)
            arcVa.cancel();
        if (faceVa != null)
            faceVa.cancel();
        arcVa = null;
        faceVa = null;
        isDraw = false;
    }
}
