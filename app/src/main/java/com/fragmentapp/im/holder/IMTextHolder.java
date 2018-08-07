package com.fragmentapp.im.holder;

import android.view.View;
import android.widget.TextView;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMTextHolder extends IMBaseHolder{

    protected TextView tv_content;

    @Override
    protected void init(View view,boolean isSelef) {
        tv_content = getView(R.id.tv_content);
        int txtColor;
        if (isSelef){
            txtColor = view.getContext().getResources().getColor(R.color.color_ffffff);
        }else{
            txtColor = view.getContext().getResources().getColor(R.color.color_4b4b4b);
        }
        tv_content.setTextColor(txtColor);
    }
    /** 此处参数为 baseHolder view */
    public IMTextHolder(View view) {
        super(view);
    }

    public void setContent(String val){
        tv_content.setText(val);
    }

}
