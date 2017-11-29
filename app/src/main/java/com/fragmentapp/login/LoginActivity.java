package com.fragmentapp.login;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.login.imple.ILoginView;
import com.fragmentapp.login.presenter.LoginPresenter;

/**
 * Created by liuzhen on 2017/11/7.
 */

public class LoginActivity extends BaseActivity implements ILoginView{

    @Override
    public int layoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void init() {
        new LoginPresenter(this).test();
    }

    @Override
    public void success() {

    }

    @Override
    public void error() {

    }
}
