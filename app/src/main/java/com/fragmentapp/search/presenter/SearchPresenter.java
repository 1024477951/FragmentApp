package com.fragmentapp.search.presenter;

import android.os.Handler;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.home.bean.HomeDataBean;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.search.imple.ISearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhen on 2017/12/20.
 */

public class SearchPresenter extends BasePresenter {

    private ISearchView view;

    public SearchPresenter(ISearchView view){
        this.view = view;
    }

    public void getArticleList(int page){
        List<HomeDataBean> list = new ArrayList<>();
        HomeDataBean bean = null;
        for (int i = 0;i < 3;i++){
            bean = new HomeDataBean();
            bean.setId(i);
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
