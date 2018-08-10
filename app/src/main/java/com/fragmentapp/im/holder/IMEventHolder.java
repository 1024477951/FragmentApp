package com.fragmentapp.im.holder;

import android.view.View;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.im.bean.MsgBean;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class IMEventHolder extends IMBaseHolder{

    protected TextView tv_event;

    @Override
    protected void init(boolean isSelef) {

    }

    @Override
    protected void status(int status) {

    }

    /** 此处参数为 baseHolder view */
    public IMEventHolder(View view) {
        super(view);
        tv_event = getView(R.id.tv_event);
    }

    @Override
    public void content(MsgBean item){
        if (item.getType() == MsgBean.Event) {
            tv_event.setText("xxxxx");
        } else {
            tv_event.setText("你撤回了一条消息");
        }
    }

}
