package com.fragmentapp.home.presenter;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.home.bean.ChatBean;
import com.fragmentapp.home.imple.IHomeModel;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.model.HomeModel;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

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
        List<ChatBean> list = new ArrayList<>();
        for (int i = 0;i<20;i++){
            ChatBean bean = new ChatBean();
            bean.setTop(i==5 ? 1 : 0);
            bean.setId(i);
            bean.setTime(System.currentTimeMillis());
            list.add(bean);
        }
        view.success(list);
    }



}
