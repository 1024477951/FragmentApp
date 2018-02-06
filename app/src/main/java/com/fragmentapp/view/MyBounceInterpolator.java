package com.fragmentapp.view;

import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

/**
 * Created by liuzhen on 2018/2/2.
 */

public class MyBounceInterpolator implements Interpolator {

    private CallBack callBack;
    private boolean t_3 = false,t_7 = false,t_9 = false,t_1 = false;

    public MyBounceInterpolator(CallBack callBack){
        this.callBack = callBack;
    }

    private float bounce(float t) {
        return t * t * 8.0f;
    }

    @Override
    public float getInterpolation(float t) {
//        Log.e("tag",""+t);
        t *= 1.1226f;
        if (t < 0.3535f) {
//            Log.e("tag","----1");0.1 0.2 0.3
            if (t_3 == false){
                t_3 = true;
//                callBack.toLast();
            }
            return bounce(t);
        } else if (t < 0.7408f) {
//            Log.e("tag","----2");4=0.6 5=0.8 6=0.8
            if (t_7 == false){
                t_7 = true;
                callBack.toLast();
            }
            return bounce(t - 0.54719f) + 0.7f;
        } else if (t < 0.9644f) {
//            Log.e("tag","----3");7=0.8 8=0.9 9=1
            if (t_9 == false){
                t_9 = true;
                callBack.toLast();
            }
            return bounce(t - 0.8526f) + 0.9f;
        } else {
//            Log.e("tag","----4");
            if (t_1 == false){
                t_1 = true;
                callBack.toLast();
            }
            return bounce(t - 1.0435f) + 0.95f;
        }
    }

    public interface CallBack{
        void toLast();
    }

}
