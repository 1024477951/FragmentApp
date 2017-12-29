package com.fragmentapp.login.presenter;

import android.util.Log;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.login.bean.LoginDataBean;
import com.fragmentapp.login.imple.ILoginModel;
import com.fragmentapp.login.imple.ILoginView;
import com.fragmentapp.login.model.LoginModel;

/**
 * Created by liuzhen on 2017/11/3.
 */

public class LoginPresenter extends BasePresenter {

    private ILoginView view;
    private ILoginModel model;

    public LoginPresenter(ILoginView view){
        this.view = view;
        model = new LoginModel();
    }

    public void login(String username,String pwd){
        model.login(observer,username,pwd);
    }

    BaseObserver<BaseResponses<LoginDataBean>> observer = new BaseObserver<BaseResponses<LoginDataBean>>(){

        @Override
        public void onNextResponse(BaseResponses<LoginDataBean> loginDataBean) {
            Log.e("token",loginDataBean.getData().getHCACCESSTOKEN()+"");
            view.success(loginDataBean.getData());
        }

        @Override
        public void onErrorResponse(BaseResponses<LoginDataBean> loginDataBean) {
            view.error();
        }

        @Override
        public void onNetWorkError(String val) {
            view.error();
        }
    };

}
