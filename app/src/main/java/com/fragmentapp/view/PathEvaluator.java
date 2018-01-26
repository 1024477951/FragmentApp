package com.fragmentapp.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by liuzhen on 2018/1/24.
 */

public class PathEvaluator implements TypeEvaluator<PointF> {

    private PointF mControlPoint1,mControlPoint2;

//    public PathEvaluator(PointF controlPoint) {
//        this.mControlPoint1 = controlPoint;
//    }
    public PathEvaluator(PointF controlPoint1,PointF controlPoint2) {
        this.mControlPoint1 = controlPoint1;
        this.mControlPoint2 = controlPoint2;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForCubic(t, startValue, mControlPoint1,mControlPoint2, endValue);
    }

}
