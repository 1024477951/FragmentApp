package com.fragmentapp.home.model;

import com.fragmentapp.home.imple.IMainModel;
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

public class MainModel implements IMainModel{

    @Override
    public void login(BaseObserver<BaseResponses> observer) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13429667914");
        map.put("key", "safly");
        RetrofitHelper.getInstance().getService()
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
