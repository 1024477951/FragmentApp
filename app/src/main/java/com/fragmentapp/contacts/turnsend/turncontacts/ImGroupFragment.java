package com.fragmentapp.contacts.turnsend.turncontacts;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.contacts.bean.IMUserGroupListDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImGroupFragment extends LazyFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.iv_contact_list_arrow_nor)
    ImageView mIvContactListArrowNor;
    @BindView(R.id.tv_teamName)
    TextView mTvTeamName;
    @BindView(R.id.tv_userNum)
    TextView mTvUserNum;
    @BindView(R.id.suspension_bar)
    RelativeLayout mSuspensionBar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private ImGroupAdapter mAdapter;
    private boolean isRefresh;

    private String url, message = "0", name = "0", filePath, targetId;
    private String username;
    private String sendTable = null;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notoolbar_recyclerview;
    }

    @Override
    protected void init() {
        Bundle bundle = getArguments();
        url = bundle.getString("url");
        message = bundle.getString("message", "0");
        name = bundle.getString("name", "0");
        filePath = bundle.getString("filePath");
        sendTable = bundle.getString("sendTable");

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mAdapter = new ImGroupAdapter(R.layout.item_im_user);
        mRecyclerview.setAdapter(mAdapter);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.color_3399ff,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light
        );
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                longTimeOperation();
            }
        });
        List<IMUserGroupListDataBean> list = new ArrayList<>();
        list.add(new IMUserGroupListDataBean("-4", "我的文件助手", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "-4"));
        list.add(new IMUserGroupListDataBean("1", "韩创big family", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "1"));
        list.add(new IMUserGroupListDataBean("2", "内部系统", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "2"));
        mAdapter.setNewData(list);
    }

    private void longTimeOperation() {
        // true，刷新开始，所以启动刷新的UI样式.
        mSwipeRefreshLayout.setRefreshing(true);
        isRefresh = true;
        // 开始启动刷新...
        // 在这儿放耗时操作的 AsyncTask线程、后台Service等代码。
        mAdapter.clean();

        List<IMUserGroupListDataBean> list = new ArrayList<>();
        list.add(new IMUserGroupListDataBean("-4", "我的文件助手", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "-4"));
        list.add(new IMUserGroupListDataBean("1", "韩创big family", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "1"));
        list.add(new IMUserGroupListDataBean("2", "内部系统", "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", "2"));
        for (int i = 3;i < 13;i++){
            list.add(new IMUserGroupListDataBean(""+i, "11"+i, "https://pic2.hanmaker.com/im/20171226/5a41baca09b1b.png", ""+i));
        }
        mAdapter.setNewData(list);
    }

    @Override
    public void onDestroyView() {
        mAdapter.clean();
        super.onDestroyView();

    }

}
