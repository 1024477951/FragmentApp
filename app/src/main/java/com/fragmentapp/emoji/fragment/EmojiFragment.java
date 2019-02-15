package com.fragmentapp.emoji.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fragmentapp.R;
import com.fragmentapp.emoji.adapter.EmojiAdapter;
import com.fragmentapp.emoji.manager.EmojiManager;

import butterknife.BindView;

/**
 * Created by liuzhen on 2018/11/30.
 */

public class EmojiFragment extends EmojiBaseFragment {

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
        adapter = new EmojiAdapter(R.layout.item_emoji);
        recyclerView.setAdapter(adapter);
        adapter.setNewData(EmojiManager.getEmojiList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                if (callBack != null){
                    callBack.click(adapter.getData().get(position).text,name);
                }
            }
        });
    }

}
