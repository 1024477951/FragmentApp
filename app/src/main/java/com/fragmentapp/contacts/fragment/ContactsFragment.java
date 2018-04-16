package com.fragmentapp.contacts.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.ResourcesUtil;
import com.fragmentapp.view.tablayout.SlidingTabLayout;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Bryan on 2017/8/4.
 */

public class ContactsFragment extends LazyFragment {

    @BindView(R.id.tb_contacts)
    SlidingTabLayout mTbContacts;
    @BindView(R.id.vp_contactsCont)
    ViewPager mVpContactsCont;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.iv_contact_list_arrow_nor)
    ImageView ivContactListarrowNor;
    @BindView(R.id.tv_teamName)
    TextView tvTeamName;
    @BindView(R.id.tv_userNum)
    TextView tvUserNum;
    @BindView(R.id.suspension_bar)
    RelativeLayout mSuspensionBar;
    @BindView(R.id.fl_search)
    FrameLayout mFlsearch;

    private ContactsFragmentAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contacts;
    }

    @Override
    protected void init() {
        tv_title.setText("通讯录");

        mVpContactsCont.setOffscreenPageLimit(3);
        setAdapterAndNotify();
        String[] mTitles = ResourcesUtil.getStringArray(R.array.contactsGroup);
        mTbContacts.setViewPager(mVpContactsCont, mTitles);
    }

    private void setAdapterAndNotify() {
        mAdapter = new ContactsFragmentAdapter(getChildFragmentManager(), 4);
        mVpContactsCont.setAdapter(mAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter=null;
        }
    }

    @OnClick(R.id.fl_search)
    public void onViewClicked() {

    }

}
