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
        if (page == 1) {
            bean = new MsgBean();
            bean.setId(0);
            bean.setType(MsgBean.Text);
            list.add(bean);

            bean = new MsgBean();
            bean.setId(1);
            bean.setType(MsgBean.Photo);
            bean.setSelf(true);
            list.add(bean);

            bean = new MsgBean();
            bean.setId(2);
            bean.setType(MsgBean.Photo);
            list.add(bean);

            bean = new MsgBean();
            bean.setId(3);
            bean.setType(MsgBean.Image_Text);
            bean.setSelf(true);
            list.add(bean);
            bean = new MsgBean();
            bean.setId(4);
            bean.setType(MsgBean.Image_Text);
            list.add(bean);

            bean = new MsgBean();
            bean.setId(5);
            bean.setType(MsgBean.Text);
            bean.setSelf(true);
            list.add(bean);
            bean = new MsgBean();
            bean.setId(6);
            bean.setType(MsgBean.Text);
            list.add(bean);

        }else{
            for (int i = 0; i < 4; i++) {
                bean = new MsgBean();
                bean.setId(i);
                bean.setType(MsgBean.Text);
                bean.setSelf(true);
                list.add(bean);
            }
        }
        view.success(list);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.loadStop();
            }
        },1000);
    }

}
