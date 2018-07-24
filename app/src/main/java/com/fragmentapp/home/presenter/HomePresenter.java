package com.fragmentapp.home.presenter;

import android.os.Handler;

import com.fragmentapp.base.BasePresenter;
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
        List<String> list = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            list.add(""+(i+page));
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
