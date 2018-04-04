package com.fragmentapp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.helper.AppManager;
import com.fragmentapp.helper.EmptyLayout;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/1.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public String TAG = getClass().getSimpleName();
    protected EmptyLayout emptyLayout;

    public abstract int layoutID();
    public abstract void init();

    protected Context context;
    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        AppManager.getAppManager().addActivity(this);
        context = this;
        ButterKnife.bind(this);
        emptyLayout = new EmptyLayout(this);
        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)//解决软键盘与底部输入框冲突问题
                .statusBarColor(R.color.color_3399ff);
        mImmersionBar.init();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        emptyLayout.clear();
        emptyLayout = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();
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
