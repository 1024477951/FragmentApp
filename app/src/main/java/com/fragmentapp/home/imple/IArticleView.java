package com.fragmentapp.home.imple;

import com.fragmentapp.home.bean.ArticleDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface IArticleView {

    void success(List<ArticleDataBean> list);
    void error();

}
