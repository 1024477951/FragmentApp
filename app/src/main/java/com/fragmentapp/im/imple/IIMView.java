package com.fragmentapp.im.imple;

import com.fragmentapp.im.bean.MsgBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface IIMView {

    void success(List<MsgBean> list);
    void error();

    void loading();
    void loadStop();
}
