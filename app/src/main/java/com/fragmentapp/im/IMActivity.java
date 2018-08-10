package com.fragmentapp.im;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fragmentapp.R;
import com.fragmentapp.base.BaseActivity;
import com.fragmentapp.im.adapter.IMAdapter;
import com.fragmentapp.im.bean.IMEntity;
import com.fragmentapp.im.bean.MsgBean;
import com.fragmentapp.im.imple.IIMView;
import com.fragmentapp.im.presenter.IMPresenter;
import com.fragmentapp.view.refresh.DownHeadView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.StickyHeadView;
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
    private StickyHeadView stickyHeadView;

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
        mImmersionBar.statusBarView(R.id.view_status);
        mImmersionBar.init();
        setTitleText("消息");

        adapter = new IMAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        presenter = new IMPresenter(this);
        page = 1;
        presenter.getList(page);

        textHeadView = new TextHeadView(context);
        downHeadView = new DownHeadView(context);
        stickyHeadView = new StickyHeadView(context);
        refreshLayout
                .setHeaderView(downHeadView)
                .setHeaderView(textHeadView)
                .setHeaderView(stickyHeadView)
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

                                }
                                presenter.getList(page);
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

        //viewholder 点击监听
        adapter.setClickListener(new IMAdapter.IMClickListener() {
            @Override
            public void click(View view, MsgBean message) {
                switch (view.getId()){
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
                switch (view.getId()){
                    case R.id.layout_content:
                        break;
                }
            }
        });

    }

    @Override
    public void success(List<MsgBean> list) {
        if (list.size() == 0){
//            emptyLayout.showEmpty((ViewGroup) getView(),"empty");
        }else {
            if (page > 1) {
                adapter.addData(0,list);
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
        refreshLayout.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
