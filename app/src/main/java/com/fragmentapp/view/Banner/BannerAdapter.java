package com.fragmentapp.view.Banner;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by liuzhen on 2018/3/7.
 */

public class BannerAdapter extends BaseQuickAdapter<BannerData, BannerAdapter.ViewHolder> {


    public BannerAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, BannerData bannerData) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    static class ViewHolder extends BaseViewHolder{

        public ViewHolder(View view) {
            super(view);
        }
    }

}
