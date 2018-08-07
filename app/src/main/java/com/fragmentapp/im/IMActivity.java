package com.fragmentapp.im;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.im.adapter.IMAdapter;
import com.fragmentapp.im.bean.IMEntity;
import com.fragmentapp.im.bean.MsgBean;
import com.fragmentapp.im.imple.IIMView;
import com.fragmentapp.im.presenter.IMPresenter;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.TextHeadView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/8/6.
 */

public class IMActivity extends BaseActivity implements IIMView{

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private IMAdapter adapter;
    private List<MsgBean> list = new ArrayList<>();

    private TextHeadView textHeadView;
    private DownHeadView downHeadView;
    private int page = 1,lastPage = -1;

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

        adapter = new IMAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        presenter = new IMPresenter(this);
        page = 1;
        presenter.getList(page);

        textHeadView = new TextHeadView(context);
        downHeadView = new DownHeadView(context);

        refreshLayout
                .setHeaderView(downHeadView)
                .setHeaderView(textHeadView)
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

                                break;
                        }
                    }

                    @Override
                    public void pullListener(int y) {
                        int pullHeight = y / 2;
                        downHeadView.setPull_height(pullHeight);
                    }
                });

    }

    @Override
    public void success(List<MsgBean> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.setNewData(this.list);
    }

    @Override
    public void error() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void loadStop() {
        refreshLayout.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
