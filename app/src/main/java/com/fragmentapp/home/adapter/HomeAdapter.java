package com.fragmentapp.home.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class HomeAdapter extends BaseQuickAdapter<String, HomeAdapter.ViewHolder> {

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final ViewHolder holder, final String bean) {

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_title,tv_content,tv_time,tv_tag,tv_read;
        View root;
        CircleImageView profile_image;

        public ViewHolder(View view)
        {
            super(view);
            tv_title = getView(R.id.tv_title);
            tv_content = getView(R.id.tv_content);
            tv_time = getView(R.id.tv_time);
            tv_tag = getView(R.id.tv_tag);
            tv_read = getView(R.id.tv_read);

            root = getView(R.id.root);

            profile_image = getView(R.id.profile_image);
        }
    }

}
