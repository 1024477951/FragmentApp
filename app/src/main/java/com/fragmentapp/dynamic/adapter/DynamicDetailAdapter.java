package com.fragmentapp.dynamic.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fragmentapp.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by liuzhen on 2018/11/23.
 */

public class DynamicDetailAdapter extends BaseQuickAdapter<String,DynamicDetailAdapter.ViewHolder>{

    public DynamicDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(ViewHolder helper, String item) {
        helper.tv_name.setText("name "+item);
    }

    static class ViewHolder extends BaseViewHolder{

        CircleImageView author;
        TextView tv_name;

        RecyclerView recyclerView;
        private DynamicItemAdapter adapter;

        public ViewHolder(View view) {
            super(view);
            author = view.findViewById(R.id.iv_author);
            tv_name = view.findViewById(R.id.tv_name);

            recyclerView = view.findViewById(R.id.recyclerView);
            adapter = new DynamicItemAdapter(R.layout.item_dynamic_item);
            recyclerView.setAdapter(adapter);
            List<String> items = new ArrayList<>();
            for(int i = 0;i< 5;i++){
                items.add(""+i);
            }
            adapter.setNewData(items);
        }
    }

}
