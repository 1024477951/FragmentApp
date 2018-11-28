package com.fragmentapp.dynamic.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.IMFragment;
import com.fragmentapp.dynamic.DynamicDetailActivity;
import com.fragmentapp.dynamic.adapter.DynamicAdapter;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/23.
 */

public class DynamicFragment extends IMFragment implements OnRefreshLoadMoreListener {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DynamicAdapter adapter;
    private int page,count;

    @Override
    protected void init() {
        setTitleText("动态");
        adapter = new DynamicAdapter(R.layout.item_dynamic);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                DynamicDetailActivity.start(getContext());
            }
        });
        page = 1;
        count = 3;
        getList();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        if (page > count) {
            refreshLayout.finishLoadMoreWithNoMoreData();
        }else{
            getList();
            refreshLayout.finishLoadMore();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh();
        page = 1;
        getList();
    }

    private void getList(){
        List<String> items = new ArrayList<>();
        for(int i = 0;i< 5*page;i++){
            items.add(""+i);
        }
        adapter.setNewData(items);
    }

}
