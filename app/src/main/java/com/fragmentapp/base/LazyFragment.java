package com.fragmentapp.base;

import android.util.Log;

/**
 * Created by liuzhen on 2017/11/10.
 */

public abstract class LazyFragment extends BaseFragment {

    protected boolean isLoad;//是否加载完

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

    protected void onVisible(){
        if(!isPrepared || !isVisible || !isLoad) {
            return;
        }
        init();
        Log.e("tag","init success");
    }

}
