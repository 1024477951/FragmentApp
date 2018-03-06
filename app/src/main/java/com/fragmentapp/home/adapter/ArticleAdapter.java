package com.fragmentapp.home.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.home.bean.ArticleDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class ArticleAdapter extends BaseQuickAdapter<ArticleDataBean.ListBean, ArticleAdapter.ViewHolder> {

    private Context context;

    public ArticleAdapter(int layoutResId,Context context) {
        super(layoutResId);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder holder, ArticleDataBean.ListBean bean) {
        holder.tv_title.setText(bean.getArticle_name());
        holder.tv_content.setText(bean.getArticle_info());
        holder.tv_time.setText(bean.getArticle_addtime());
        holder.tv_read.setText(bean.getArticle_view());
        holder.tv_home.setText(bean.getArticle_urlcom());
        //        GlideApp.with(AndroidApplication.getInstance().getApplicationContext())
//                .load(item.path)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .centerCrop()
//                .transform(new RoundedCorners(10))
//                .into(imageView);
    }

    public String getItemText(int position){
        return getData().get(position).getArticle_name();
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_title,tv_content,tv_time,tv_home,tv_read;

        public ViewHolder(View view)
        {
            super(view);
            tv_title = getView(R.id.tv_title);
            tv_content = getView(R.id.tv_content);
            tv_time = getView(R.id.tv_time);
            tv_home = getView(R.id.tv_home);
            tv_read = getView(R.id.tv_read);
        }
    }

}
