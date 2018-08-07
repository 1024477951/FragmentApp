package com.fragmentapp;

import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.login.LoginActivity;

/**
 * Created by liuzhen on 2018/8/7.
 */

public class SplashActivity extends BaseActivity{
    @Override
    public int layoutID() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        LoginActivity.start(context);
        finish();
    }
}
