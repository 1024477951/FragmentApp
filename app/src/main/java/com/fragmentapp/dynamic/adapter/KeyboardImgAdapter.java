package com.fragmentapp.dynamic.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;

/**
 * Created by liuzhen on 2018/11/28.
 */

public class KeyboardImgAdapter extends BaseQuickAdapter<String,BaseViewHolder>{



    public KeyboardImgAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final BaseViewHolder helper, String item) {
        helper.getView(R.id.iv_del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(helper.getAdapterPosition());
            }
        });
    }
}
