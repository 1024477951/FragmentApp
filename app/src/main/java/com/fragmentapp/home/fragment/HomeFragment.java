package com.fragmentapp.home.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.home.bean.ChatBean;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.presenter.HomePresenter;
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration;

import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends LazyFragment implements IHomeView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private HomeAdapter adapter;

    private HomePresenter presenter;
    private int page = 1,lastPage = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

        tv_title.setText("消息");
        img_menu_icon.setImageResource(R.mipmap.de_ic_add);

        presenter = new HomePresenter(this);
        page = 1;

        adapter = new HomeAdapter(R.layout.item_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new PinnedHeaderItemDecoration.Builder(1)
                .setDividerId(R.drawable.shape_divider_999999)
                // Enable draw the separator line, by default it's disable.
                .enableDivider(true)
                .create());
        presenter.getArticleList(page);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                adapter.click(i);
            }
        });
    }

    @OnClick({R.id.menu})
    public void click(View view) {
        AlertFragment.newInstance(null).show(getChildFragmentManager(),TAG);
    }

    @Override
    public void success(List<ChatBean> list) {
        if (list.size() == 0){
            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        }else {
            page++;//如果有数据则+1下一页
            if (lastPage != page) {

            }
            lastPage = page;
            adapter.sortList(list);
        }

    }

    @Override
    public void error() {
        emptyLayout.showEmpty((ViewGroup) getView(),"error");
    }

    @Override
    public void loading() {
        showDialog();
    }

    @Override
    public void loadStop() {
        dismissDialog();
    }
}
