package com.fragmentapp.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.view.refresh.DefFootView;
import com.fragmentapp.view.refresh.DefHeaderView;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
import com.fragmentapp.view.refresh.TextHeadView;
import com.fragmentapp.view.refresh.WaterHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private TextHeadView textHeadView;
    private DownHeadView downHeadView;//扇形头部
    private StickyHeadView stickyHeadView;//粘性头部
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        for (int i = 0; i < 20; i++) {
            list.add("" + i);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new HomeAdapter(getActivity(), R.layout.item_home, list));

        textHeadView = new TextHeadView(getActivity());
        downHeadView = new DownHeadView(getActivity());
        stickyHeadView = new StickyHeadView(getActivity());
        refreshLayout
//                .setHeaderView(downHeadView)
//                .setHeaderView(textHeadView)
//                .setHeaderView(new DefHeaderView(getActivity()))
//                .setHeaderView(stickyHeadView)
                .setHeaderView(new WaterHeadView(getActivity()))
                .setFootView(new DefFootView(getActivity()))
                .setCallBack(new RefreshLayout.CallBack() {
                    @Override
                    public void refreshHeaderView(int state, String stateVal) {
//                        textHeadView.setText(stateVal);
                        switch (state) {
                            case RefreshLayout.DOWN_REFRESH: // 下拉刷新状态
                                break;
                            case RefreshLayout.RELEASE_REFRESH: // 松开刷新状态
                                break;
                            case RefreshLayout.LOADING: // 正在刷新中状态
                                break;
                        }
                    }

                    @Override
                    public void pullListener(int y) {
                        int pullHeight = y / 2;
//                        downHeadView.setPull_height(pullHeight);
//                        stickyHeadView.move(pullHeight);
//                        Log.e("tag", pullHeight + "");
                    }
                });
    }

    @OnClick({R.id.search})
    public void search(View view) {
        new SearchFragment().show(getFragmentManager(), TAG);
    }

}
