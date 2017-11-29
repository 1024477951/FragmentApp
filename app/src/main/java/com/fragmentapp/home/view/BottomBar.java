package com.fragmentapp.home.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class BottomBar extends LinearLayout {


    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setOrientation(HORIZONTAL);
    }

    public void load(String [] titles,int [] colors,int position){
        removeAllViews();
        for (int i = 0; i < titles.length; i++) {
            addView(getGroup(titles[i],colors[i]));
        }
        setIndicatePosition(position);
    }

    private View getGroup(String title,int color){
        RadioButton button = new RadioButton(getContext());
        ViewGroup.LayoutParams params = new LayoutParams(R.dimen.dp_0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        button.setLayoutParams(params);
        button.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(color), null, null);
        button.setText(title);
        button.setButtonDrawable(null);
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setTextColor(Color.GRAY);
        return button;
    }

    public void setIndicatePosition(int position){
        int count = getChildCount();
        if (position > count)
            return;
        for (int i = 0; i < count; i++) {
            RadioButton button = (RadioButton) getChildAt(i);
            if (position == i){
                button.setChecked(true);
            }else{
                button.setChecked(false);
            }
        }
    }

}
