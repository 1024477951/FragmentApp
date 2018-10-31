package com.fragmentapp.home.presenter;

import android.os.Handler;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.home.bean.HomeDataBean;
import com.fragmentapp.home.imple.IHomeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhen on 2017/12/20.
 */

public class HomePresenter extends BasePresenter {

    private IHomeView view;

    public HomePresenter(IHomeView view){
        this.view = view;
    }

    public void getArticleList(int page){
        List<HomeDataBean> list = new ArrayList<>();
        HomeDataBean bean = null;
        for (int i = 0;i < 21;i++){
            bean = new HomeDataBean();
            bean.setId(i);
            if (i == 10 || i == 2 || i == 5 || i == 20){
                bean.setReadNum(3);
            }
            list.add(bean);
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
