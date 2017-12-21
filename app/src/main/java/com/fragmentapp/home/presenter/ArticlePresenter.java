package com.fragmentapp.home.presenter;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.home.bean.ArticleDataBean;
import com.fragmentapp.home.imple.IArticleModel;
import com.fragmentapp.home.imple.IArticleView;
import com.fragmentapp.home.model.ArticleModel;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

import io.reactivex.Observable;

/**
 * Created by liuzhen on 2017/12/20.
 */

public class ArticlePresenter extends BasePresenter {

    private IArticleView view;
    private IArticleModel model;

    public ArticlePresenter(IArticleView view){
        this.view = view;
        model = new ArticleModel();
    }

    public void getArticleList(){
        model.getArticleList();
    }


}
