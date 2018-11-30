package com.fragmentapp.dynamic.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.fragmentapp.R;
import com.fragmentapp.base.LazyFragment;
import com.fragmentapp.dynamic.adapter.EmojiAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiFragment extends LazyFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private EmojiAdapter adapter;

    public static EmojiFragment newInstance(Bundle bundle) {
        EmojiFragment fragment = new EmojiFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_emoji;
    }

    @Override
    protected void init() {
        String[] rc_myemoji_code = getResources().getStringArray(R.array.rc_myemoji_code);
        adapter = new EmojiAdapter(R.layout.item_emoji);
        recyclerView.setAdapter(adapter);
        List<String> list = Arrays.asList(rc_myemoji_code);
        adapter.setNewData(list);
    }
}
