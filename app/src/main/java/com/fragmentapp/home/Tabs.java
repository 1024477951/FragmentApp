package com.fragmentapp.home;

import com.fragmentapp.R;
import com.fragmentapp.home.fragment.HomeFragment;
import com.fragmentapp.home.fragment.IMFragment;

/**
 * Created by liuzhen on 2018/9/28.
 */

public enum Tabs {

    Home(0,HomeFragment.class, R.layout.fragment_home),
    Contact(1,HomeFragment.class, R.layout.fragment_home),
    Use(2,HomeFragment.class, R.layout.fragment_home),
    Me(3,HomeFragment.class, R.layout.fragment_home);

    Tabs(int index, Class<? extends IMFragment> clazz, int layoutId){
        this.tabIndex = index;
        this.clazz = clazz;
        this.layoutId = layoutId;
    }

    public final Class<? extends IMFragment> clazz;

    public final int layoutId;
    public final int tabIndex;

    public static final Tabs fromTabIndex(int tabIndex) {
        for (Tabs value : Tabs.values()) {
            if (value.tabIndex == tabIndex) {
                return value;
            }
        }
        return null;
    }

}