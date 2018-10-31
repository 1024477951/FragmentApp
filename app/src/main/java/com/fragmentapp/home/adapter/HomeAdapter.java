package com.fragmentapp.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;
import com.fragmentapp.helper.RecyclerViewPositionHelper;
import com.fragmentapp.home.bean.HomeDataBean;
import com.orhanobut.logger.Logger;

import java.util.HashSet;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2017/11/20.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeDataBean, HomeAdapter.ViewHolder> {

    private HashSet<Integer> finds = new HashSet<>();

    public HomeAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(final ViewHolder holder, HomeDataBean bean) {
        holder.tv_name.setText("ID "+bean.getId());
        if (bean.getReadNum() > 0) {
            holder.tv_read.setVisibility(View.VISIBLE);
            holder.tv_read.setText(bean.getReadNum() + "");
        }else{
            holder.tv_read.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
    }

    public int findReadPosition(RecyclerView recyclerView){
        int position = 0;
        int size = getData().size();
        /** 列表中第一个可见的item position */
        int firstPosition =  RecyclerViewPositionHelper.createHelper(recyclerView).findFirstVisibleItemPosition();
//        int lastPosition =  RecyclerViewPositionHelper.createHelper(recyclerView).findLastVisibleItemPosition();

        /** 定位第一个未读消息的position */
        int lastNumPosition = -1,firstNumPosition = 0;
        for (int i = size - 1;i >= 0;i--){
            if (getData().get(i).getReadNum() > 0){
                lastNumPosition = i;
                break;
            }
        }
        for (int i = 0;i < size;i++){
            if (getData().get(i).getReadNum() > 0){
                firstNumPosition = i;
                break;
            }
        }
        /**************** end *****************/

        for (int i = 0;i < size;i++){
            int num = getData().get(i).getReadNum();
            /** 从可见的position开始往下查找 */
            if (i > firstPosition && num > 0 && finds.contains(i) == false) {
                position = i;

                //如果是定位的第一个，把finds集合清空，以控制定位到最后一个的时候上拉到上面在次点击定位到最后一个的时候会跳过最后一个到第一个，
                // 因为此时finds有值，然后又没有在次连续定位进入else页面clear数据，所以else里面的操作需要放到手动滑到上方定位时来处理
                finds.clear();

                if (lastNumPosition == i) {//如果定位到了最后一个未读position下标就添加到finds集合中，以控制下次在进入最后一个的时候会跳到第一个循环和重新定位
                    finds.add(i);
                }
                break;
            }else if (lastNumPosition == i && finds.contains(i)){//如果是已经定位到最后一条未读position了，返回第一条未读position
                finds.clear();
                position = firstNumPosition;
            }
        }
//        position = position + lastPosition - firstPosition;
        return position;
    }

    static class ViewHolder extends BaseViewHolder
    {

        TextView tv_content,tv_read,tv_name;
        View root;
        CircleImageView profile_image;

        public ViewHolder(View view)
        {
            super(view);
            tv_content = getView(R.id.tv_content);
            tv_read = getView(R.id.tv_read);
            tv_name = getView(R.id.tv_name);

            root = getView(R.id.root);

            profile_image = getView(R.id.profile_image);
        }
    }

}
