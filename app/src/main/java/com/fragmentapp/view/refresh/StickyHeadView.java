package com.fragmentapp.view.refresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/12/4.
 */

public class StickyHeadView extends View implements IHeadView {

    private Paint mPaint;
    private int bg;
    private float mStaicRadius = 20f;// 直径
    private float mMoveRadius = 20f;// 直径

    // 存储静态的两个点
    private PointF[] mStaticPointFs = new PointF[2];
    // 存储移动的两个点
    private PointF[] mMovewPointFs = new PointF[2];
    // 控制点
    private PointF mControlPointF = new PointF();
    // 静态点
    private PointF staticCenterPointF = null;
    // 移动点
    private PointF movewCenterPointF = null;

    private boolean isStart = false;

    public StickyHeadView(Context context) {
        this(context, null, 0);
    }

    public StickyHeadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg = getResources().getColor(R.color.color_8babaf);
        mPaint.setColor(bg);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            staticCenterPointF = new PointF(getWidth() / 2, getHeight()/4);
            movewCenterPointF = new PointF(getWidth() / 2, getHeight()/4);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStart) {
            // 1、获得偏移量
            float yOffset = staticCenterPointF.y - movewCenterPointF.y;
            float xOffset = staticCenterPointF.x - movewCenterPointF.x;
            // 2、有了偏移量就可以求出两点斜率了
            Double lineK = 0.0;
            if (xOffset != 0f) {
                lineK = (double) (yOffset / xOffset);
            }
            // 3、通过工具求得两个点的集合
            mMovewPointFs = getIntersectionPoints(movewCenterPointF, mMoveRadius, lineK);
            mStaticPointFs = getIntersectionPoints(staticCenterPointF, mStaicRadius, lineK);
            // 4、通过公式求得控制点
            mControlPointF = getMiddlePoint(staticCenterPointF, movewCenterPointF);

            // 保存画布状态，保存方法之后的代码，能够调用Canvas的平移、放缩、旋转、裁剪等操作
//        canvas.save();

            // 工型绘制
            Path path = new Path();
            // 左上角点
            path.moveTo(mStaticPointFs[0].x, mStaticPointFs[0].y);
            // 上一边的弯度和右上角点
            path.quadTo(mControlPointF.x, mControlPointF.y, mMovewPointFs[0].x, mMovewPointFs[0].y);
            // 右下角点
            path.lineTo(mMovewPointFs[1].x, mMovewPointFs[1].y);
            // 下一边的弯度和左下角点
            path.quadTo(mControlPointF.x, mControlPointF.y, mStaticPointFs[1].x, mStaticPointFs[1].y);
            // 关闭后，会回到最开始的地方，形成封闭的图形
            path.close();

            canvas.drawPath(path, mPaint);

            canvas.drawCircle(staticCenterPointF.x, staticCenterPointF.y, mStaicRadius, mPaint);
//        // 画移动的大圆
            canvas.drawCircle(movewCenterPointF.x, movewCenterPointF.y, mMoveRadius, mPaint);
        }
    }
    /**拉扯移动点*/
    public void move(float downY) {
        //以中间点为定点
        updateMoveCenter(getWidth() / 2 + downY, movewCenterPointF.y);
        updateStaticCenter(getWidth() / 2 - downY, staticCenterPointF.y);
    }


    /**
     * 更新移动的点
     */
    private void updateMoveCenter(float downX, float downY) {
        movewCenterPointF.set(downX, downY);
        invalidate();
    }

    private void updateStaticCenter(float downX, float downY) {
        staticCenterPointF.set(downX, downY);
        invalidate();
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void startAnim() {
        isStart = true;
        bg = getResources().getColor(R.color.color_8b90af);
        mPaint.setColor(bg);
        //甩动动画
        ValueAnimator vAnim = ValueAnimator.ofFloat(-30,0,50,0);
        vAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                float percent = animation.getAnimatedFraction();
                float val = (float)animation.getAnimatedValue();
//                Log.e("tag","percent "+percent + " percent*val "+percent*val);
                updateMoveCenter(getWidth() / 2 + val, movewCenterPointF.y);
                updateStaticCenter(getWidth() / 2 - val, staticCenterPointF.y);
            }
        });
        vAnim.setInterpolator(new OvershootInterpolator(2));//甩动差值器
        vAnim.setDuration(1000);
        vAnim.start();
    }

    @Override
    public void stopAnim() {
        isStart = false;
        bg = getResources().getColor(R.color.color_8babaf);
        mPaint.setColor(bg);
        invalidate();
    }

    /**
     * Get the point of intersection between circle and line. 获取
     * 通过指定圆心，斜率为lineK的直线与圆的交点。
     */
    private PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];

        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);

        return points;
    }


    /**
     * Get middle point between p1 and p2. 获得两点连线的中点
     */
    private PointF getMiddlePoint(PointF p1, PointF p2) {
        return new PointF((p1.x + p2.x) / 2.0f, (p1.y + p2.y) / 2.0f);
    }

}
