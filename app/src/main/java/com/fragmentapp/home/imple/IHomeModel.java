package com.fragmentapp.home.imple;

import com.fragmentapp.home.bean.HomeDataBean;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;

/**
 * Created by liuzhen on 2017/11/7.
 */

public interface IHomeModel {

    void getArticleList(BaseObserver<BaseResponses<HomeDataBean>> observer, int page);

}
