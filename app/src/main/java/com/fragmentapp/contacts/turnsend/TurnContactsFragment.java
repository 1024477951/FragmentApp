package com.fragmentapp.contacts.turnsend;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.helper.ResourcesUtil;
import com.fragmentapp.view.tablayout.SlidingTabLayout;

import butterknife.BindView;


public class TurnContactsFragment extends LazyFragment {

    @BindView(R.id.tb_contacts)
    SlidingTabLayout mTbContacts;
    @BindView(R.id.vp_contactsCont)
    ViewPager mVpContactsCont;

    private TurnContactsFragmentAdapter mAdapter;

    public static TurnContactsFragment newInstance(Bundle bundle) {
        TurnContactsFragment fragment = new TurnContactsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_turn_contacts;
    }

    @Override
    protected void init() {
        mVpContactsCont.setOffscreenPageLimit(3);
        String[] mTitles = ResourcesUtil.getStringArray(R.array.contactsGroup);
        mTbContacts.setViewPager(mVpContactsCont, mTitles);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter=null;
        }
    }

}
