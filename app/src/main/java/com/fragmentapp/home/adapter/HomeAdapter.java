package com.fragmentapp.home.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.home.bean.ChatBean;
import com.fragmentapp.view.remove.SwipeItemLayout;

import java.util.Collections;
import java.util.List;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class HomeAdapter extends BaseQuickAdapter<ChatBean, HomeAdapter.ViewHolder> {

    private int topPosition = -1;

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final ViewHolder holder, final ChatBean bean) {
        if (bean.getTop() == 1){
            holder.root.setBackgroundResource(R.color.color_EEEEEE);
            holder.top.setText("取消置顶");
        }else{
            holder.root.setBackgroundResource(R.color.white);
            holder.top.setText("置顶");
        }
        holder.tv_title.setText("00"+bean.getId()+"号");
        holder.tv_content.setText("大家好，我是00"+bean.getId()+"号,我的top是"+bean.getTop());
        holder.tv_time.setText(bean.getTime()+"");
        //        GlideApp.with(AndroidApplication.getInstance().getApplicationContext())
//                .load(item.path)
//                .skipMemoryCache(true)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .centerCrop()
//                .transform(new RoundedCorners(10))
//                .into(imageView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        final ChatBean bean = getItem(position);

        holder.item_layout.setSwipeAble(true);
        holder.item_layout.setDelegate(new SwipeItemLayout.SwipeItemLayoutDelegate() {
            @Override
            public void onSwipeItemLayoutOpened(SwipeItemLayout swipeItemLayout) {


            }

            @Override
            public void onSwipeItemLayoutClosed(SwipeItemLayout swipeItemLayout) {
                if (topPosition >= 0) {
                    if (bean.getTop() == 0){
                        bean.setTime(System.currentTimeMillis());
                        setTop(position,1);
                    }else{
                        setTop(position,0);
                    }
                    topPosition = -1;
                }
            }

            @Override
            public void onSwipeItemLayoutStartOpen(SwipeItemLayout swipeItemLayout) {

            }
        });

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.item_layout.closeWithAnim();
            }
        });
        holder.top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (topPosition == -1) {
                    holder.item_layout.closeWithAnim();
                    topPosition = position;
                }
            }
        });
    }

    private void setTop(int position,int top){
        mData.get(position).setTop(top);
        Collections.sort(mData);
        notifyDataSetChanged();
    }

    public void click(int position){
        mData.get(position).setTime(System.currentTimeMillis());
        Collections.sort(mData);
        notifyDataSetChanged();
    }

    public List<ChatBean> sortList(List<ChatBean> list){
        Collections.sort(list);
        setNewData(list);
        return list;
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_title,tv_content,tv_time,tv_home,tv_read,del,top;
        SwipeItemLayout item_layout;
        View root;

        public ViewHolder(View view)
        {
            super(view);
            tv_title = getView(R.id.tv_title);
            tv_content = getView(R.id.tv_content);
            tv_time = getView(R.id.tv_time);
            tv_home = getView(R.id.tv_home);
            tv_read = getView(R.id.tv_read);

            item_layout = getView(R.id.item_layout);

            root = getView(R.id.root);

            del = getView(R.id.item_contact_delete);
            top = getView(R.id.item_contact_top);

        }
    }

}
