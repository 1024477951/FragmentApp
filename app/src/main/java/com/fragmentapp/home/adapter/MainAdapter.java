package com.fragmentapp.home.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.fragmentapp.TabHelper;

/**
 * Created by liuzhen on 2017/11/10.
 */

public class MainAdapter extends FragmentPagerAdapter {

    public MainAdapter(FragmentManager fm) {
        super(fm);
        TabHelper.init(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return TabHelper.getFragment(position);
    }

    @Override
    public int getCount() {
        return TabHelper.getCount();
    }
}
