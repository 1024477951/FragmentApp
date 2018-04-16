package com.fragmentapp.contacts.allfile;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/3/14.
 */

public class ContactsGroupFileFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private GroupFileAdapter adapter;

    private int mPage = 1, mTotal = 0;
    private boolean isRefresh = false;

    public static ContactsGroupFileFragment newInstance(Bundle bundle) {
        ContactsGroupFileFragment fragment = new ContactsGroupFileFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_group_file;
    }

    @Override
    protected void init() {
    }

}
