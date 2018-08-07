package com.fragmentapp.im.bean;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class MsgBean {

    public static final int Text = 1;
    public static final int Photo = 2;
    public static final int Image_Text = 3;

    private int type;
    private boolean isSelf;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }
}
