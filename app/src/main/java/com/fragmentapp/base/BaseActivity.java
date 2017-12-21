package com.fragmentapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fragmentapp.helper.AppManager;

import butterknife.ButterKnife;

/**
 * Created by liuzhen on 2017/11/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getSimpleName();

    public abstract int layoutID();
    public abstract void init();

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        AppManager.getAppManager().addActivity(this);
        context = this;
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
    }

    protected <T> T cover(Object t){
        return (T)t;
    }

    protected boolean isNull(String... param){
        boolean b = false;
        for (String p : param) {
            if (p == null || p.isEmpty())
                b = true;
        }
        return b;
    }
    protected boolean isNull(Object... param){
        boolean b = false;
        for (Object p : param) {
            if (p == null)
                b = true;
        }
        return b;
    }

}
