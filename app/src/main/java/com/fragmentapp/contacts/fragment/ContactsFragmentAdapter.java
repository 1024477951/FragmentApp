package com.fragmentapp.contacts.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragmentapp.contacts.ContactsFragmentFactory;

/**
 * Created by Bryan on 2017/8/4.
 */

public class ContactsFragmentAdapter extends FragmentPagerAdapter {
    private int count;

    public ContactsFragmentAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count=count;
    }

    @Override
    public Fragment getItem(int position) {
        return ContactsFragmentFactory.getFragment(position);
    }

    @Override
    public int getCount() {
        return count;
    }
}
