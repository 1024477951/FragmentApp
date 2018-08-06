package com.fragmentapp.im.bean;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class MsgBean {

    public static final int Text = 1;
    public static final int Photo = 2;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
