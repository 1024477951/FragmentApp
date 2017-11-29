package com.fragmentapp.login.model;

import android.util.Log;

import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.http.RetrofitHelper;
import com.fragmentapp.login.bean.PersonBean;
import com.fragmentapp.login.imple.ILoginModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class LoginModel implements ILoginModel {

    @Override
    public void login(final BaseObserver<BaseResponses> observer) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "13429667914");
        map.put("key", "safly");
        RetrofitHelper.getInstance().getService()
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void save(final BaseObserver<BaseResponses> observer) {
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
