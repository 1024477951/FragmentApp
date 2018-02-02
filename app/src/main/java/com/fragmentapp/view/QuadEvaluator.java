package com.fragmentapp.view;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by liuzhen on 2018/1/24.
 */

public class QuadEvaluator implements TypeEvaluator<PointF> {

    private PointF mControlPoint;

    public QuadEvaluator(PointF controlPoint) {
        this.mControlPoint = controlPoint;
    }

    @Override
    public PointF evaluate(float t, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(t, startValue, mControlPoint, endValue);
    }

}
