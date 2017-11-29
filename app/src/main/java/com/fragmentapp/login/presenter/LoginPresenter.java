package com.fragmentapp.login.presenter;

import com.fragmentapp.base.BasePresenter;
import com.fragmentapp.http.BaseResponses;
import com.fragmentapp.http.BaseObserver;
import com.fragmentapp.login.bean.PersonBean;
import com.fragmentapp.login.imple.ILoginModel;
import com.fragmentapp.login.imple.ILoginView;
import com.fragmentapp.login.model.LoginModel;

import rx.Observable;
import rx.Observer;

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

    public void test(){
        model.login(observer);
        PersonBean bean = new PersonBean();
        bean.setName("123");
        bean.setPwd("123");
        bean.setAccount("123");
        Observable.just(bean).subscribe((Observer<? super PersonBean>) observer);
    }

    BaseObserver<BaseResponses> observer = new BaseObserver<BaseResponses>() {
        @Override
        public void onNextResponse(BaseResponses baseResponses) {
            view.success();
        }

        @Override
        public void onErrorResponse(BaseResponses baseResponses) {
            view.error();
        }
    };

}
