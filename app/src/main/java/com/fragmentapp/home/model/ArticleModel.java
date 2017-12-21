package com.fragmentapp.home.model;

import android.util.Log;

import com.fragmentapp.home.bean.ArticleDataBean;
import com.fragmentapp.home.imple.IArticleModel;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.http.RetrofitHelper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class ArticleModel implements IArticleModel {

    @Override
    public void getArticleList() {
        Map<String, String> map = new HashMap<>();
        map.put("id", "2");
        map.put("p", "1");
        RetrofitHelper.getInstance().getService()
                .getArticleList(map)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleDataBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("onSubscribe",d.toString());
                    }

                    @Override
                    public void onNext(ArticleDataBean articleDataBean) {
                        Log.e("onNext",articleDataBean.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete","--------------");
                    }
                });
    }
}
