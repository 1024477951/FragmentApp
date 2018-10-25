package com.fragmentapp.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.EmptyLayout;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.home.imple.IHomeView;
import com.fragmentapp.home.presenter.HomePresenter;
import com.fragmentapp.im.IMActivity;
import com.fragmentapp.view.refresh.DefFootView;
import com.fragmentapp.view.refresh.DefHeaderView;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
import com.fragmentapp.view.refresh.SunHeadView;
import com.fragmentapp.view.refresh.TextHeadView;
import com.orhanobut.logger.Logger;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class HomeFragment extends IMFragment implements IHomeView {

//    @BindView(R.id.refreshLayout)
//    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

//    private TextHeadView textHeadView;
//    private DownHeadView downHeadView;//扇形头部
//    private StickyHeadView stickyHeadView;//粘性头部
//    private SunHeadView sunHeadView;

    private HomeAdapter adapter;

    private HomePresenter presenter;
    private int page = 1,lastPage = -1;

    @Override
    protected void init() {

        setTitleText("消息");
        img_menu_icon.setImageResource(R.mipmap.de_ic_add);

        presenter = new HomePresenter(this);
        page = 1;

        adapter = new HomeAdapter(R.layout.item_home);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        presenter.getArticleList(page);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                IMActivity.start(getContext());
            }
        });
//        textHeadView = new TextHeadView(getContext());
//        downHeadView = new DownHeadView(getContext());
//        stickyHeadView = new StickyHeadView(getContext());
//        sunHeadView = new SunHeadView(getContext());

//        emptyLayout.setCallBack(new EmptyLayout.CallBack() {
//            @Override
//            public void click() {
//                presenter.getArticleList(page);
//            }
//        });

//        Observable.just("object")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String list) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {//初始化默认加载
            onVisible();
        }
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
                adapter.setNewData(list);
            }
            lastPage = page;
        }
    }

    @Override
    public void error() {
//        emptyLayout.showEmpty((ViewGroup) getView(),"error");
    }

    @Override
    public void loading() {
        showDialog();
    }

    @Override
    public void loadStop() {
        dismissDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
//        textHeadView = null;
//        downHeadView = null;
//        stickyHeadView = null;
//        sunHeadView = null;
    }

}
