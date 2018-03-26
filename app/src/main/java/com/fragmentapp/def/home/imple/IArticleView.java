package com.fragmentapp.def.home.imple;

import com.fragmentapp.def.home.bean.ArticleDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface IArticleView {

    void success(List<ArticleDataBean.ListBean> list);
    void error();

    void loading();
    void loadStop();
}
