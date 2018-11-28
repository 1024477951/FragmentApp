package com.fragmentapp.search.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.home.bean.HomeDataBean;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class SearchAdapter extends BaseQuickAdapter<HomeDataBean, SearchAdapter.ViewHolder> {

    public SearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final ViewHolder holder, HomeDataBean bean) {
        holder.tv_content.setText("Body ID "+bean.getId());
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_content;

        public ViewHolder(View view)
        {
            super(view);
            tv_content = getView(R.id.body);
        }
    }

}
