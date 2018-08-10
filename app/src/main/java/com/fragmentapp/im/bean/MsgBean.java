package com.fragmentapp.im.bean;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class MsgBean {

    public static final int Text = 1;
    public static final int Photo = 2;
    public static final int Image_Text = 3;
    public static final int Event = 4;
    public static final int Voice = 5;
    public static final int TextImage = 6;
    public static final int File = 7;

    private int id;
    private int type;
    private boolean isSelf;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
