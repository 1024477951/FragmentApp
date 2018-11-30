package com.fragmentapp.dynamic.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by liuzhen on 2017/12/14.
 */

public class EmojiListAdapter extends FragmentPagerAdapter {

    private int count;

    public EmojiListAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return EmojiFragmentFactory.getInstance().getFragment(position);
    }

    @Override
    public int getCount() {
        return count;
    }

}