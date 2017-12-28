package com.fragmentapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fragmentapp.helper.EmptyLayout;

/**
 * Created by liuzhen on 2017/11/10.
 */

public abstract class LazyFragment extends BaseFragment {

    protected boolean isLoad = false;//是否加载完

    protected EmptyLayout emptyLayout;

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
        if(!isPrepared || !isVisible || isLoad) {
            return;
        }
        init();
        isLoad = true;//加载完后更改状态，只限定加载一次
        emptyLayout = new EmptyLayout(getActivity());
//        Log.e("tag","onVisible");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        emptyLayout.clear();
        emptyLayout = null;
    }
}
