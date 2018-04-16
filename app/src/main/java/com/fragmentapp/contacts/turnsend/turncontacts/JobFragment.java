package com.fragmentapp.contacts.turnsend.turncontacts;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.contacts.bean.UserGroupListDataBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JobFragment extends LazyFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;

    List<MultiItemEntity> mList = new ArrayList<>();
    List<UserGroupListDataBean.UserListBean> userListBeans = new ArrayList<>();
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
    private JobAdapter mAdapter;

    private int mCurrentPosition = 0;

    private int mSuspensionHeight;
    private LinearLayoutManager mLayoutManager;

    private ArrayMap<String, Integer> mArrayMap;
    private String mKey;
    private boolean isRefresh = false;

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

        //        mSwipeRefreshLayout.setEnabled(false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(mLayoutManager);
        mAdapter = new JobAdapter(mList);
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
        List<MultiItemEntity> mList = new ArrayList<>();
        for (int i = 0;i < 5;i++){
            UserGroupListDataBean bean = new UserGroupListDataBean();
            bean.setTeam_id(""+i);
            bean.setTeam_name("11"+i);
            bean.setTeam_sign(""+i);

            List<UserGroupListDataBean.UserListBean> subItem = new ArrayList<>();
            for(int j = 0;j<3;j++){
                UserGroupListDataBean.UserListBean sub = new UserGroupListDataBean.UserListBean();
                sub.setFull_name("fullName");
                sub.setNum("0");
                sub.setPost_name("postName");
                sub.setTeam_name("aaa");

                subItem.add(sub);
                bean.addSubItem(sub);
            }
            bean.setUserList(subItem);

            mList.add(bean);
        }
        mAdapter.setNewData(mList);

    }
    private void longTimeOperation() {
        // true，刷新开始，所以启动刷新的UI样式.
//        mSwipeRefreshLayout.setRefreshing(true);
//        isRefresh = true;
        // 开始启动刷新...
        // 在这儿放耗时操作的 AsyncTask线程、后台Service等代码。
        List<MultiItemEntity> mList = new ArrayList<>();
        for (int i = 5;i < 10;i++){
            UserGroupListDataBean bean = new UserGroupListDataBean();
            bean.setTeam_id(""+i);
            bean.setTeam_name("11"+i);
            bean.setTeam_sign(""+i);

            List<UserGroupListDataBean.UserListBean> subItem = new ArrayList<>();
            for(int j = 0;j<3;j++){
                UserGroupListDataBean.UserListBean sub = new UserGroupListDataBean.UserListBean();
                sub.setFull_name("fullName");
                sub.setNum("0");
                sub.setPost_name("postName");
                sub.setTeam_name("bbb");

                subItem.add(sub);
                bean.addSubItem(sub);
            }
            bean.setUserList(subItem);

            mList.add(bean);
        }
        mAdapter.setNewData(mList);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}
