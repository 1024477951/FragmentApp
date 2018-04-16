package com.fragmentapp.contacts.turnsend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TurnContactsFragmentAdapter extends FragmentPagerAdapter {
    private int count;
    private Bundle bundle;
    public TurnContactsFragmentAdapter(FragmentManager fm, int count, Bundle bundle) {
        super(fm);
        this.count=count;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = TurnContactsFragmentFactory.getFragment(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
