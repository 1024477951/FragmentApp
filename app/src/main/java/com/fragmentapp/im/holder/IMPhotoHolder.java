package com.fragmentapp.im.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMPhotoHolder extends IMBaseHolder{

    protected ImageView iv_content;

    @Override
    protected void init(View view,boolean isSelef) {
        iv_content = getView(R.id.iv_content);
    }

    public IMPhotoHolder(View view) {
        super(view);

    }

    public void setContent(){

    }

}
