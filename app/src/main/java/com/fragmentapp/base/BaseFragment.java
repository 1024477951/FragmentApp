package com.fragmentapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liuzhen on 2017/11/8.
 */

public abstract class BaseFragment extends Fragment{

    protected abstract int getLayoutId();
    protected abstract void init();//显示加载

    public String TAG = getClass().getSimpleName();

    protected View contentView;

    protected boolean isVisible;
    // 标志位，标志已经初始化完成。
    protected boolean isPrepared;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("tag","onCreateView");
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup parent = (ViewGroup) contentView.getParent();
            if (parent != null) {
                parent.removeView(contentView);
            }
        }
        isPrepared = true;
        return contentView;
    }
}
