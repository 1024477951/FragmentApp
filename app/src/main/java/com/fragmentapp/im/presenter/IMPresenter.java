package com.fragmentapp.im.presenter;

import android.os.Handler;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.im.bean.MsgBean;
import com.fragmentapp.im.imple.IIMView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMPresenter extends BasePresenter {

    private IIMView view;

    public IMPresenter(IIMView view){
        this.view = view;
    }

    public void getList(int page){
        List<MsgBean> list = new ArrayList<>();
        MsgBean bean = null;
        for (int i = 0;i < 6;i++){
            bean = new MsgBean();
            bean.setType(MsgBean.Text);
            if (i % 2 == 0){
                bean.setSelf(true);
            }
            list.add(bean);
        }
        bean = new MsgBean();
        bean.setType(MsgBean.Photo);
        bean.setSelf(true);
        list.add(bean);

        bean = new MsgBean();
        bean.setType(MsgBean.Photo);
        list.add(bean);

        bean = new MsgBean();
        bean.setType(MsgBean.Image_Text);
        bean.setSelf(true);
        list.add(bean);
        bean = new MsgBean();
        bean.setType(MsgBean.Image_Text);
        list.add(bean);

        view.success(list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.loadStop();
            }
        },1000);
    }

}
