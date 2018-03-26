package com.fragmentapp.def.home.presenter;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.def.home.bean.ArticleDataBean;
import com.fragmentapp.def.home.imple.IArticleModel;
import com.fragmentapp.def.home.imple.IArticleView;
import com.fragmentapp.def.home.model.ArticleModel;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

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

    public void getArticleList(int page){
        view.loading();
        model.getArticleList(observer,page);
    }

    BaseObserver<BaseResponses<ArticleDataBean>> observer = new BaseObserver<BaseResponses<ArticleDataBean>>() {
        @Override
        public void onNextResponse(BaseResponses<ArticleDataBean> articleDataBeanBaseResponses) {
            view.loadStop();
            if (articleDataBeanBaseResponses.getData() != null && articleDataBeanBaseResponses.getData().getList() != null)
                view.success(articleDataBeanBaseResponses.getData().getList());
        }

        @Override
        public void onErrorResponse(BaseResponses<ArticleDataBean> articleDataBeanBaseResponses) {
            view.error();
            view.loadStop();
        }

        @Override
        public void onNetWorkError(String val) {
            view.error();
            view.loadStop();
        }
    };

}
