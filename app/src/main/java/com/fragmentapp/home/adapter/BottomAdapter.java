package com.fragmentapp.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragmentapp.home.fragment.HomeFragment;

/**
 * Created by liuzhen on 2017/11/8.
 */

public class BottomAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"首页", "通讯录", "消息","我的"};

    public BottomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
