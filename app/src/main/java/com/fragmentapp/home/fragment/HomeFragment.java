package com.fragmentapp.home.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.EmptyLayout;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.presenter.HomePresenter;
import com.fragmentapp.view.refresh.DefFootView;
import com.fragmentapp.view.refresh.DefHeaderView;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
import com.fragmentapp.view.refresh.SunHeadView;
import com.fragmentapp.view.refresh.TextHeadView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends LazyFragment implements IHomeView {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TextHeadView textHeadView;
    private DownHeadView downHeadView;//扇形头部
    private StickyHeadView stickyHeadView;//粘性头部
    private SunHeadView sunHeadView;

    private HomeAdapter adapter;

    private HomePresenter presenter;
    private int page = 1,lastPage = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

        setTitleText("消息");
        img_menu_icon.setImageResource(R.mipmap.de_ic_add);

        presenter = new HomePresenter(this);
        page = 1;

        adapter = new HomeAdapter(R.layout.item_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        presenter.getArticleList(page);

        textHeadView = new TextHeadView(getContext());
        downHeadView = new DownHeadView(getContext());
        stickyHeadView = new StickyHeadView(getContext());
        sunHeadView = new SunHeadView(getContext());
        refreshLayout
                .setHeaderView(downHeadView)
                .setHeaderView(textHeadView)
                .setHeaderView(new DefHeaderView(getContext()))
                .setHeaderView(stickyHeadView)
                .setHeaderView(sunHeadView)
                .setFootView(new DefFootView(getContext()))
                .setCallBack(new RefreshLayout.CallBack() {
                    @Override
                    public void refreshHeaderView(int state, String stateVal) {
                        textHeadView.setText(stateVal);
                        switch (state) {
                            case RefreshLayout.DOWN_REFRESH: // 下拉刷新状态
                                break;
                            case RefreshLayout.RELEASE_REFRESH: // 松开刷新状态
                                break;
                            case RefreshLayout.LOADING: // 正在刷新中状态
                                if (refreshLayout.isBottom() == false) {
                                    page = 1;
                                    lastPage = -1;
                                }
                                presenter.getArticleList(page);
                                sunHeadView.upAnim();
                                break;
                        }
                    }

                    @Override
                    public void pullListener(int y) {
                        int pullHeight = y / 2;
                        downHeadView.setPull_height(pullHeight);
                        stickyHeadView.move(pullHeight);
                    }
                });

        emptyLayout.setCallBack(new EmptyLayout.CallBack() {
            @Override
            public void click() {
                presenter.getArticleList(page);
            }
        });
    }

    @OnClick({R.id.menu})
    public void click(View view) {

    }

    @Override
    public void success(List<String> list) {
        if (list.size() == 0){
//            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        }else {
            page++;//如果有数据则+1下一页
            if (lastPage != page) {
                if (refreshLayout.isBottom())
                    adapter.addData(list);
                else {
                    adapter.setNewData(list);
                }
            }
            lastPage = page;
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
        refreshLayout.stop();
    }
}
