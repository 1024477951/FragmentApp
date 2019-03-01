package com.fragmentapp.im;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.KeyboardUtils;
import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.im.adapter.IMAdapter;
import com.fragmentapp.im.bean.IMEntity;
import com.fragmentapp.im.bean.MsgBean;
import com.fragmentapp.im.imple.IIMView;
import com.fragmentapp.im.presenter.IMPresenter;
import com.fragmentapp.view.keyboard.KeyBoardView;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
import com.fragmentapp.view.refresh.TextHeadView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMActivity extends BaseActivity implements IIMView ,OnRefreshLoadMoreListener ,KeyboardUtils.OnSoftInputChangedListener{

    @BindView(R.id.layout_keyboard)
    KeyBoardView layout_keyboard;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private IMAdapter adapter;
    private List<MsgBean> list = new ArrayList<>();

    private int page = 1;

    private IMPresenter presenter;

    public static void start(Context context) {
        start(context, null);
    }

    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, IMActivity.class);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    @Override
    public int layoutID() {
        return R.layout.activity_im;
    }

    @Override
    public void init() {
        setTitleText("消息");
        KeyboardUtils.registerSoftInputChangedListener(this, this);
        adapter = new IMAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        presenter = new IMPresenter(this);
        page = 1;
        presenter.getList(page);

        //viewholder 点击监听
        adapter.setClickListener(new IMAdapter.IMClickListener() {
            @Override
            public void click(View view, MsgBean message) {
                switch (view.getId()) {
                    case R.id.btn_error:
                        break;
                    case R.id.layout_content:
                        break;
                    case R.id.profile_image:
                        break;
                }
            }
        });
        //viewholder 长按监听
        adapter.setLongClickListener(new IMAdapter.IMLongClickListener() {
            @Override
            public void longClick(View view, MsgBean message) {
                switch (view.getId()) {
                    case R.id.layout_content:
                        break;
                }
            }
        });
        layout_keyboard.setKeyBoardView(getSupportFragmentManager());
    }

    @Override
    public void success(List<MsgBean> list) {
        if (list.size() == 0) {
//            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        } else {
            if (page > 1) {
                adapter.addData(0, list);
            } else {
                this.list.clear();
                this.list.addAll(list);
                adapter.setNewData(this.list);
                recyclerView.scrollToPosition(this.list.size() - 1);
            }
            page++;//如果有数据则+1下一页
        }
    }

    @Override
    public void error() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void loadStop() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void onLoadMore(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
//        refreshLayout.finishLoadMore();
        refreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void onRefresh(@NonNull com.scwang.smartrefresh.layout.api.RefreshLayout refreshLayout) {
        presenter.getList(page);
        refreshLayout.finishRefresh();
    }

    @Override
    public void onSoftInputChanged(int height) {
        layout_keyboard.updateHeight(height);
    }
}
