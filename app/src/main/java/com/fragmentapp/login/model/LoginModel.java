package com.fragmentapp.login.model;

import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.http.RetrofitHelper;
import com.fragmentapp.login.bean.LoginDataBean;
import com.fragmentapp.login.imple.ILoginModel;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class LoginModel implements ILoginModel {

    @Override
    public void login(final BaseObserver<BaseResponses<LoginDataBean>> observer,String username,String pwd) {
//        Map<String, String> map = new HashMap<>();
//        map.put("HC-ACCESS-TOKEN", username);
//        map.put("account[password]", pwd);
//        RetrofitHelper.getInstance().getService()
//                .register(map)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
    }

}
