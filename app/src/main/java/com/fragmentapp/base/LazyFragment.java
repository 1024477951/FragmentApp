package com.fragmentapp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.helper.EmptyLayout;
import com.fragmentapp.view.beans.LoadingFragment;
import com.fragmentapp.view.progress.Loadding;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by liuzhen on 2017/11/10.
 */

public abstract class LazyFragment extends BaseFragment {

    //-------toolbar-------
    /** left */
    @BindView(R.id.fl_left)
    public View fl_left;
    @BindView(R.id.tv_left_title)
    public TextView tv_left_title;
    @BindView(R.id.img_left_icon)
    public ImageView img_left_icon;

    /** center */
    @BindView(R.id.tv_title)
    public TextView tv_title;
    @BindView(R.id.img_triangle)
    public ImageView img_triangle;

    /** right */
    @BindView(R.id.tv_right_title)
    public TextView tv_right_title;
    @BindView(R.id.menu)
    public FrameLayout menu;
    @BindView(R.id.img_menu_icon)
    public ImageView img_menu_icon;
    //-------toolbar-------

    private boolean isLoad = false;//是否加载完
    private boolean isLazyLoad = true;

    protected EmptyLayout emptyLayout;
//    protected Loadding loadding;
    private LoadingFragment loadingFragment;

    protected Unbinder unbinder;

    private ImmersionBar mImmersionBar;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onVisible();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }
    /**可见时调用*/
    protected void onVisible(){
        if(isPrepared == false || isVisible == false || isLoad || isLazyLoad() == false) {
            return;
        }
        emptyLayout = new EmptyLayout(getActivity());//初始化空页面布局
        loadingFragment = LoadingFragment.newInstance();

        mImmersionBar = ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.color_3399ff)
                .keyboardEnable(true)
                .navigationBarWithKitkatEnable(false);
        mImmersionBar.init();

        unbinder = ButterKnife.bind(this, contentView);
        init();
        isLoad = true;//加载完后更改状态，只限定加载一次
    }

    /**
     * 是否懒加载
     */
    protected boolean isLazyLoad() {
        return isLazyLoad;
    }

    protected void setLazyLoad(boolean b){
        isLazyLoad = b;
    }

    protected void showDialog(){
        if (loadingFragment.isVisible() == false){
            loadingFragment.show(getFragmentManager(),TAG);
        }
    }
    protected void dismissDialog(){
        if (loadingFragment.isVisible() == true){
            loadingFragment.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (emptyLayout != null)
            emptyLayout.clear();
        emptyLayout = null;
        loadingFragment = null;
        if (mImmersionBar != null)
            mImmersionBar.destroy();
        unbinder.unbind();
    }
}
