package com.fragmentapp.view.bounce;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.CompoundButton;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/11/14.
 */

public class BounceView extends android.support.v7.widget.AppCompatCheckBox{

    public BounceView(Context context) {
        super(context);
        init();
    }

    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    startAnimation();
            }
        });
    }

    public void startAnimation() {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        int topMargin = lp.topMargin;
        ValueAnimator anim = ValueAnimator.ofInt(-10,0+topMargin,6,0+topMargin);

        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int curValue = (int) animation.getAnimatedValue();
                layout(
                        getLeft(),
                        curValue+((ViewGroup)getParent()).getPaddingTop(),
                        getRight(),
                        curValue+getHeight()+((ViewGroup)getParent()).getPaddingBottom()
                );
            }
        });
        anim.setInterpolator(new BounceInterpolator());//反弹
        anim.setDuration(500);
        anim.start();
    }

}
