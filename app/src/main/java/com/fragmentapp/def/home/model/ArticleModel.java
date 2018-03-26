package com.fragmentapp.def.home.model;

import android.util.Log;

import com.fragmentapp.def.home.bean.ArticleDataBean;
import com.fragmentapp.def.home.imple.IArticleModel;
import com.fragmentapp.helper.SharedPreferencesUtils;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.http.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class ArticleModel implements IArticleModel {

    @Override
    public void getArticleList(BaseObserver<BaseResponses<ArticleDataBean>> observer, int page) {
        Map<String, Integer> map = new HashMap<>();
        map.put("id", 2);
        map.put("p", page);

        String token = SharedPreferencesUtils.getParam("token");
        if (token == null) {
            Log.e("HomeModel","token is null");
            observer.onErrorResponse(null);
            return;
        }
        RetrofitHelper.getInstance().getService()
                .getArticleList(token,map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
