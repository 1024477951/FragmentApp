package com.fragmentapp.view;

import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by liuzhen on 2018/2/2.
 */

public class MyBounceInterpolator implements Interpolator {

    private float bounce(float t) {
        return t * t * 8.0f;
    }

    @Override
    public float getInterpolation(float t) {
        Log.e("tag",""+t);
        t *= 1.1226f;
        if (t < 0.3535f) {
//            Log.e("tag","----1");
            return bounce(t);
        } else if (t < 0.7408f) {
//            Log.e("tag","----2");
            return bounce(t - 0.54719f) + 0.7f;
        } else if (t < 0.9644f) {
//            Log.e("tag","----3");
            return bounce(t - 0.8526f) + 0.9f;
        } else {
//            Log.e("tag","----4");
            return bounce(t - 1.0435f) + 0.95f;
        }
    }

}
