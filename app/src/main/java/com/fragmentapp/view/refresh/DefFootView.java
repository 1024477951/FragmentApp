package com.fragmentapp.view.refresh;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by liuzhen on 2017/11/24.
 */

public class DefFootView extends ProgressBar implements IFootView{

    public DefFootView(Context context) {
        this(context,null,android.R.attr.progressBarStyleSmallInverse);
    }

    public DefFootView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DefFootView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(Color.WHITE);
        init();
    }

    private void init(){

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void startAnim() {

    }

    @Override
    public void stopAnim() {

    }
}
