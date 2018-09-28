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

    //-------toolbar-------
    /** left */
    @Nullable @BindView(R.id.fl_left)
    public View fl_left;
    @Nullable @BindView(R.id.tv_left_title)
    public TextView tv_left_title;
    @Nullable @BindView(R.id.tv_read)
    public TextView tv_read;
    @Nullable @BindView(R.id.circle_read)
    public View circle_read;
    @Nullable @BindView(R.id.img_left_icon)
    public ImageView img_left_icon;

    /** center */
    @Nullable @BindView(R.id.tv_title)
    public TextView tv_title;

    /** right */
    @Nullable @BindView(R.id.tv_right_title)
    public TextView tv_right_title;
    @Nullable @BindView(R.id.menu)
    public FrameLayout menu;
    @Nullable @BindView(R.id.img_menu_icon)
    public ImageView img_menu_icon;
    //-------toolbar-------

    public String TAG = getClass().getSimpleName();
    protected EmptyLayout emptyLayout;

    public abstract int layoutID();
    public abstract void init();

    protected Context context;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutID());
        AppManager.getAppManager().addActivity(this);
        context = this;
        ButterKnife.bind(this);
        emptyLayout = new EmptyLayout(this);
        View view = findViewById(R.id.view_status);
        mImmersionBar = ImmersionBar.with(this);
        if (view != null) {
            mImmersionBar.fitsSystemWindows(false)
                    .statusBarView(view);
        }
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
        context = null;
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

    protected void setTitleText(String title){
        tv_title.setText(title);
    }

    protected void setTitleText(String title,boolean isGoneTriangle){
        tv_title.setText(title);
    }

    protected void setTitleRightText(String title){
        tv_right_title.setVisibility(View.VISIBLE);
        tv_right_title.setText(title);

        menu.setVisibility(View.GONE);
    }

    protected void isVisibleLeftBack(boolean b){
        if (b) {
            img_left_icon.setVisibility(View.VISIBLE);
        }else{
            img_left_icon.setVisibility(View.GONE);
        }
    }

    protected void setTitleRightMenu(int res){
        img_menu_icon.setImageResource(res);
        img_menu_icon.setVisibility(View.VISIBLE);

        tv_right_title.setVisibility(View.GONE);
    }

    protected void setTitleLeftText(String title){
        tv_left_title.setVisibility(View.VISIBLE);
        tv_left_title.setText(title);

        tv_read.setVisibility(View.GONE);
        circle_read.setVisibility(View.GONE);
    }

    protected void setTitleReadNum(String num){
        tv_read.setText(num);
        tv_read.setVisibility(View.VISIBLE);
        circle_read.setVisibility(View.VISIBLE);

        tv_left_title.setVisibility(View.GONE);
    }

}
