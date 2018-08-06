package com.fragmentapp.im.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMEntity implements MultiItemEntity {

    public static final int Send = 1;
    public static final int Receive = 2;
    private int itemType;

    public IMEntity(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}

