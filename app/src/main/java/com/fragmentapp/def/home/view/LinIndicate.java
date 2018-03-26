package com.fragmentapp.def.home.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by liuzhen on 2017/10/25.
 */

public class LinIndicate extends LinearLayout {


    public LinIndicate(Context context) {
        super(context);
    }

    public LinIndicate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinIndicate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void load(int count){
        removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(10,10,10,20);
            addView(imageView);
        }
    }

    /**
     * @param count 指示器数量
     * @param position 默认显示下标
     */
    public void load(int count,int position){
        removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView imageView = new ImageView(getContext());
            ViewGroup.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(params);
            imageView.setPadding(10,10,10,20);
            addView(imageView);
        }
        setIndicatePosition(position);
    }

    public void setIndicatePosition(int position){
        int count = getChildCount();
        if (position > count)
            return;
//        for (int i = 0; i < count; i++) {
//            ImageView imageView = (ImageView) getChildAt(i);
//            if (position == i){
//                imageView.setImageResource(R.mipmap.ic_page_indicator_focused);
//            }else{
//                imageView.setImageResource(R.mipmap.ic_page_indicator);
//            }
//        }
    }

}
