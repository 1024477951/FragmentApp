package com.fragmentapp.login.imple;

import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.login.bean.LoginDataBean;

import io.reactivex.Observable;

/**
 * Created by liuzhen on 2017/11/7.
 */

public interface ILoginModel {

    void login(BaseObserver<BaseResponses<LoginDataBean>> observer,String username,String pwd);

}
