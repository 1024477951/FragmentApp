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

    public void login(){
        model.login(loginObserver,"s645","123456");
    }

    public void register(){
        model.register(registerObserver);
    }

    BaseObserver<BaseResponses<LoginDataBean>> loginObserver = new BaseObserver<BaseResponses<LoginDataBean>>(){

        @Override
        public void onNextResponse(BaseResponses<LoginDataBean> loginDataBean) {
            Log.e("token",loginDataBean.getData().getHCACCESSTOKEN());
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

    BaseObserver<BaseResponses> registerObserver = new BaseObserver<BaseResponses>(){

        @Override
        public void onNextResponse(BaseResponses responses) {
            view.register();
        }

        @Override
        public void onErrorResponse(BaseResponses responses) {
            view.error();
        }

        @Override
        public void onNetWorkError(String val) {
            view.error();
        }
    };

}
