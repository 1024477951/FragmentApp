package com.fragmentapp.dynamic.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/11/26.
 */

public class DynamicItemAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public DynamicItemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dynamic_item, parent, false);
        BaseViewHolder holder = new BaseViewHolder(view);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                parent.getContext().getResources().getDimensionPixelOffset(R.dimen.d180_0),
                parent.getContext().getResources().getDimensionPixelOffset(R.dimen.d180_0)
        );
        view.setLayoutParams(lp);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
