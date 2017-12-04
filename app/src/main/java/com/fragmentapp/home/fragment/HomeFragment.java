package com.fragmentapp.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.DensityUtil;
import com.fragmentapp.home.FragmentFactory;
import com.fragmentapp.home.adapter.HomeAdapter;
import com.fragmentapp.view.refresh.DefFootView;
import com.fragmentapp.view.refresh.DefHeaderView;
import com.fragmentapp.view.refresh.DownView;
import com.fragmentapp.view.refresh.RefreshLayout;
import com.fragmentapp.view.refresh.TextHeadView;
import com.fragmentapp.view.sticky.OnGroupClickListener;
import com.fragmentapp.view.sticky.PowerGroupListener;
import com.fragmentapp.view.sticky.PowerfulStickyDecoration;

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
    private TextHeadView headView;
    private DownView downView;
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

//        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
//                .init(new PowerGroupListener() {
//                    @Override
//                    public String getGroupName(int position) {
//                        //获取组名，用于判断是否是同一组
//                        if (list.size() > position) {
//                            return list.get(position)+"--";
//                        }
//                        return null;
//                    }
//
//                    @Override
//                    public View getGroupView(int position) {
//                        //获取自定定义的组View
//                        if (list.size() > position) {
//                            View view = getLayoutInflater().inflate(R.layout.item_group, null, false);
//                            ((TextView) view.findViewById(R.id.tv)).setText(list.get(position)+"--");
//                            return view;
//                        } else {
//                            return null;
//                        }
//                    }
//                })
//                .setGroupHeight(DensityUtil.dp2px(getActivity(), 40))     //设置高度
//                .setGroupBackground(Color.WHITE)
//                .setDivideColor(Color.parseColor("#CCCCCC"))            //分割线颜色
//                .setDivideHeight(DensityUtil.dp2px(getActivity(), 1))     //分割线高度
//                .setOnClickListener(new OnGroupClickListener() {                   //点击事件，返回当前分组下的第一个item的position
//                    @Override
//                    public void onClick(int position) {                                 //Group点击事件
//                        String content = "onGroupClick --> " + list.get(position);
//                        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
//                    }
//                })
//                .build();

//        recyclerView.addItemDecoration(decoration);

//        headView = new TextHeadView(getActivity());
        downView = new DownView(getActivity());

        refreshLayout.setHeaderView(downView)
//                .setHeaderView(headView)
                .setHeaderView(new DefHeaderView(getActivity()))
                .setFootView(new DefFootView(getActivity()))
                .setCallBack(new RefreshLayout.CallBack() {
                    @Override
                    public void refreshHeaderView(int state, String stateVal) {
//                      headView.setText(stateVal);
                        switch (state) {
                            case RefreshLayout.DOWN_REFRESH: // 下拉刷新状态

                                break;
                            case RefreshLayout.RELEASE_REFRESH: // 松开刷新状态
//                              headView.down();
                                break;
                            case RefreshLayout.LOADING: // 正在刷新中状态
//                              headView.up();
                                break;
                        }
                    }

                    @Override
                    public void pullListener(int y) {
                        int pullHeight = y / 2;
                        downView.setPull_height(pullHeight);
//                        Log.e("tag", pullHeight + "");
                    }
                });
    }

    @OnClick({R.id.search})
    public void search(View view) {
        new SearchFragment().show(getFragmentManager(), TAG);
    }

}
