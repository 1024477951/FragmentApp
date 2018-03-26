package com.fragmentapp.def.home.imple;

import com.fragmentapp.def.home.bean.ArticleDataBean;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

/**
 * Created by liuzhen on 2017/11/7.
 */

public interface IArticleModel {

    void getArticleList(BaseObserver<BaseResponses<ArticleDataBean>> observer, int page);

}
