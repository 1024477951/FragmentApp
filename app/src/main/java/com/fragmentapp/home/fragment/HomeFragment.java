package com.fragmentapp.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.EmptyLayout;
import com.fragmentapp.home.adapter.ArticleAdapter;
import com.fragmentapp.home.bean.ArticleDataBean;
import com.fragmentapp.home.imple.IArticleView;
import com.fragmentapp.home.presenter.ArticlePresenter;
import com.fragmentapp.view.refresh.DefFootView;
import com.fragmentapp.view.refresh.DefHeaderView;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
import com.fragmentapp.view.refresh.TextHeadView;
import com.fragmentapp.view.sticky.DividerDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends LazyFragment implements IArticleView {

    @BindView(R.id.root)
    View root;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    private TextHeadView textHeadView;
    private DownHeadView downHeadView;//扇形头部
    private StickyHeadView stickyHeadView;//粘性头部
    private List<ArticleDataBean.ListBean> list = new ArrayList<>();
    private ArticleAdapter adapter;

    private ArticlePresenter presenter;
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        presenter = new ArticlePresenter(this);
        page = 1;
        presenter.getArticleList(page);

        adapter = new ArticleAdapter(getActivity(), R.layout.item_home, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));

        textHeadView = new TextHeadView(getActivity());
        downHeadView = new DownHeadView(getActivity());
        stickyHeadView = new StickyHeadView(getActivity());
        refreshLayout
                .setHeaderView(downHeadView)
                .setHeaderView(textHeadView)
                .setHeaderView(new DefHeaderView(getActivity()))
                .setHeaderView(stickyHeadView)
//                .setHeaderView(new WaterHeadView(getActivity()))
                .setFootView(new DefFootView(getActivity()))
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
                                if (refreshLayout.isBottom() == false)
                                    page = 1;
                                presenter.getArticleList(page);

                                break;
                        }
                    }

                    @Override
                    public void pullListener(int y) {
                        int pullHeight = y / 2;
                        downHeadView.setPull_height(pullHeight);
                        stickyHeadView.move(pullHeight);
//                        Log.e("tag", pullHeight + "");
                    }
                });

        emptyLayout.setCallBack(new EmptyLayout.CallBack() {
            @Override
            public void click() {
                init();
            }
        });
    }

    @OnClick({R.id.search})
    public void search(View view) {
        new SearchFragment().show(getFragmentManager(), TAG);
    }

    @Override
    public void success(List<ArticleDataBean.ListBean> list) {
        if (list.size() == 0){
            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        }else {
            page++;
            if (refreshLayout.isBottom())
                adapter.addList(list);
            else
                adapter.setList(list);
            Toast.makeText(getActivity(), "success"+adapter.getItemCount(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void error() {
        emptyLayout.showEmpty((ViewGroup) root,"error");
        Toast.makeText(getActivity(),"error",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadStop() {
        refreshLayout.stop();
    }
}
