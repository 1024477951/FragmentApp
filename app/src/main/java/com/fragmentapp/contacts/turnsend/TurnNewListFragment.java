package com.fragmentapp.contacts.turnsend;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;

import butterknife.BindView;
import butterknife.OnClick;


public class TurnNewListFragment extends LazyFragment {

    @BindView(R.id.fragment_cont)
    FrameLayout mFragmentCont;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.fl_search)
    FrameLayout mFlSearch;
    /**
     * 会话列表的fragment
     */
    private TurnConversationFragment mConversationListFragment = null;
    private Context mContext;

    public static TurnNewListFragment newInstance(Bundle bundle) {
        TurnNewListFragment fragment = new TurnNewListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_turn_newlist;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.fl_search,R.id.contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fl_search:
                break;
            case R.id.contact:
                break;
        }
    }



}
