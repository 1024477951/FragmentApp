package com.fragmentapp.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragmentapp.home.IMTabHelper;

/**
 * Created by liuzhen on 2017/11/10.
 */

public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
        IMTabHelper.init(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return IMTabHelper.getFragment(position);
    }

    @Override
    public int getCount() {
        return IMTabHelper.getCount();
    }
}
