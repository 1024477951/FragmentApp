package com.fragmentapp.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.fragmentapp.R;
import com.fragmentapp.home.bean.ArticleDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context context;
    private int resource;
    private List<ArticleDataBean.ListBean> list;
    private LayoutInflater inflate;

    public ArticleAdapter(Context context, int resource, List<ArticleDataBean.ListBean> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflate.inflate(resource, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        ArticleDataBean.ListBean bean = list.get(position);

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

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ArticleDataBean.ListBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void addList(List<ArticleDataBean.ListBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_title,tv_content,tv_time,tv_home,tv_read;

        public ViewHolder(View view)
        {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            tv_content = view.findViewById(R.id.tv_content);
            tv_time = view.findViewById(R.id.tv_time);
            tv_home = view.findViewById(R.id.tv_home);
            tv_read = view.findViewById(R.id.tv_read);
        }
    }

}
