package com.fragmentapp.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fragmentapp.home.bean.ArticleDataBean;

import java.util.List;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context context;
    private int resource;
    private List<ArticleDataBean> list;
    private LayoutInflater inflate;

    public ArticleAdapter(Context context, int resource, List<ArticleDataBean> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(inflate.inflate(resource, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public void setList(List<ArticleDataBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv;

        public ViewHolder(View view)
        {
            super(view);
//            tv = view.findViewById(R.id.tv_val);
        }
    }

}
