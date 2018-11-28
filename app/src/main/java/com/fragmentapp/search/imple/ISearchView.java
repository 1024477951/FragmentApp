package com.fragmentapp.search.imple;

import com.fragmentapp.home.bean.HomeDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/6.
 */

public interface ISearchView {

    void success(List<HomeDataBean> list);
    void error();

    void loading();
    void loadStop();
}
